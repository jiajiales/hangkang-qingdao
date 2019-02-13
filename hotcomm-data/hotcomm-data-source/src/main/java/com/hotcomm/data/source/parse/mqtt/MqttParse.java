package com.hotcomm.data.source.parse.mqtt;

import org.springframework.stereotype.Component;

import com.hotcomm.data.source.bean.TransportData;
import com.hotcomm.data.source.parse.DataSourceParase;

@Component
public class MqttParse implements DataSourceParase{

	@Override
	public TransportData parse(String source) {
		
		return null;
	}
	
}
