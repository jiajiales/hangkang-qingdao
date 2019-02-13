package com.hot.manage.entity.yg.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class YGHistoricalDate implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//(value = "年份")
	private Integer theyear;
	
	//(value = "月份")
	private Integer mon;
	
	//(value = "报警次数/月")
	private Integer alarmCount;
	
	//(value = "报警总数/年")
	private Integer deviceAlarmCount;
}
