package com.hot.manage.entity.krq;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTKRQSelectOnId implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Integer itempicid;
	
	private String picpath;
	
	private String site;
	
	private String devnum;
	
	private String lat;
	
	private String lng;
	
	private double x;
	
	private double y;
	
	private Integer groupid;
	
	private String groupname;

	private String lastalarmtime;
	
	private int own_id;
	
	private String videos;
	
	private String code;
}
