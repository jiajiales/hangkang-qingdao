package com.hot.manage.entity.yg;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTSelctDevOnid implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// (value = "报警/事件id")
	private Integer id;

	// (value = "来源")
	private String sourcetype;

	// (value = "发布时间")
	private String addtime;

	// (value = "描述")
	private String state_name;

	// (value = "紧急程度")
	private Integer level;

	// (value = "设备id")
	private Integer deviceid;
	// 设备地址
	private String code;
	// 设备编号
	private String devnum;

}
