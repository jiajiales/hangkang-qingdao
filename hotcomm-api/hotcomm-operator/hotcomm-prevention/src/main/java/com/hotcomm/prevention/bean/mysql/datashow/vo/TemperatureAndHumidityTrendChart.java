package com.hotcomm.prevention.bean.mysql.datashow.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TemperatureAndHumidityTrendChart implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String TheDate;
	private Double humidity;
	private Double temp;
}
