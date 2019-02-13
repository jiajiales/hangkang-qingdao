package com.hotcomm.prevention.bean.mysql.manage.workorder;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class WorkDetailsAllevent implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// (value = "工单id")
	private Integer woid;

	// (value = "报警/事件id")
	private Integer id;

	// (value = "来源")
	private String sourcetype;

	// (value = "添加时间")
	private String addtime;

	// (value = "报警描述")
	private String state_name;

	// (value = "紧急程度")
	private Integer level;

	// (value = "设备id")
	private Integer devid;

	// (value = "设备类型")
	private String devicetype;

	// (value = "设备地址")
	private String code;

	// (value = "设备mac")
	private String mac;

	// (value = "纬度")
	private String lat;

	// (value = "经度")
	private String lng;
}
