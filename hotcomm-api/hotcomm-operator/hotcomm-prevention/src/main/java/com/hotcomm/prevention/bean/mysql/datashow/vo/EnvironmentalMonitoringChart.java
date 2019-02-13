package com.hotcomm.prevention.bean.mysql.datashow.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class EnvironmentalMonitoringChart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String TheDate;
	private Double pm25;
	private Double noiseval;
}
