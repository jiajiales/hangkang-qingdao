package com.hotcomm.data.bean.vo.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DataVO {

	private Long memberId;

	private String deviceCode;

	private Long dataId;

	private String ucode;

	private String macAddress;

	private String receiveData;

	private Integer frameCnt;

	private String rssiSnr;

	private String channel;

	private String sf;

	private Long createTime;

	private Integer sendStatus;

	private String queueName;

	private String gwip;

	private Integer dataSource;

}
