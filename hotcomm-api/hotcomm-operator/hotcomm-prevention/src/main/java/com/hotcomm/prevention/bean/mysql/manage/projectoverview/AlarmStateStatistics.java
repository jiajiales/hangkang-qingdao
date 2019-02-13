package com.hotcomm.prevention.bean.mysql.manage.projectoverview;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//报警状态统计
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AlarmStateStatistics implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String stateName;
	private Integer Num;
}
