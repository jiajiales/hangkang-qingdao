package com.hot.manage.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DevPageParam {

	private Integer pageNum;

	private Integer pageSize;

	private Integer userid;

	private Integer moduleid;
	
	//设备编号或设备地址
	private String context;
	
	private Integer groupid;

	private String starttime;

	private String endtime;

	private Integer batterystate;// 0：正常；1：报警
}
