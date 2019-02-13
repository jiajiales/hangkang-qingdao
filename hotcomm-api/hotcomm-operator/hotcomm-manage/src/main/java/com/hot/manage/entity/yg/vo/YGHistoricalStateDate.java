package com.hot.manage.entity.yg.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class YGHistoricalStateDate implements Serializable {
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
}
