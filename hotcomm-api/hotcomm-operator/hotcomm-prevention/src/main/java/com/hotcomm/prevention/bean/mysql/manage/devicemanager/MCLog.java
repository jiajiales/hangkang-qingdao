package com.hotcomm.prevention.bean.mysql.manage.devicemanager;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class MCLog {
	private String queueid;
	private String macAddr;
	private String data;
	private Integer state;
	private Integer lbflag;
	private Integer keepalive;
	private Integer calibration;
	private String voltagestate;
	private String voltage;
	private String gsensor;
	private Integer lastvalue;
	private Integer temperature;
	private String addtime;
	private String recv;
	private String commsysType;
	private double rssip;
	private double snr;
	private double frameCnt;
	private String gwid;
	private String gwip;
	private String channel;
	private Integer sf;
	private Integer fport;
	private String pub;
}
