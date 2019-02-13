package com.hotcomm.prevention.bean.mysql.manage.home;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class HomeDataForDay {
	private Integer moduleid;//模块id
	private String modulename;
	private Integer alarmcount;//报警数量统计
	private Integer wocount;//工单数量
}
