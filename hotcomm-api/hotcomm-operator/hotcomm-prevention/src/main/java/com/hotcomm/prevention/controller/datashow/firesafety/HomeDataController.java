package com.hotcomm.prevention.controller.datashow.firesafety;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.datashow.weatherService;
import com.hotcomm.prevention.service.datashow.firesafety.HomeDataServer;
import com.hotcomm.prevention.utils.ApiResult;

@RestController
@RequestMapping("datashow/firesafety/HomeData")
public class HomeDataController {

	@Autowired
	private HomeDataServer dataServer;
	@Autowired
	private weatherService weather;

	@PostMapping("/selectGroupData")
	// 终端数据
	public ApiResult selectGroupData(Integer userid, String moduleid) throws MyException {
		return ApiResult.resultInfo("1", "执行成功", dataServer.selectGroupData(userid, moduleid));
	}

	@PostMapping("/selectGroupForMap")
	// 地图数据
	public ApiResult selectGroupForMap(Integer userid, String moduleid) throws MyException {
		return ApiResult.resultInfo("1", "执行成功", dataServer.selectGroupForMap(userid, moduleid));
	}

	@PostMapping("/selectDevInform")
	// 设备实时报警通知
	public ApiResult selectDevInform(String moduleid) throws MyException {
		return ApiResult.resultInfo("1", "执行成功", dataServer.selectDevInform(moduleid));
	}

	@PostMapping("/selectOperationalDataOverview")
	// 运行数据概况
	public ApiResult selectOperationalDataOverview() throws MyException {
		return ApiResult.resultInfo("1", "执行成功", dataServer.selectOperationalDataOverview());
	}

	@PostMapping("/selectAlarmTrend")
	// 设备报警趋势图
	public ApiResult selectAlarmTrend(Integer type, String moduleid) throws MyException {
		return ApiResult.resultInfo("1", "执行成功", dataServer.selectAlarmTrend(type, moduleid));
	}
	
	@PostMapping("/selectAalarmIndex")
	// 设备报警趋势图
	public ApiResult selectAalarmIndex() throws MyException {
		return ApiResult.resultInfo("1", "执行成功", dataServer.selectAalarmIndex());
	}
	@PostMapping("/selectTheWeather")
	public ApiResult selectTheWeather(String name)throws MyException{
		return ApiResult.resultInfo("1", "执行成功", weather.selectTheWeatherByCityName(name));
	}
}
