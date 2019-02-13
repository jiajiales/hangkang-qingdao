package com.hotcomm.prevention.service.common;


import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ImportDevice implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mac;

	private Integer moduleid;
	
	private Integer adduserid;

	private Integer lessalarmvalue;
	
	private Integer topalarmvalue;
	
	private String addTime;

}
