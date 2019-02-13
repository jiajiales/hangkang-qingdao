package com.hotcomm.prevention.bean.mysql.manage.projectoverview;

import java.io.Serializable;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


//报警趋势
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AlarmTendency implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String TheDate;
	
	private Integer AlarmTime;
	
	private Integer differ;
	
}
