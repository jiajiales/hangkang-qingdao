package com.hot.manage.entity.krq;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTKRQDevList {
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
	
	private double x;

	private double y;

	private Integer state;

	private String battery;

	private Integer adduserid;

	private String lastalarmtime;

	private String count;

	private Integer own_id;

	private String site;
	
	private String picpath;
}
