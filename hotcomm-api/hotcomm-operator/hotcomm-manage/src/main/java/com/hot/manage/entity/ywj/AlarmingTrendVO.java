package com.hot.manage.entity.ywj;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AlarmingTrendVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer maxalarmcount;
	
	private Integer minalarmcount;
	
	private String date;
	
	private Integer yearAgo;

}