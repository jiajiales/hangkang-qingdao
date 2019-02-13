package com.hot.analysis.controller.sj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.hot.analysis.exception.MyException;
import com.hot.analysis.service.sj.LKTSJService;
import com.hot.analysis.utils.ApiResult;

@RestController
public class LKTSJController {

	@Autowired
	private LKTSJService lktsjService;

	@PostMapping("/SJState")
	@ResponseBody
	// ("设备状态图")
	public ApiResult SJState(Integer Time, Integer moduleID, Integer userID) throws MyException {
		return ApiResult.resultInfo("1", "成功", lktsjService.LKTSJDevState(Time, moduleID, userID));
	}

	@PostMapping("/SJAlarmHandleForType")
	@ResponseBody
	// ("报警类型统计")
	public ApiResult SJAlarmHandleForType(Integer Time, Integer moduleID, Integer userID) throws MyException {
		return ApiResult.resultInfo("1", "成功", lktsjService.LKTSJAlarmList(Time, moduleID, userID));
	}

}
