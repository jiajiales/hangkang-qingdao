package com.hotcomm.prevention.controller.datashow.dc;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.prevention.bean.mysql.datashow.dc.DevUseRate;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.datashow.dc.DcService;
import com.hotcomm.prevention.utils.ApiResult;

@RestController
public class DcController {

	@Autowired
	DcService dcService;

	// 终端设备数量、停车数量、空闲数量、故障设备数量/设备状态图
	@PostMapping("/dc/DevStateNum")
	public ApiResult DevStateNum(Integer userid) {
		return ApiResult.resultInfo("1", "获取成功", dcService.DevStateNum(userid));
	}

	// 车位利用率
	@PostMapping("/dc/DevUseRate")
	public List<DevUseRate> DevUseRate(Integer querytype, Integer userid) throws MyException {
		return dcService.DevUseRate(querytype, userid);
	}

	// 停车时段分布
	@PostMapping("/dc/DCParkingSlotsData")
	public ApiResult DCParkingSlotsData(Integer userid) {
		return ApiResult.resultInfo("1", "获取成功", dcService.DCParkingSlotsData(userid));
	}

	// 最近三十天收入
	@PostMapping("/dc/DcIncome")
	public ApiResult DcIncome(Integer userid) {
		return ApiResult.resultInfo("1", "获取成功", dcService.DcIncome(userid));
	}

	// 查询单个地磁设备信息
	@PostMapping("/dc/DcInfo")
	public ApiResult DcInfo(Integer userid, Integer devid) throws ParseException {
		return ApiResult.resultInfo("1", "获取成功", dcService.selectSummary(userid, devid));
	}

	// 查询设备状态图
	@PostMapping("/dc/CountState")
	public ApiResult selectCountState(Integer userid) {
		return ApiResult.resultInfo("1", "获取成功", dcService.selectCountState(userid));
	}

}
