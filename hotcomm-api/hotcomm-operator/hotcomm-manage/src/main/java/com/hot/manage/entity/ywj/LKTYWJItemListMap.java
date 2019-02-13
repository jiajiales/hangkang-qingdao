package com.hot.manage.entity.ywj;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTYWJItemListMap implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	
	private String groupname;
	
	private double x;
	
	private double y;
	
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

	private Integer allCount;
	
	private Integer alarmCount;
	
	private Integer faultCount;
}
