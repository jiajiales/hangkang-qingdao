package com.hotcomm.data.model.rabbitmq.send.bean;

import org.slf4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;

import com.hotcomm.data.service.DataService;
import com.hotcomm.data.utils.LogUtil;

public class SendReturnCallBack implements ReturnCallback {

	private String dataCode;

	private DataService dataService;

	public SendReturnCallBack(String dataCode, DataService dataService) {
		super();
		this.dataCode = dataCode;
		this.dataService = dataService;
	}

	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		Logger log = LogUtil.SERVICE_LOG;
		if (replyCode == 302) {
			log.info("数据编号:-->" + dataCode + ":-->发送失败,得到确认,失败原因:" + message);
			dataService.pushFail(this.dataCode);
		}
	}

}
