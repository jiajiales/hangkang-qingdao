package com.hotcomm.prevention.controller.manage.home;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hotcomm.prevention.bean.mysql.manage.home.HomeAlarmTrend;
import com.hotcomm.prevention.bean.mysql.manage.home.HomeDataForDay;
import com.hotcomm.prevention.bean.mysql.manage.home.HomeTotalData;
import com.hotcomm.prevention.bean.mysql.manage.home.HomeWoTrend;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.manage.home.HomeService;
import com.hotcomm.prevention.utils.ApiResult;

@RestController
@RequestMapping("home")
public class HomeController {

	@Autowired
	HomeService homeService;

	/*
	 * 首页，数据汇总，设备、报警、工单、巡检数据统计
	 */
	@PostMapping("/selectTotalData")
	public ApiResult selectTotalData(Integer userid, Integer module) throws MyException {
		HomeTotalData selectTotalData = homeService.selectTotalData(userid, module);
		return ApiResult.resultInfo("1", "成功", selectTotalData);
	}

	/*
	 * 报警趋势统计
	 */
	@PostMapping("/selectAlarmTrend")
	public ApiResult selectAlarmTrend(Integer userid, Integer type, Integer module) throws MyException {
		List<HomeAlarmTrend> list = homeService.selectAlarmTrend(userid, type, module);
		return ApiResult.resultInfo("1", "成功", list);
	}

	/*
	 * 工单趋势统计
	 */
	@PostMapping("/selectWoTrend")
	public ApiResult selectWoTrend(Integer userid, Integer type, Integer module) throws MyException {
		List<HomeWoTrend> list = homeService.selectWoTrend(userid, type, module);
		return ApiResult.resultInfo("1", "成功", list);
	}

	/*
	 * 当天报警、工单统计
	 */
	@PostMapping("/selectDataForDay")
	public ApiResult selectDataForDay(Integer userid, Integer module) throws MyException {
		List<HomeDataForDay> selectDataForDay = homeService.selectDataForDay(userid, module);
		return ApiResult.resultInfo("1", "成功", selectDataForDay);
	}

}
