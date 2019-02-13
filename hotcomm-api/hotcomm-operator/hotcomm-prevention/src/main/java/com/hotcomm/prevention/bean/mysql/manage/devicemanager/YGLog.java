package com.hotcomm.prevention.bean.mysql.manage.devicemanager;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class YGLog {
	private String lbAlarm;
	private String channel;
	private String commsysType;
	private String dfId;
	private String faultAlarm;
	private String fireAlarm;
	private Integer frameCnt;
	private String gwid;
	private String gwip;
	private String inputTime;
	private String macAddr;
	private String msgId;
	private Integer placId;
	private String repeater;
	private String reserved;
	private Integer rssi;
	private Integer sf;
	private Integer snr;
	private Integer snrMax;
	private Integer snrMin;
	private Integer status;
	private Integer sysType;
	private Integer tempAlarm;
	private float tempValue;
	private String updateTime;

}
