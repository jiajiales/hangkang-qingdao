package com.hot.analysis.bean.yg;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SomkeAlarmNum  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String stateName;
	
	private Integer Num;
	
	
	
}
