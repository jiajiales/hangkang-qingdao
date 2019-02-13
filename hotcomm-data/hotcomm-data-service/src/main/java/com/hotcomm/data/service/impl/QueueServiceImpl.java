package com.hotcomm.data.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotcomm.data.bean.entity.service.MemberVhost;
import com.hotcomm.data.bean.entity.service.Queue;
import com.hotcomm.data.bean.entity.sys.Member;
import com.hotcomm.data.bean.enums.MemberEnum.MemberStatus;
import com.hotcomm.data.bean.enums.QueueEnum.QueueDeleteStatus;
import com.hotcomm.data.bean.enums.QueueEnum.QueueStatus;
import com.hotcomm.data.bean.enums.QueueEnum.QueueType;
import com.hotcomm.data.bean.params.service.queue.QueuePageParams;
import com.hotcomm.data.bean.params.service.queue.QueueParams;
import com.hotcomm.data.bean.params.service.queue.QueueQueryParams;
import com.hotcomm.data.bean.vo.queue.QueuePageVO;
import com.hotcomm.data.bean.vo.queue.QueueVO;
import com.hotcomm.data.comm.PageInfo;
import com.hotcomm.data.db.MemberVhostMapper;
import com.hotcomm.data.db.QueueMapper;
import com.hotcomm.data.model.rabbitmq.RabbitQueueManager;
import com.hotcomm.data.model.rabbitmq.RabbitRestTool;
import com.hotcomm.data.model.rabbitmq.common.QueueCustomer;
import com.hotcomm.data.service.CustomerService;
import com.hotcomm.data.service.DataPushReadyService;
import com.hotcomm.data.service.DataService;
import com.hotcomm.data.service.QueueService;
import com.hotcomm.data.utils.LogUtil;
import com.hotcomm.framework.utils.DateUtils;
import com.hotcomm.framework.web.exception.ExceptionManager;
import com.hotcomm.framework.web.exception.HKException;

@Service
@Transactional
public class QueueServiceImpl implements QueueService {

	@Resource
	private QueueMapper queueMapper;

	@Resource
	private MemberVhostMapper memberVhostMapper;

	@Autowired
	@Qualifier("exceptionManager")
	private ExceptionManager manager;

	@Autowired
	private RabbitQueueManager rabbitQueueManager;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private RabbitRestTool rabbitRestTool;

	@Autowired
	private DataService dataService;

	@Autowired
	private DataPushReadyService dataPushReadyService;

	@Autowired
	RedisTemplate<String, ?> redisTemplate;

	/**
	 * 队列 新增
	 */
	@Override
	public Integer addBean(QueueParams params) throws HKException {
		// Queue queue = new Queue();
		// String userName = params.getCreateUser();
		return null;
	}

	/**
	 * 队列 删除
	 */
	@Override
	public void delBean(Integer id) throws HKException {

	}

	/**
	 * 队列 修改
	 */
	@Override
	public void updateBean(QueueParams params) throws HKException {
		Queue result = new Queue();
		result.setQueueId(params.getQueueId());
		result.setRemark(params.getRemark());
		result.setUpdateTime(new Date());
		queueMapper.updateByPrimaryKeySelective(result);
	}

	/**
	 * 队列 查询
	 */
	@Override
	public QueueVO getBean(Integer id) throws HKException {
		return queueMapper.getQueueParamsByQueueId(id);
	}

	/**
	 * 队列 分页查询
	 */
	@Override
	public PageInfo<QueuePageVO> queryPage(QueuePageParams params) throws HKException {
		PageHelper.startPage(params.getPage(), params.getRows());
		Page<QueuePageVO> pagelist = queueMapper.queryPage(params);
		List<QueuePageVO> volist = new ArrayList<QueuePageVO>();
		volist.addAll(pagelist);
		PageInfo<QueuePageVO> info = new PageInfo<>(pagelist.getTotal(), volist);
		return info;
	}

	/**
	 * 队列 运行
	 */
	@Override
	public void queueRun(Integer queueId) throws HKException {
		Logger log  = LogUtil.SERVICE_LOG;
		
		Queue queue = new Queue();
		queue.setQueueId(queueId);
		queue = queueMapper.selectByPrimaryKey(queue);
		
		if (queue == null)
			throw manager.create("QUEUE10001");
		
		log.info("队列--{}---运行",queue.getQueueName());

		if (QueueStatus.QUEUE_RUN.getValue() == queue.getQueueStatus())
			throw manager.create("QUEUE10003");

		if (System.currentTimeMillis() > queue.getHoleTime().getTime())
			throw manager.create("QUEUE10005");

		Integer memberId = queue.getMemberId();
		Member member = customerService.getMemberByid(memberId);

		if (member.getStatus() == MemberStatus.DISABLE.getValue())
			throw manager.create("MEMBER10002");

		String queueName = queue.getQueueName();

		MemberVhost vhostVo = customerService.getVhostByMemberId(memberId);
		String vhostName = vhostVo.getVhost();

		QueueCustomer customer = rabbitRestTool.getQueueMsg(vhostName, queueName);
		Integer messages = customer.getMessages();

		if (messages > queue.getSendFilterInterval())
			throw manager.create("QUEUE10006");

		Integer queueStatus = QueueStatus.QUEUE_RUN.getValue();
		queue.setQueueStatus(queueStatus);
		Queue updateQueue = new Queue();
		updateQueue.setQueueName(queueName);
		queueMapper.updateByPrimaryKeySelective(queue);

		final int limit = 500;
		final long waitSendNums = Long.valueOf(this.queueMapper.getWaitSendNumByQueueId(queueId));
		
		dataService.executePushWaitSendData(queueId, limit, waitSendNums);
		
		// 更新数据库->hk_device_push_message{queue_status}
		dataPushReadyService.queueStatusUpdate(queueId, queueStatus);
	}

