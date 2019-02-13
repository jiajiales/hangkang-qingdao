package com.hot.manage.entity.ywj;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTYWJDevList {
	private Integer devid;

	private String devnum;

	private String mac;

	private String groupname;

	private String code;

	private String managerid;

	private String contacts;

	private String addtime;

	private String lng;

	private String lat;

	private String x;
	private String y;
	private Integer itempicid;
	private String site;

	private Integer state;

	private String battery;

	private String lastvalue1;

	private String lastvalue2;

	private String lastvalue3;

	private String lastvalue4;

	private String lastvalue5;

	private String lastvalue6;

	private Integer adduserid;

	private String lastalarmtime;

	private int maxalarmvalue;
	
	private int minalarmvalue;

	private Integer plusminus;
	
	private String picpath;
}