package com.hot.manage.entity.common;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AllDevByGroupID implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String x;
	
	private String y;

	private Integer state;
	
	private String code;

	private String devnum;
	
	private Integer battery;

	private String contacts;
	
	private String lng;

	private String lat;

}
