package com.hot.manage.entity.dc.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class DCGroupAddress implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//项目id
	private Integer id;

	//项目名称
	private String groupname;

	//项目经度
	private double lat;

	//项目纬度
	private double lng;
	
	//坐标类型
	private Integer coordinate;
}
