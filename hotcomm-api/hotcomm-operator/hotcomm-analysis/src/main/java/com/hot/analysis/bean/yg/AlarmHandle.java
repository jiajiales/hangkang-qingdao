package com.hot.analysis.bean.yg;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AlarmHandle  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	private Integer moduleid;//模块id
	
	private String date;//报警时间
	
	private Integer alarmcount;//报警数
	
	private Integer handlecount;//处理数
	
	private Integer falsecount;//误报
}
