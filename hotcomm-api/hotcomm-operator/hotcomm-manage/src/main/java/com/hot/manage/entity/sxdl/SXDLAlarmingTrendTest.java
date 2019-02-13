package com.hot.manage.entity.sxdl;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SXDLAlarmingTrendTest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<SXDLAlarmingTrendVO> thisYear;
	
	private List<SXDLAlarmingTrendVO> lastYear;
	
	private List<SXDLAlarmingTrendVO> theYearBefore;

}
