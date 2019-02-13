package com.hotcomm.prevention.bean.mysql.datashow.jg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AreaVO {

	private Integer areaid;//地区id
	
	private String areaname;//行政区划名称
	
	private double lng;//经度
	
	private double lat;//维度
	
	private Integer state;
	
	private Integer alarmcount;//报警数
	
	private Integer failurecount;//故障数
	
	private Integer normalcount;//正常数
	
	private Integer othercount;//其他数
}
