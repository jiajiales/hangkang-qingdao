package com.hot.manage.entity.jg;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTDevList implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private Integer devid;

	private String devnum;

	private String mac;
	
	private String groupname;

	private String code;

	private Integer own_id;

	private String contacts;

	private String addtime;

	private String lat;

	private String lng;

	private Integer videoNum;
	
	private Integer battery;

	private Integer x;
	
	private Integer y;
	
	private Integer itempicid;
	
	private String site;
	
	private String picpath;
}
