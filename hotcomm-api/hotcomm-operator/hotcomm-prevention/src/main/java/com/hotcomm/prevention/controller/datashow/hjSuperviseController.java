package com.hotcomm.prevention.controller.datashow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.datashow.hjSuperviseService;
import com.hotcomm.prevention.utils.ApiResult;

@RestController
@RequestMapping("datashow/hjSupervise")
public class hjSuperviseController {

	@Autowired
	private hjSuperviseService hjSuperviseService;

	// PM设备统计
	@PostMapping("/selectPMGroupData")
	public ApiResult selectPMGroupData(Integer type, Integer userid) throws MyException {
		return ApiResult.resultInfo("1", "执行成功", hjSuperviseService.selectPMGroupData(type, userid));
	}

	// 城市PM值统计
	@PostMapping("/selectPMAvgByCity")
	public ApiResult selectPMAvgByCity(Integer areaid, Integer userid) throws MyException {
		return ApiResult.resultInfo("1", "执行成功", hjSuperviseService.selectPMByCity(areaid, userid));
	}

	// 温湿度趋势图
	@PostMapping("/selectTemperatureAndHumidityTrendChart")
	public ApiResult selectTemperatureAndHumidityTrendChart(Integer type) throws MyException {
		return ApiResult.resultInfo("1", "执行成功", hjSuperviseService.selectTemperatureAndHumidityTrendChart(type));
	}

	// 环境监控图
	@PostMapping("/selectEnvironmentalMonitoringChart")
	public ApiResult selectEnvironmentalMonitoringChart(Integer type) throws MyException {
		return ApiResult.resultInfo("1", "执行成功", hjSuperviseService.selectEnvironmentalMonitoringChart(type));
	}

	// 监测点
	@PostMapping("/selectPMMonitoringPoint")
	public ApiResult selectPMMonitoringPoint() throws MyException {
		return ApiResult.resultInfo("1", "执行成功", hjSuperviseService.selectPMMonitoringPoint());
	}

	// 运行数据概况
	@PostMapping("/selectOperationalDataOverview")
	public ApiResult selectOperationalDataOverview() throws MyException {
		return ApiResult.resultInfo("1", "执行成功", hjSuperviseService.selectOperationalDataOverview());
	}

	// PM楼层信息
	@PostMapping("/selectPMFloor")
	public ApiResult selectPMFloor(Integer groupid) throws MyException {
		return ApiResult.resultInfo("1", "执行成功", hjSuperviseService.selectPMFloor(groupid));
	}

	// PM设备位置信息
	@PostMapping("/selectPMDevInfo")
	public ApiResult selectPMDevInfo(Integer tipid) throws MyException {
		return ApiResult.resultInfo("1", "执行成功", hjSuperviseService.selectPMDevInfo(tipid));
	}
}
