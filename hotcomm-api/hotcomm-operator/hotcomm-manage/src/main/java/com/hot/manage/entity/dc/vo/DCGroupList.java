package com.hot.manage.entity.dc.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DCGroupList implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 项目id
	private Integer id;

	// 图片路径
	private String pictureUrl;

	// 项目编号
	private String itemnum;

	// 项目名称
	private String groupname;

	// 项目地址
	private String groupcode;

	// 设备总数
	private Integer devCount;

	// 联系人
	private String contacts;

	// 添加时间
	private String addtime;

	// 经度
	private double lat;

	// 纬度
	private double lng;
}
