package com.hot.manage.entity.ywj;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AlarmingTrendTest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<AlarmingTrendVO> thisYear;
	
	private List<AlarmingTrendVO> lastYear;
	
	private List<AlarmingTrendVO> theYearBefore;

}
