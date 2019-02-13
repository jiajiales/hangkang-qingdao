package com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DeviceParam implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String devnum;
	private String mac;
	private String code;
	private String lat;
	private String lng;
	private Integer coordinate;
	private Date installtime;
	private Integer isenable;
	private Integer isdelete;
	private String addtime;
	private Integer adduserid;
	private Date lastalarmtime;
	private Integer battery;
	private double x;
	private double y;
	private Integer ownId;
	private Integer devtype;
	private Integer state;
	private Integer alarmstate;
	private String lastvalue;
	private String maxAlarmvalue;
	private String minAlarmvalue;
	private String jgCoverName;
	private String jgManu;
	private String jgMaterial;
	private Integer jgPurpose;
	private Integer jgLoadbear;
	private double ljtHeight;
	private double ljtAlarmvalue;
	private String pm;
	private String pmOne;
	private String pmNoiseval;
	private String pmTemval;
	private String pmHumval;
	private String pmLight;
	private String slVoltage;
	private String slAmpere;
	private String slWatt;
	private String slLighteness;
	private String slElectricity;
	private String slOfftime;
	private String slOntime;
	private Integer alarmset;
	private String wtElectricity;
	private Integer ywjLastvalue1;
	private String ywjLastvalue2;

	private String ywjLastvalue3;

	private String ywjLastvalue4;

	private String ywjLastvalue5;

	private String ywjLastvalue6;
	
	private String ywjLlusminus;

	private Integer moduleid;
}
