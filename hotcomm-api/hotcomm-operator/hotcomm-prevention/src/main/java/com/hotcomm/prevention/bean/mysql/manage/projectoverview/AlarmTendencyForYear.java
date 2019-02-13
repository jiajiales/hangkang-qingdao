package com.hotcomm.prevention.bean.mysql.manage.projectoverview;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AlarmTendencyForYear implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer year;
	
	private List<AlarmTendency> alarmtendency;
	
	
}
