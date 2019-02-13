package com.hotcomm.data.model.rabbitmq.send.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DataCallBack {

	private String dataId;

	private String queueKey;

	private String data;

}