	/**
	 * 队列 暂停
	 */
	@Override
	public void queuePause(Integer queueId) throws HKException {
		Logger log  = LogUtil.SERVICE_LOG;
		Queue queue = new Queue();
		queue.setQueueId(queueId);
		queue = queueMapper.selectByPrimaryKey(queue);

		if (queue == null)
			throw manager.create("QUEUE10001");
		
		log.info("队列--{}---暂停",queue.getQueueName());
		Integer queueStatus = QueueStatus.QUEUE_PAUSE.getValue();

		if (QueueStatus.QUEUE_RUN.getValue() == queue.getQueueStatus()) {
			queue.setQueueStatus(queueStatus);
			queueMapper.updateByPrimaryKeySelective(queue);

			// 更新数据库->hk_device_push_message{queue_status}
			dataPushReadyService.queueStatusUpdate(queueId, queueStatus);
		}
	}

	/**
	 * 队列 名称 变更
	 */
	@Override
	public void queueRename(QueueParams params) throws HKException {
		Logger log  = LogUtil.SERVICE_LOG;
		
		Queue queue = new Queue();
		Integer queueId = params.getQueueId();
		queue.setQueueId(queueId);
		queue = queueMapper.selectByPrimaryKey(queue);

		if (queue == null)
			throw manager.create("QUEUE10001");

		String oldQueueName = queue.getQueueName();
		log.info("队列--{}---名称变更", oldQueueName);
		
		// 判断队列是否暂停
		if (QueueStatus.QUEUE_PAUSE.getValue() != queue.getQueueStatus())
			throw manager.create("QUEUE10003");

		// 判断队列待发送数据-数量是否为0
		if (queueMapper.getWaitSendNumByQueueId(queueId) > 0)
			throw manager.create("QUEUE10004");

		// 变更队列服务器队列名
		String vhostCode = memberVhostMapper.getVhostCodeByMemberId(queue.getMemberId());
		String newQueueName = params.getQueueName();
		rabbitQueueManager.updateQueueBingVhost(vhostCode, oldQueueName, newQueueName);

		// 变更数据库队列名称
		queue.setQueueName(newQueueName);
		queue.setUpdateTime(new Date());
		queueMapper.updateByPrimaryKeySelective(queue);

		if (queue.getType() == QueueType.UPSTREAM.getValue()) {
			// 更新数据库->hk_device_push_message{queue_name}
			dataPushReadyService.queueNameUpdate(queueId, newQueueName);
		}
	}

	/**
	 * 队列 有效期限 变更
	 */
	@Override
	public void updateHoleTime(QueueParams params) throws HKException {
		Logger log  = LogUtil.SERVICE_LOG;
		
		Queue queue = new Queue();
		Integer queueId = params.getQueueId();
		queue.setQueueId(queueId);
		queue = queueMapper.selectByPrimaryKey(queue);

		if (queue == null)
			throw manager.create("QUEUE10001");
		
		log.info("队列--{}---效期限变更",queue.getQueueName());
		
		if (QueueStatus.QUEUE_PAUSE.getValue() != queue.getQueueStatus())
			throw manager.create("QUEUE10005");

		Date queueHoldTime = params.getHoleTime();
		queue.setHoleTime(queueHoldTime);
		queue.setUpdateTime(new Date());
		queueMapper.updateByPrimaryKeySelective(queue);

		// 更新数据库->hk_device_push_message{queue_holdtime}
		dataPushReadyService.queueHoleTimeUpdate(queueId, queueHoldTime);
	}

