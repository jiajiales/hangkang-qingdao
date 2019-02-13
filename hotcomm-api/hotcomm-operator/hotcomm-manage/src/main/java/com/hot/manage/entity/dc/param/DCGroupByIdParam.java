package com.hot.manage.entity.dc.param;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class DCGroupByIdParam implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 项目id
	private Integer id;
	// 项目名称
	private String groupname;
	// 项目地址
	private String groupcode;
	// 经度
	private String x;
	// 纬度
	private String y;
	// 坐标类型
	private Integer coordinate;
	// 城市id
	private Integer cityid;
	//负责人id
	private Integer managerid;
	// 项目编号
	private String itemnum;
	// 项目图片路径
	private String imgpath;
	
	private List<TItemPicParam> tItemPicParam;
}
