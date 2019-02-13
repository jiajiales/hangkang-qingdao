package com.hot.manage.entity.yg;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTSignList implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//(value = "签到地址id")
	private Integer id;
	
	//(value = "签到设备编号")
	private String devnum;
	
	//(value = "关联项目名称")
	private String groupname;
	
	//(value = "签到设备放置位置")
	private String address;
	
	//(value = "经度")
	private String lon;
	
	//(value = "纬度")
	private String lat;
	
	//(value = "巡检人员id列表")
	private String patrolsidlist;
	
	//(value = "签到人姓名列表")
	private String contacts;
	
	//(value = "最新一条签到时间")
	private String addtime;
	
}
