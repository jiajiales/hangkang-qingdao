package com.hotcomm.prevention.bean.mysql.manage.group.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class AppSum {
	private Integer  eventSum; //未处理事件数 
	private Integer  workSum;//未处理工单数
	private Integer  alarmSum;//未处理报警数
}
