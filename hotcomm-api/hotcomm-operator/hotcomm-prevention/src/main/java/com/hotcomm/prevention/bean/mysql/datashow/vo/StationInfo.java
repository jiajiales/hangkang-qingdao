package com.hotcomm.prevention.bean.mysql.datashow.vo;

import javax.persistence.Column;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class StationInfo {
	
	private String stcd;//设备编码
	private String stnm;//站点名称
	private String adcd;//行政编码
	private String sttp;//设备类型
	private String lgtd;//经度
	private String lttd;//维度
	private String code;//地址
	private String tm;//更新时间
	private String z;//实时水位
	private String drp;//实时雨量
	@Column(name="swstatus")
	private Integer swstatus;//水位状态 1：正常 2：警戒 3：报警
	@Column(name="ylstatus")
	private Integer ylstatus;//雨量状态 1：正常 2：警戒 3：报警

}
