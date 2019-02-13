package com.hotcomm.prevention.bean.mysql.manage.devicemanager;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DevList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	private String picpath;
	
	private Integer onlinestate;
}