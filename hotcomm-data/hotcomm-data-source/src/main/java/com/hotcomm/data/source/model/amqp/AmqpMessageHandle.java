package com.hotcomm.data.source.model.amqp;

import java.util.UUID;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotcomm.data.source.bean.TransportData;
import com.hotcomm.data.source.bean.TransportData.TransportParseType;
import com.hotcomm.data.source.comm.Constant;
import com.hotcomm.data.source.db.DeviceDao;
import com.hotcomm.data.source.model.MessageHandle;
import com.hotcomm.data.source.parse.amqp.ZhenWenMessageParase;
import com.hotcomm.data.source.parse.amqp.ZhenWenMessageParase.Data;
import com.hotcomm.framework.utils.RedisHelper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AmqpMessageHandle implements MessageHandle {

	final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private RedisHelper redisHelper;

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private DeviceDao deviceDao;
	
	@Override
	public void handleMessage(byte[] data) {
		try {
			String logUno = UUID.randomUUID().toString();
			String message = new String(data);
			log.info("编号{}接收消息:{}", logUno, message);
			ZhenWenMessageParase.Data dataVo = ZhenWenMessageParase.parse(message);
			String code = dataVo.getCode();
			String keyCode = "DEVICE_"+code;
			String deviceIdStr = redisHelper.get(keyCode);
			Integer deviceId = null;
			if(deviceIdStr==null) {
				deviceId = deviceDao.existsDeviceCode(code);
				redisHelper.set(keyCode, deviceId.toString(), 60*60*12);
			}else {
				deviceId = Integer.parseInt(deviceIdStr);
			}
			String codekey = code + "_" + dataVo.getFrameCnt();
			if (redisHelper.existsKey(codekey)) {
				log.info("编号{}消息重复,取消发送", logUno);
				return ;
			}
			redisHelper.set(codekey, message, Constant.REDIS_CODE_CNT_KEEPLIVE);
			TransportData pushData = conver(dataVo);
			pushData.setDeviceId(deviceId);
			Message pushMessage = new Message(mapper.writeValueAsBytes(pushData), new MessageProperties());
			rabbitTemplate.send(pushMessage);
			log.info("编号{}发送消息成功", logUno);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
	}

	@Override
	public TransportData conver(Object... args) {
		TransportData data = new TransportData();
		ZhenWenMessageParase.Data dataVo = (Data) args[0];
		data.setDeviceCode(dataVo.getCode());
		data.setSourceData(dataVo.getMessage());
		data.setSource(TransportData.TransportDataSource.AMQP.getValue());
		data.setCoreData(dataVo.getCoreData());
		data.setParseType(TransportParseType.ZHENGWEN.getValue());
		return data;
	}

}
