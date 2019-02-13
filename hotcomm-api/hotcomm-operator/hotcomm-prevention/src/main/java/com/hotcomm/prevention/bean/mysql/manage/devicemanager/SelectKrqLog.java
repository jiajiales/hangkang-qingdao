package com.hotcomm.prevention.bean.mysql.manage.devicemanager;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SelectKrqLog {
	private Integer id;

	private String queueid;

	private String macAddr;

	private String data;

	private String state;

	private Integer lbflag;

	private Integer fraflag;

	private Integer mofaflag;

	private Integer soaflag;

	private Integer keepalive;

	private Integer calibration;

	private String voltagestate;

	private String voltage;

	private String gsensor;

	private String lastvalue;

	private Integer temperature;

	private String addtime;

	private String recv;

	private String commsysType;

	private double rssi;

	private double snr;

	private double frameCnt;

	private String gwid;

	private String gwip;

	private String channel;

	private Integer sf;

	private Integer fport;

	private String pub;
}
