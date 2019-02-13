package com.hotcomm.prevention.bean.mysql.datashow.jg;


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
	
	private String code;
	
	private Integer  areaid;
	
	
}
