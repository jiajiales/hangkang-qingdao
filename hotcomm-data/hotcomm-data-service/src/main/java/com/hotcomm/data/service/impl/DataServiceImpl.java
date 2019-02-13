package com.hotcomm.data.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotcomm.data.bean.entity.service.Data;
import com.hotcomm.data.bean.entity.service.DataPushReady;
import com.hotcomm.data.bean.entity.service.DataQueue;
import com.hotcomm.data.bean.entity.service.MemberVhost;
import com.hotcomm.data.bean.enums.DataQueueEnum;
import com.hotcomm.data.bean.enums.MemberEnum.MemberStatus;
import com.hotcomm.data.bean.enums.QueueEnum.QueueStatus;
import com.hotcomm.data.bean.params.service.data.DataPageParams;
import com.hotcomm.data.bean.vo.data.DataQueueSendVO;
import com.hotcomm.data.bean.vo.data.DataVO;
import com.hotcomm.data.bean.vo.data.ReceiveDataVO;
import com.hotcomm.data.bean.vo.data.SendDataVO;
import com.hotcomm.data.comm.PageInfo;
import com.hotcomm.data.db.DataMapper;
import com.hotcomm.data.db.DataPushReadyMapper;
import com.hotcomm.data.db.DataQueueMapper;
import com.hotcomm.data.db.QueueMapper;
import com.hotcomm.data.model.rabbitmq.RabbitVhostManager;
import com.hotcomm.data.model.rabbitmq.receive.TransportData;
import com.hotcomm.data.model.rabbitmq.send.RabbitSendManager;
import com.hotcomm.data.service.DataService;
import com.hotcomm.data.utils.LogUtil;
import com.hotcomm.data.utils.UUIDUtils;
import com.hotcomm.framework.utils.SpringUtil;
import com.hotcomm.framework.web.exception.ExceptionManager;
import com.hotcomm.framework.web.exception.HKException;

@Service
@Transactional
public class DataServiceImpl implements DataService {


	@Resource
	private DataMapper dataMapper;
	@Resource
	private QueueMapper queueMapper;

	@Autowired
	@Qualifier("exceptionManager")
	private ExceptionManager manager;


	@Autowired
	private DataQueueMapper dataQueueMapper;

	@Autowired
	private RabbitVhostManager rabbitVhostManager;

	@Autowired
	private RabbitSendManager sendManager;
	
	@Autowired
	DataPushReadyMapper dataPushReadyMapper;
	
	@Override
	public PageInfo<DataVO> queryPage(DataPageParams params) {
		Date startTime = params.getStartTime();
		Date endTime = params.getEndTime();

		if (startTime != null)
			params.setLongStartTime(startTime.getTime());

		if (endTime != null)
			params.setLongEndTime(endTime.getTime());

		int page = params.getPage();
		int rows = params.getRows();
		params.setStartIndex(((page - 1) * rows));
		params.setEndIndex(rows);

		Long count = dataMapper.queryPageCount(params);
		return new PageInfo<>(count, count == 0 ? new ArrayList<>() : dataMapper.queryPage(params));
	}

	/**
	 * 查阅具体数据
	 */
	@Override
	public ReceiveDataVO getData(Long dataId) throws HKException {
		return new ReceiveDataVO(dataMapper.getData(dataId));
	}

	@Override
	@Transactional
	public void pushSuccess(String code) throws HKException {
		dataQueueMapper.updateDataQueueSendStatus(code, DataQueueEnum.SendStatus.SEND_SUCCESS.getValue());
	}

	@Override
	@Transactional
	public void pushFail(String code) throws HKException {
		dataQueueMapper.updateDataQueueSendStatus(code, DataQueueEnum.SendStatus.SEND_FAIL.getValue());
	}

