package com.hotcomm.data.bean.vo.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DataQueueSendVO {

	private String ucode;

	private String queueName;

	private String message;

	private String deviceCode;

	private String deviceTypeCode;

	private Integer memberId;

	private String sourceData;

}
