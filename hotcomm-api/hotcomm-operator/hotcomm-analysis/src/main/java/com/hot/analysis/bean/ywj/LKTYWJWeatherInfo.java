package com.hot.analysis.bean.ywj;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTYWJWeatherInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// (value = "城市")
	private String city;
	
	// (value = "日期")
	private String date;

	// (value = "周")
	private String week;

	// (value = "天气")
	private String weather;

	// (value = "温度")
	private Integer temp;

	// (value = "最低温度")
	private Integer templow;

	// (value = "最高温度")
	private Integer temphigh;

	// (value = "风向")
	private String winddirect;

	// (value = "风力")
	private String windpower;

	// (value = "湿度")
	private String humidity;
}
