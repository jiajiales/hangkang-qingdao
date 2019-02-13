package com.hot.analysis.controller.ywj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.hot.analysis.exception.MyException;
import com.hot.analysis.service.ywj.LKTYWJService;
import com.hot.analysis.utils.ApiResult;

@RestController
public class YWJController {

	@Autowired
	private LKTYWJService lktywjService;

	@PostMapping("/selectAlramNum")
	@ResponseBody
	// ("报警频率统计")
	public ApiResult selectAlramNum(String starttime, String endtime, Integer moduleid, Integer querytype,
			Integer userid) throws MyException {
		return ApiResult.resultInfo("1", "成功", lktywjService.LKTYWJselectFlipNum(starttime, endtime, moduleid, userid));
	}

	@PostMapping("/selectWeatherInfoByCity")
	@ResponseBody
	// ("根据城市查询天气预报信息")
	public ApiResult selectWeatherInfoByCity(String city) throws MyException {
		return ApiResult.resultInfo("1", "成功", lktywjService.selectWeatherInfoByCity(city));
	}

	@PostMapping("/selectWeatherInfoByIP")
	@ResponseBody
	// ("根据IP地址查询天气预报信息")
	public ApiResult selectWeatherInfoByIP(String ip) throws MyException {
		return ApiResult.resultInfo("1", "成功", lktywjService.selectWeatherInfoByIP(ip));
	}

	@PostMapping("/selectAlarmType")
	@ResponseBody
	// ("报警类型统计")
	public ApiResult selectAlarmType(Integer time, Integer moduleid, Integer userid) throws MyException {
		return ApiResult.resultInfo("1", "成功", lktywjService.YWJAlarmType(time, moduleid, userid));
	}
}
