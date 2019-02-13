package com.hot.manage.entity.common.groupgk;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
