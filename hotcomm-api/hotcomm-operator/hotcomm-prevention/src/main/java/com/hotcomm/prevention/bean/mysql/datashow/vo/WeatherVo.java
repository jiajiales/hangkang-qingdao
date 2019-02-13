package com.hotcomm.prevention.bean.mysql.datashow.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class WeatherVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 城市
	 */
	private String city;

	/**
	 * 日期
	 */
	private String date;
	
	/**
	 * 星期
	 */
	private String week;
	
	/**
	 * 气温
	 */
	private String temp;

	/**
	 * 天气
	 */
	private String weather;
	
	/**
	 * 最高气温
	 */
	private String temphigh;

	/**
	 * 最低气温
	 */
	private String templow;

	/**
	 * 相对湿度
	 */
	private String humidity;

	/**
	 * 空气质量
	 */
	private String quality;

	/**
	 * 风向
	 */
	private String winddirect;

	/**
	 * 风级
	 */
	private String windpower;

	/**
	 * 日出
	 */
	private String sunrise;

	/**
	 * 日落
	 */
	private String sunset;
}
