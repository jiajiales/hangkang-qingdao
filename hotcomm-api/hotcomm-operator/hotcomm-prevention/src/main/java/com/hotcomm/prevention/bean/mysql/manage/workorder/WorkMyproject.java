package com.hotcomm.prevention.bean.mysql.manage.workorder;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class WorkMyproject implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private Integer groupid;
	
	private String groupname;
	
	private String lng;
	
	private String lat;
	
	private String groupcode;
	
	private Integer coordinate;
	
	private String addtime;
	
	private Integer adduserid;
	
	private Integer managerid;
	
	private String contacts;
	
	private Integer cityid;
	
	private String telephone;
	
	private String imgpath;
	
	private String itemnum;
	
	private Integer worknum;
}
