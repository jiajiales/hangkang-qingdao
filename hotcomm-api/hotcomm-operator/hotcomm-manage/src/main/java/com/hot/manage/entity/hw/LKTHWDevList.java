package com.hot.manage.entity.hw;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTHWDevList implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private Integer devid;

	private String devnum;

	private String mac;

	private String groupname;

	private String code;

	private Integer managerid;

	private String contacts;

	private String addtime;

	private String lat;

	private String lng;

	private Integer count;

	private Integer battery;

	private Double x;

	private Double y;

	private Integer own_id;

	private Integer itempicid;

	private String site;
	private String picpath;

}
