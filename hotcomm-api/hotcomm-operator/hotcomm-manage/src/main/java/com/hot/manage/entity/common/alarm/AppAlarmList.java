package com.hot.manage.entity.common.alarm;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AppAlarmList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer alarmid;
	private String devnum;
	private Integer handlestate;
	private String modulename;
	private String addtime;
	private String code;
	private String lat;
	private String lng;
	private Integer moduleid;
	private String handleTime;
	private Integer isdispatch;
}
