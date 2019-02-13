package com.hot.analysis.bean.jg;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class JGTypequery {

	private Integer moduleid;
	
	private Integer groupid;
	
	private String site;
	
	private String purpose;
	
	private String loadbear;
	
}
