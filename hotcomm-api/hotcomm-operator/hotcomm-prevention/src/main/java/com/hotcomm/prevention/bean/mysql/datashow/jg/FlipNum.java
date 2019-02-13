package com.hotcomm.prevention.bean.mysql.datashow.jg;

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


	

	
	private String date;
	
	private Integer count;
	
}
