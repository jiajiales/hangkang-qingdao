package com.hotcomm.prevention.bean.mysql.manage.alarm.VO;

import java.io.Serializable;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class HistoricalStateDateVo implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// (value = "年份")
	private Integer theyear;

	// (value = "报警类型")
	private String statename;

	// (value = "报警类型次数统计/年")
	private Integer alarmcount;

	// 模块
	private String module;
}
