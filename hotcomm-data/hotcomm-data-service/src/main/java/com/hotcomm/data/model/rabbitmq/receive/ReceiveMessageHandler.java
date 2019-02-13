package com.hotcomm.data.model.rabbitmq.receive;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotcomm.data.service.DataService;
import com.hotcomm.data.utils.LogUtil;

@Component
public class ReceiveMessageHandler {

	final static ObjectMapper mapper = new ObjectMapper();

	@Autowired
	DataService dataService;

	public void handleMessage(String datamsg) throws Exception {
		Logger log = LogUtil.SERVICE_LOG;

		TransportData data = mapper.readValue(datamsg, TransportData.class);
		
		log.info("接收设备--{}--终端数据--{}",data.getDeviceCode(),datamsg);
		
		dataService.saveData(data);
		
	}

	
}



