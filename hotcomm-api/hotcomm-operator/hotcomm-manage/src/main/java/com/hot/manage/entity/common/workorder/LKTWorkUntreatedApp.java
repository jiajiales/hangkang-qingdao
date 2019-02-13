package com.hot.manage.entity.common.workorder;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTWorkUntreatedApp implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// (value = "工单id")
	private Integer woid;

	private String title;

	// (value = "设备编号")
	private String devnum;

	// (value = "发布时间")
	private String addtime;

	// (value = "要求处理时间 0-其他 ;1-立即 ;2-30分钟 ;3-1个小时; 4-2个小时' 5- 3个小时 ;6- 6个小时; 7-
	// 一天内; 8-两天内;9-三天内")
	private Integer complete_time;

	// (value = "设备地址")
	private String code;

	// (value = "派单人姓名")
	private String contacts;

	// (value = "派单人电话")
	private String telephone;

	// (value = "状态，0：未处理；1：挂起； ")
	private Integer state;

	// (value = "经度")
	private String lng;

	// (value = "纬度")
	private String lat;

	// (value = "模块id")
	private Integer moduleid;

}
