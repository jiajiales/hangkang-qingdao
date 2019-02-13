package com.hot.manage.controller.common;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hot.manage.annotation.Permissions;
import com.hot.manage.entity.common.home.HomeAlarmTrend;
import com.hot.manage.entity.common.home.HomeDataForDay;
import com.hot.manage.entity.common.home.HomeTotalData;
import com.hot.manage.entity.common.home.HomeWoTrend;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.common.home.HomeService;
import com.hot.manage.utils.ApiResult;

@RestController
public class HomeController {

	@Autowired
	HomeService homeService;

	/**
	 * 首页，数据汇总，设备、报警、工单、巡检数据统计
	 * 
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/home/selectTotalData")
	@Permissions("home:selectTotalData:query")
	public ApiResult selectTotalData(Integer userid) throws MyException {
		HomeTotalData selectTotalData = homeService.selectTotalData(userid);
		return ApiResult.resultInfo("1", "成功", selectTotalData);
	}

	/**
	 * 报警趋势统计
	 * 
	 * @param userid
	 * @param type
	 *            1：最近7天；2：最近一个月
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/home/selectAlarmTrend")
	@Permissions("home:selectAlarmTrend:query")
	public ApiResult selectAlarmTrend(Integer userid, Integer type) throws MyException {
		List<HomeAlarmTrend> list = homeService.selectAlarmTrend(userid, type);
		return ApiResult.resultInfo("1", "成功", list);
	}

	/**
	 * 工单趋势统计
	 * 
	 * @param userid
	 * @param type
	 *            1：最近7天；2：最近一个月
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/home/selectWoTrend")
	@Permissions("home:selectWoTrend:query")
	public ApiResult selectWoTrend(Integer userid, Integer type) throws MyException {
		List<HomeWoTrend> list = homeService.selectWoTrend(userid, type);
		return ApiResult.resultInfo("1", "成功", list);
	}

	/**
	 * 当天报警、工单统计
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/home/selectDataForDay")
	@Permissions("home:selectDataForDay:query")
	public ApiResult selectDataForDay(Integer userid) throws MyException {
		List<HomeDataForDay> selectDataForDay = homeService.selectDataForDay(userid);
		return ApiResult.resultInfo("1", "成功", selectDataForDay);
	}

}
