package com.hotcomm.prevention.bean.mysql.manage.alarm;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class AlarmInfo {
	private Integer alarmid;
	private Integer devid;
	private String addtime;
	private String state_name;
	private Integer moduleid;
	private String alarmResult;
	private String alarmTime;
	private String alarmContent;

}