	/**
	 * 队列 分发拦截基数 变更
	 */
	@Override
	public void updateFilternums(QueueParams params) throws HKException {
		Logger log  = LogUtil.SERVICE_LOG;
		
		Queue queue = new Queue();
		Integer queueId = params.getQueueId();
		queue.setQueueId(queueId);
		queue = queueMapper.selectByPrimaryKey(queue);

		if (queue == null)
			throw manager.create("QUEUE10001");

		if (QueueStatus.QUEUE_PAUSE.getValue() != queue.getQueueStatus())
			throw manager.create("QUEUE10007");
		
		log.info("队列--{}---分发拦截基数 变更",queue.getQueueName());

		Long sendFilterInterval = params.getSendFilterInterval();
		queue.setSendFilterInterval(sendFilterInterval);
		queue.setUpdateTime(new Date());
		queueMapper.updateByPrimaryKeySelective(queue);

		// 更新数据库->hk_device_push_message{queue_holdtime}
		dataPushReadyService.queueSendFilterNumsUpdate(queueId, sendFilterInterval);
	}

	@Override
	public List<Queue> getVhostInnerQueue(List<MemberVhost> memberVhosts) throws HKException {
		if (memberVhosts == null || memberVhosts.isEmpty())
			return new ArrayList<>();

		List<Integer> memberIds = new ArrayList<>(memberVhosts.size());

		for (MemberVhost memberVhost : memberVhosts)
			memberIds.add(memberVhost.getMemberId());

		return queueMapper.queryQueues(new QueueQueryParams(memberIds));
	}

	@Override
	public List<Queue> getActiveQueues(List<Integer> memberIds) {
		return queueMapper.listActive(memberIds, null);
	}

	@Override
	public void saveQueue(String queueName, QueueType type, Integer memberId) throws HKException {
		Queue queue = new Queue();
		queue.setQueueName(queueName);
		queue.setType(type.getValue());
		queue.setMemberId(memberId);
		queue.setHoleTime(DateUtils.converTime(new Date(), DateUtils.YEAR, DateUtils.ADD, 1));
		queue.setQueueStatus(QueueStatus.QUEUE_PAUSE.getValue());
		queue.setCreateTime(new Date());
		queue.setUpdateTime(new Date());
		queue.setCreateUser("admin");
		queue.setIsDelete(QueueDeleteStatus.NO.getValue());
		queueMapper.insertSelective(queue);
	}

	@Override
	public Long queryQueueWatiSendNums(Integer memberId) throws HKException {
		return queueMapper.getWaitSendNums(memberId);
	}

	@Override
	public String getQueueName(Integer memberId, QueueType type) throws HKException {
		Queue queue = new Queue();
		queue.setMemberId(memberId);
		queue.setType(type.getValue());
		return queueMapper.selectOne(queue).getQueueName();
	}

	@Override
	public void updateQueueName(Integer memberId, QueueType type, String queueName) throws HKException {
		Queue queue = new Queue();
		queue.setMemberId(memberId);
		queue.setType(type.getValue());
		queue.setQueueName(queueName);
		queueMapper.updateQueueName(queue);
	}

	/**
	 * 队列 通道信息 查询
	 */
	@Override
	public QueueCustomer getQueueRabbitView(String queueName) throws HKException {
		Queue queue = queueMapper.selectOne(new Queue(queueName));

		if (queue == null)
			throw manager.create("QUEUE10008");

		MemberVhost memberVhost = memberVhostMapper.selectOne(new MemberVhost(queue.getMemberId()));
		QueueCustomer queueCustomer = rabbitRestTool.getQueueMsg(memberVhost.getVhost(), queueName);
		return queueCustomer;
	}

	/**
	 * 查询具体某个队列信息
	 */
	@Override
	public QueueVO getQueueView(QueueParams params) throws HKException {
		return queueMapper.getQueueView(params);
	}

	@Override
	public void stopQueue(Integer memberId) throws HKException {
		queueMapper.stopQueue(memberId);
	}

	@Override
	public void handleExpireQueue() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Logger log = LogUtil.SERVICE_LOG;
				List<Queue> failures = queueMapper.getExpireQueue();
				for(Queue queue:failures) {
					String queueName = queue.getQueueName();
					log.info("该队列{}已到过期时间,同时将队列暂停",queueName);
					queuePause(queue.getQueueId());
				}
				
			}
		}).start();;
	
	}

	@Override
	public void loopQueueSendFilterInterval() {
		Logger log = LogUtil.SERVICE_LOG;
		List<Map<String, Object>> runningQueues = this.queueMapper.getRuningQueue();
		if(runningQueues.size()==0) 
			return ;
		
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					for(Map<String, Object> queue:runningQueues) {
						Integer queueId = Integer.parseInt(queue.get("queueId").toString());
						String vhostName = queue.get("vhost").toString();
						String queueName = queue.get("queueName").toString();
						Long blockMaxSize = Long.parseLong(queue.get("blockMaxSize").toString());
						QueueCustomer customer = rabbitRestTool.getQueueMsg(vhostName, queueName);
						Integer messageSize = customer.getMessages();
						if(messageSize>=blockMaxSize) {
							log.info("队列{}阻塞数据边界值超过客户定义最大值,将队列暂停",queueName);
							queuePause(queueId);
							continue;
						}
					}
					
				}
			}).start();
	
		}
	
}
