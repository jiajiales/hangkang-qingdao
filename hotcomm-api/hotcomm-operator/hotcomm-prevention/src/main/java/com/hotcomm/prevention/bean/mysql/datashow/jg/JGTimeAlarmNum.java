package com.hotcomm.prevention.bean.mysql.datashow.jg;

import java.io.Serializable;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class JGTimeAlarmNum implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer moduleid;//模块id
	
	private Integer alarmtype;//报警类型
	
	private String hourpart;//报警时段
	
	private Integer count;//数量


}
