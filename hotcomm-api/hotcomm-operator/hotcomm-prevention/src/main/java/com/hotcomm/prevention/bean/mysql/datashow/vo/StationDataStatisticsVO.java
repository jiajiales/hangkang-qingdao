package com.hotcomm.prevention.bean.mysql.datashow.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class StationDataStatisticsVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nowDrp;

	private String drpMax;

	private String drpMin;

	private String drpCount;

	private String drpAvg;

	private String ZMax;

	private String ZMin;

	private String ZAvg;

	private String nowZ;

}
