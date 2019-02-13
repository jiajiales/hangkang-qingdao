package com.hotcomm.prevention.bean.mysql.manage.home;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class HomeModuleData {
	private Integer moduleid;//模块id
	private String modulename;//模块名称
	private Integer alarmCount;//报警数量（未处理）
	private Integer woCount;//工单数量（未处理）
	private Integer messageCount;//消息数量（未发送消息）
}
