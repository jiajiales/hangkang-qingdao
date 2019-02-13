package com.hot.manage.entity.ywj;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTYWJSelectOnId implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String mac;
	private Integer ownid;
	private Integer itempicid;
	private String code;
	private String picpath;
	private String contacts;
	private String site;

	private String devnum;

	private String lat;

	private String lng;

	private String x;

	private String y;

	private Integer groupid;

	private String groupname;

	private String lastvalue1;

	private String lastvalue2;

	private String lastvalue3;

	private String lastvalue4;

	private String lastvalue5;

	private String lastvalue6;

	private String lastalarmtime;

	private String maxalarmvalue;

	private String minalarmvalue;
	
	private Integer plusminus;
	
	private String videos;
}
