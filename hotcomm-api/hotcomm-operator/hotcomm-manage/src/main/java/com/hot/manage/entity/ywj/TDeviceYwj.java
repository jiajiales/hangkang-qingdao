package com.hot.manage.entity.ywj;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TDeviceYwj {
	private Integer id;

	private String devnum;

	private String mac;

	private String code;

	private String lng;

	private String lat;

	private Integer state;

	private String battery;

	private String lastvalue1;

	private String lastvalue2;

	private String lastvalue3;

	private String lastvalue4;

	private String lastvalue5;

	private String lastvalue6;

	private String addtime;

	private Boolean isenable;

	private Boolean isdelete;

	private Integer adduserid;

	private String lastalarmtime;

	private String alarmvalue;

	private Integer plusminus;

	private double x;

	private double y;
}