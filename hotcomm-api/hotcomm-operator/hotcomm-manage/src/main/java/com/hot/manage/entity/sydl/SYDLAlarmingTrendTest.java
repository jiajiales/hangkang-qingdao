package com.hot.manage.entity.sydl;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SYDLAlarmingTrendTest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<SYDLAlarmingTrendVO> thisYear;
	
	private List<SYDLAlarmingTrendVO> lastYear;
	
	private List<SYDLAlarmingTrendVO> theYearBefore;

}
