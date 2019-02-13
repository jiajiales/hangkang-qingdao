package com.hotcomm.data.model.rabbitmq.send.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SendDataBean {

	private String macAddr;

	private String deviceType;

	private String dataMsg;

	private String receiveTime;

	private String dataCode;

}
