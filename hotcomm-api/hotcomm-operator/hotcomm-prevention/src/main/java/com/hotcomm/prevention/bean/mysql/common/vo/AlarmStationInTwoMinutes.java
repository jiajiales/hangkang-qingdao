package com.hotcomm.prevention.bean.mysql.common.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AlarmStationInTwoMinutes {
	private String STCD;
	
	private String STNM;

	private String STTP;

	private String DRP;
	
	private String Z;
	
	private String devnum;
}
