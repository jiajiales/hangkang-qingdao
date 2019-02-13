package com.hot.manage.entity.dc.vo;

import lombok.Data;

@Data
public class DCDeviceAllGroup {

	// 项目组id
	private Integer id;
	// 项目组名称
	private String groupname;
	// 项目组责任人
	private String contacts;
	// 经度
	private String lat;
	// 纬度
	private String lng;
}
