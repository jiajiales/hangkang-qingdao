package com.hotcomm.data.model.rabbitmq.receive;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class MessageData {
	
	private String  mac;
	private String  sourceData;
	private String  gwid;
	private Integer source;
	private String  coreData;
}
