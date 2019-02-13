package com.hot.manage.entity.common.alarm;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AlarmDeviceInfoVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// (value = "报警记录ID")
	private Integer alarmid;

	// 设备id
	private Integer devid;

	// (value = "报警时间")
	private String addtime;

	// (value = "设备地址")
	private String code;
	
	private String mac;

	// (value = "报警类型")
	private String state_name;

	// (value = "设备编号")
	private String devnum;

	// (value = "负责人")
	private String contacts;

	// (value = "所属项目")
	private String groupname;

	// (value = "经度")
	private String lat;

	// (value = "纬度")
	private String lng;

	// (value = "电话1")
	private String telephone;

	// 关联视频
	private String videoPath;

	// 处理状态
	private Integer state;
	
	// 模块id
	private Integer moduleid;
	
	private Integer isdispatch;
}
