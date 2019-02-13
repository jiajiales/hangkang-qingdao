package com.hotcomm.prevention.bean.mysql.datashow.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class MaterialVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String lat;
	private String lng;
	/**
	 * 物资点名称
	 */
	private String materialname;
	
	/**
	 * 行政区id
	 */
	private Integer areaid;
	/**
	 * 行政区名
	 */
	private String areaname;
}
