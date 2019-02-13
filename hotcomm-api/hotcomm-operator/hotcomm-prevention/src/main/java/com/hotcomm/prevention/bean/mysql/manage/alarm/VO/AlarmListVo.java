package com.hotcomm.prevention.bean.mysql.manage.alarm.VO;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AlarmListVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// (value = "报警记录ID")
	private Integer moduleid;
	private Integer alarmid;

	// 项目组名称
	private String groupname;

	// 当前模块
	private String module;

	// (value = "设备ID")
	private Integer deviceid;

	// (value = "设备编码")
	private String devnum;

	// (value = "设备所在地址")
	private String code;

	// (value = "负责人")
	private String contacts;

	// (value = "报警时间")
	private String addtime;

	// (value = "经度")
	private String lat;

	// (value = "纬度")
	private String lng;

	// (value = "处理人")
	private String handler;

	// (value = "报警程度，1-5星")
	private Integer level;

	// (value = "处理状态，0：未处理；1：处理中；2：已处理")
	private Integer handlestate;

	// (value = "报警处理类型")
	private String state_name;

	// (value="是否需要派单")
	private Integer isdispatch;
}
