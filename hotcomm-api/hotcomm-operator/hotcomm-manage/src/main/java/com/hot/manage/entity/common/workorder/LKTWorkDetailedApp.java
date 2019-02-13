package com.hot.manage.entity.common.workorder;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTWorkDetailedApp implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// (value = "工单id")
	private Integer woid;

	// (value = "工单标题")
	private String title;

	// (value = "工单描述")
	private String descrition;

	// (value = "地址")
	private String code;

	// (value = "经度")
	private String lng;

	// (value = "纬度")
	private String lat;

	// (value = "0-其他 ;1-立即 ;2-30分钟 ;3-1个小时; 4-2个小时' 5- 3个小时 ;6- 6个小时; 7- 一天内;
	// 8-两天内;9-三天内")
	private Integer complete_time;

	// (value = "添加时间")
	private String addtime;

	// (value = "派单人姓名")
	private String contacts;

	// (value = "派单人号码")
	private String telephone;

	// (value = "状态，0：未处理；1：挂起；2：已处理")
	private Integer state;

	// (value = "设备编号")
	private String devnum;

	// (value = "设备id")
	private Integer devid;
	
	private String devicetype;

	// (value = "派单人id")
	private Integer pdid;

	// (value = "模块id")
	private Integer moduleid;
		
	// (value = "图片URL;用,隔开")
	private String img;

	private String newdevnum;
	
	// (value = "关联事件集合")
	private List<LKTWorkDetailsAllevent> son;

}
