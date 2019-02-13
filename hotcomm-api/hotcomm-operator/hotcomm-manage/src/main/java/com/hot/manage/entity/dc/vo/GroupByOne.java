package com.hot.manage.entity.dc.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class GroupByOne implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//项目id
	private Integer id;
	//项目名称
	private String groupname;
	//项目地址
	private String groupcode;
	//经度
	private String x;
	//纬度
	private String y;
	//坐标类型
	private Integer coordinate;
	//添加时间
	private String addtime;
	//城市id
	private Integer cityid;
	//负责人电话
	private String telephone;
	//设备总数
	private Integer count;
	//项目图片路径
	private String imgpath;
	//项目编号
	private String itemnum;
	//添加人
	private String addusername;
	//责任人/负责人
	private String managername;
	
	private List<DCItemPicVo> tItemPicVo;
}
