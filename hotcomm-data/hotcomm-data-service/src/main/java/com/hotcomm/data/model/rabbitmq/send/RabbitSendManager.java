package com.hotcomm.data.model.rabbitmq.send;

import org.slf4j.Logger;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotcomm.data.bean.vo.data.SendDataVO;
import com.hotcomm.data.model.rabbitmq.send.bean.SendDataConfirmCallback;
import com.hotcomm.data.model.rabbitmq.send.bean.SendReturnCallBack;
import com.hotcomm.data.service.DataService;
import com.hotcomm.data.utils.LogUtil;
import com.hotcomm.framework.utils.SpringUtil;

@Component
public class RabbitSendManager {

	final ObjectMapper jsonMapper = new ObjectMapper();

	public void pushData(SendDataVO sendDataVO, DataService dataService) {
		Logger log = LogUtil.SERVICE_LOG;
		String vhostCode = sendDataVO.getVhostCode();
		ConnectionFactory factory = null;
		try {
			factory = (ConnectionFactory) SpringUtil.getBean(vhostCode);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		String code = sendDataVO.getUcode();
		String queueName = sendDataVO.getQueueName();
		String deviceCode = sendDataVO.getDeviceCode();
		RabbitTemplate amqpTemplate = new RabbitTemplate(factory);
		amqpTemplate.setMandatory(true);
		amqpTemplate.setConfirmCallback(new SendDataConfirmCallback(code, dataService));
		amqpTemplate.setReturnCallback(new SendReturnCallBack(code, dataService));
		try {
			amqpTemplate.convertAndSend(queueName, sendDataVO.getDeviceData());
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("终端设备-{}上行数据分发队列{},分发完成,数据库记录编号{}", deviceCode, queueName, code);
		dataService.pushSuccess(code);
		log.info("数据编号-{} 分发队列成功,", code);
	}

}
