package com.hot.manage.entity.sxdl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SXDLDevList {
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

	private Integer state;

	private String battery;

	private Integer adduserid;

	private String lastalarmtime;

	private String count;

	private Integer own_id;
	
	private String lastValue;
	
	private String addrcode;
	
	private String lessalarmvalue;
	
	private String topalarmvalue;
	
	private Integer alarmset;
	
	private Double x;
	
	private Double y;
	
	private String site;
	
	private String picpath;

}
