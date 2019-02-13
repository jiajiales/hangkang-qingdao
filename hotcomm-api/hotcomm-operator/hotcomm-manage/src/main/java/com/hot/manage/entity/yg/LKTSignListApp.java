package com.hot.manage.entity.yg;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTSignListApp implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	//(value = "签到地址id")
	private Integer id;
	
	//(value = "签到设备编号")
	private String devnum;
	
	//(value = "签到设备放置位置")
	private String address;
	
	//(value = "经度")
	private String lon;
	
	//(value = "纬度")
	private String lat;
	
	//(value = "签到人编号")
	private String userNum;
	
	//(value = "签到人姓名列表")
	private String contacts;
	
}
