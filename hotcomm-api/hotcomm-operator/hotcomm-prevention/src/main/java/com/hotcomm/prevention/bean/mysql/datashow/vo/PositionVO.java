package com.hotcomm.prevention.bean.mysql.datashow.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class PositionVO {
	
	private String addvb;//行政区域简称
	private String areacode;//行政区划编码
	private Double lng;
	private Double lat;
	private String sttp;//类型，PP,ZZ,PZ,SP,WZ
	private Integer ylStatus;//降雨量状态。0:空值；1:正常；2：警戒；3：报警
	private Integer swStatus;//水位状态。0:空值；1:正常；2：警戒；3：报警
}
