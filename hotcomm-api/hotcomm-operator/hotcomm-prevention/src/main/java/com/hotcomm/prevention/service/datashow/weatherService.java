package com.hotcomm.prevention.service.datashow;

import com.hotcomm.prevention.bean.mysql.datashow.vo.WeatherVo;

public interface weatherService {

	/**
	 * 根据城市名称查看天气情况
	 * @param name
	 * @return
	 */
	WeatherVo selectTheWeatherByCityName(String name);
}
