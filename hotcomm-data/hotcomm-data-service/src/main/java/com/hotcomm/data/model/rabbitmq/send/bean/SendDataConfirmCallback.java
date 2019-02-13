package com.hotcomm.data.model.rabbitmq.send.bean;

import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;

import com.hotcomm.data.service.DataService;
import com.hotcomm.data.utils.LogUtil;

public class SendDataConfirmCallback implements ConfirmCallback {

	private String dataCode;

	private DataService dataService;

	public SendDataConfirmCallback(String dataCode, DataService dataService) {
		super();
		this.dataCode = dataCode;
		this.dataService = dataService;
	}

	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		Logger log = LogUtil.SERVICE_LOG;
		if (!ack) {
			log.info("数据编号:-->" + dataCode + ":-->发送失败,得到确认,失败原因:" + cause);
			dataService.pushFail(this.dataCode);
		}
	}

}
