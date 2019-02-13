package com.hot.manage.entity.yg.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class YG_DeviceInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//(value = "报警记录ID")
	private Integer alarmid;
	
	//(value = "设备编号")
	private String devnum;
	
	//(value = "设备地址")
	private String code;
	
	//(value = "负责人")
	private String contacts;
	
	//(value = "所属项目")
	private String groupname;
	
	//(value = "经度")
	private double lat;
	
	//(value = "纬度")
	private double lng;
	
	//(value = "电话1")
	private String telephone1;
	
	//(value = "电话2")
	private String telephone2;
	
	//(value = "报警时间")
	private String addtime;
	
	//(value = "报警类型")
	private String state_name;
}
