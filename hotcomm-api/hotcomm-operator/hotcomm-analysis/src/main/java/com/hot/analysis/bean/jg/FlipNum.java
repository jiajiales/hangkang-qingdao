package com.hot.analysis.bean.jg;



import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class FlipNum  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer moduleid;
	
	private Integer querytype;
	
	private Integer devid;
	
	private Integer count;
	
	private String devname;
	
	private Double lat;
	
	private Double lng;
}
