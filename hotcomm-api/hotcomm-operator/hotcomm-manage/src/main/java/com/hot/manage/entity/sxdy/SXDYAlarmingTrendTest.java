package com.hot.manage.entity.sxdy;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SXDYAlarmingTrendTest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<SXDYAlarmingTrendVO> thisYear;
	
	private List<SXDYAlarmingTrendVO> lastYear;
	
	private List<SXDYAlarmingTrendVO> theYearBefore;

}