	@Override
	public void saveData(TransportData data) {
		//Logger log = LogUtil.SERVICE_LOG;
		String deviceCode = data.getDeviceCode();

		// 存储 设备数据
		Data dbData = new Data(deviceCode, new Date().getTime(), data.getCoreData(), data.getSourceData(), data.getSource());
		this.dataMapper.insertSelective(dbData);
		Long dataId = dbData.getId();

		// 获取设备推送通道相关参数信息
		//List<DataPushReady> readVos = dataMapper.queryLoadReadyMsg(deviceCode);
		// 此处生产环境条件尚未合格,待优化
		List<DataPushReady> readVos = dataPushReadyMapper.select(new DataPushReady(deviceCode));
		if (readVos == null||readVos.isEmpty()) {
			readVos = dataMapper.queryLoadReadyMsg(deviceCode);
			if (readVos == null||readVos.isEmpty())
				return;
			readVos.forEach(p->{dataPushReadyMapper.insert(p);});
		} 

		for (DataPushReady veadVo : readVos) {
			String queueName = veadVo.getQueueName();
			Integer memberId = veadVo.getMemberId();
			Date holeTime = veadVo.getQueueHoleTime();
			Integer queueId = veadVo.getQueueId();
			// Long sendFilterInterval = veadVo.getSendFilterInterval();

			// 更新上行队列接收数据总量,待发送数据总量
			//log.info("更新上行队{}列接收数据总量,待发送数据总量", queueName);
			//queueMapper.updateQueueDataParams(queueName);

			// 过滤用户状态-无效 -延迟发送
			if (veadVo.getMemberStatus() == MemberStatus.DISABLE.getValue()) {
				//log.info("过滤用户{}状态{}-无效 -延迟发送", memberId, veadVo.getMemberStatus());
				this.addQueueData(queueName, memberId,queueId,dataId, DataQueueEnum.SendStatus.SEND_WAI);
				continue;
			}

			// 过滤队列状态-暂停 -延迟发送
			if (veadVo.getQueueStatus() == QueueStatus.QUEUE_PAUSE.getValue()) {
				//log.info("过滤队列{}状态{}-无效 -延迟发送", queueName, veadVo.getQueueStatus());
				this.addQueueData(queueName, memberId, queueId,dataId, DataQueueEnum.SendStatus.SEND_WAI);
				continue;
			}

			// 过滤队列有效期-过期 -延迟发送
			if (holeTime != null && holeTime.getTime() <= System.currentTimeMillis()) {
				//log.info("过滤队列{}有效期{}-过期  -延迟发送", queueName, format.format(holeTime));
				// 将队列变更为暂停状态
				this.addQueueData(queueName, memberId, queueId,dataId, DataQueueEnum.SendStatus.SEND_WAI);
				continue;
			}

			// 过滤队列通道-拥堵 -延迟发送
			// QueueCustomer queues = rabbitRestTool.getQueueMessage(vhostName, queueName);
			// if (queues.getMessages() > sendFilterInterval) {
			// log.info("过滤队列{}通道-拥堵[messages:{},customers:{}] -延迟发送", queueName,
			// queues.getMessages(),
			// queues.getConsumers());
			// // 将队列变更为暂停状态
			// queueService.queuePause(queueId);
			// updateDataToWait(ucode);
			// continue;
			// }

			// 存储 队列关联数据
			//log.info("终端设备-{} 上行数据入库", deviceCode);
			String ucode = this.addQueueData(queueName, memberId, queueId,dataId, DataQueueEnum.SendStatus.UNSENT);

			SendDataVO sendDataVo = new SendDataVO();
			sendDataVo.setDeviceCode(deviceCode);
			sendDataVo.setVhostCode(veadVo.getVhostCode());
			sendDataVo.setUcode(ucode);
			sendDataVo.setQueueName(queueName);
			sendDataVo.setDeviceData(data.getSourceData());
			sendDataVo.setDeviceType(veadVo.getDeviceTypeCode());

			sendManager.pushData(sendDataVo, this);

		}

	}
	
	private String addQueueData(String queueName, Integer memberId,Integer queueId,Long dataId, DataQueueEnum.SendStatus status) {
		String ucode = UUIDUtils.get32BitUUID();
		DataQueue dataQueue = new DataQueue();
		dataQueue.setUcode(ucode);
		dataQueue.setMemberId(memberId);
		dataQueue.setQueueName(queueName);
		dataQueue.setDataId(dataId);
		dataQueue.setSendStatus(status.getValue());
		dataQueue.setQueueId(queueId);
		dataQueueMapper.insertSelective(dataQueue);
		return ucode;
	}
	
	@Override
	public void executePushWaitSendData(Integer queueId,int limit, long waitSendNums) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Logger log = LogUtil.SERVICE_LOG;
				int len = (int) (waitSendNums <= limit ? 1 : (waitSendNums / limit) + (waitSendNums % limit > 0 ? 1 : 0));
				while (waitSendNums > 0 && len > 0) {
					List<DataQueueSendVO> vos = dataQueueMapper.queryWaitSendDatas(queueId, limit);
					
					System.out.println(vos);
					for (DataQueueSendVO vo : vos) {
						if (vo == null)
							continue;

						Integer memberId = vo.getMemberId();

						MemberVhost vhost = rabbitVhostManager.getVhost(memberId);
						String vhostCode = vhost.getVhostCode();
						SendDataVO sendDataVo = new SendDataVO();
						sendDataVo.setDeviceCode(vo.getDeviceCode());
						sendDataVo.setQueueName(vo.getQueueName());
						sendDataVo.setUcode(vo.getUcode());
						sendDataVo.setDeviceType(vo.getDeviceTypeCode());
						sendDataVo.setVhostCode(vhostCode);
						sendDataVo.setDeviceData(vo.getSourceData());
						sendManager.pushData(sendDataVo, SpringUtil.getBean(DataService.class));
						log.info("变更队列{}等待发送数据总量-递减", vo.getQueueName());
					}
					len--;	
				}
			}
		}).start();
	}

}
