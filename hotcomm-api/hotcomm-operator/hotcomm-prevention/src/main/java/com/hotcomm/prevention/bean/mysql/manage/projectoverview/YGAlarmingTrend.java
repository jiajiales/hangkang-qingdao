package com.hotcomm.prevention.bean.mysql.manage.projectoverview;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class YGAlarmingTrend implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer alarmcount;
	
	private String date;
	
	private Integer yearAgo;

}
