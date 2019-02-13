package com.hotcomm.prevention.controller.manage.projectoverview;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.prevention.bean.mysql.manage.projectoverview.Groupgkcount;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.alarmcount_class;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.alarmcount_day;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.alarmcount_month;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.manage.projectoverview.GroupgkService;
import com.hotcomm.prevention.utils.ApiResult;

@RestController
public class GroupgkController {

	@Autowired
	private GroupgkService groupgkService;

	Date d = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@SuppressWarnings("unused")
	private String etime = sdf.format(d);

	/**
	 * 项目概况计数
	 * 
	 * @param userid
	 * @param moduleid
	 * @return
	 */
	@PostMapping("groupgk/count")
	public ApiResult groupgkcount(Integer userid, Integer moduleid) {
		Groupgkcount list = groupgkService.groupgkcount(userid, moduleid);
		int t1 = (int) (System.currentTimeMillis() / 1000);
		int t2 = 1525104000;
		list.setSyscount((t1 - t2) / 3600 / 24);

		return ApiResult.resultInfo("1", "成功", list);
	}

	/**
	 * 每月报警数量
	 * 
	 * @param userid
	 * @param moduleid
	 * @param yyyy
	 * @return
	 */
	@PostMapping("groupgk/alarmcounttotalmonth")
	public ApiResult select_alarm_count_month(Integer userid, Integer moduleid, Integer yyyy) {
		List<alarmcount_month> list = groupgkService.select_alarm_count_month(userid, moduleid, yyyy);
		return ApiResult.resultInfo("1", "成功", list);

	}

	/**
	 * 近7天的报警
	 * 
	 * @param userid
	 * @param moduleid
	 * @return
	 */
	@PostMapping("groupgk/alarmcounttotalday")
	public ApiResult select_alarm_count_day(Integer userid, Integer moduleid) {
		List<alarmcount_day> list = groupgkService.select_alarm_count_day(userid, moduleid);
		return ApiResult.resultInfo("1", "成功", list);
	}

	/**
	 * 报警类型统计
	 * 
	 * @param userid
	 * @param moduleid
	 * @param yyyy
	 * @return
	 */
	@PostMapping("groupgk/alarmcountclass")
	public ApiResult select_alarm_count_class(Integer userid, Integer moduleid, Integer yyyy) {
		List<alarmcount_class> list = groupgkService.select_alarm_count_class(userid, moduleid, yyyy);
		return ApiResult.resultInfo("1", "成功", list);
	}

	/**
	 * 可燃气报警趋势图
	 * 
	 * @return
	 * @throws MyException
	 */
	@PostMapping("krq/selectKRQAlarmTendency")
	public ApiResult selectKRQAlarmTendency(Integer userid, Integer type, Integer groupid) throws MyException {
		if (type == 1) {
			return ApiResult.resultInfo("1", "执行成功", groupgkService.selectKRQAlarmForDay(groupid));
		} else if (type == 3) {
			return ApiResult.resultInfo("1", "执行成功", groupgkService.selectKRQAlarmForMonth(groupid));
		} else if (type == 2) {
			return ApiResult.resultInfo("1", "执行成功", groupgkService.selectKRQAlarmForThreeYear(groupid));
		} else {
			return ApiResult.resultInfo("0", "执行失败,请选择筛选的单位", null);
		}
	}

	/**
	 * 垃圾桶报警趋势图
	 * 
	 * @return
	 * @throws MyException
	 */

	// 报警趋势
	@PostMapping("ljtdev/selectAlarmTendency")
	public ApiResult selectAlarmTendency(Integer userid, Integer type, Integer groupid) throws MyException {
		if (type == 1) {
			return ApiResult.resultInfo("1", "执行成功", groupgkService.selectAlarmForDay(groupid));
		} else if (type == 3) {
			return ApiResult.resultInfo("1", "执行成功", groupgkService.selectAlarmForMonth(groupid));
		} else if (type == 2) {
			return ApiResult.resultInfo("1", "执行成功", groupgkService.selectAlarmForThreeYear(groupid));
		} else {
			return ApiResult.resultInfo("0", "执行失败,请选择筛选的单位", null);
		}
	}

	/**
	 * 三相电流报警趋势图
	 * 
	 * @return
	 * @throws MyException
	 */

	// 报警趋势
	@PostMapping("SXDL/AlarmingTrendForSXDL")
	public ApiResult AlarmingTrendForSXDL(Integer queryType, Integer userid, Integer groupid) throws MyException {
		return ApiResult.resultInfo("1", "成功", groupgkService.AlarmingTrendForSXDL(queryType, userid, groupid));
	}

	/**
	 * 三相电压报警趋势图
	 * 
	 * @return
	 * @throws MyException
	 */

	@PostMapping("SXDY/AlarmingTrendForSXDY")
	// 报警趋势
	public ApiResult AlarmingTrendForSXDY(Integer queryType, Integer userid, Integer groupid) throws MyException {
		return ApiResult.resultInfo("1", "成功", groupgkService.AlarmingTrendForSXDY(queryType, userid, groupid));
	}

	/**
	 * 水压报警趋势图
	 * 
	 * @return
	 * @throws MyException
	 */

	// 报警趋势
	@PostMapping("sy/selectSYAlarmTendency")
	public ApiResult selectSYAlarmTendency(Integer userid, Integer type, Integer groupid) throws MyException {
		if (type == 1) {
			return ApiResult.resultInfo("1", "执行成功", groupgkService.selectSYAlarmForDay(groupid));
		} else if (type == 3) {
			return ApiResult.resultInfo("1", "执行成功", groupgkService.selectSYAlarmForMonth(groupid));
		} else if (type == 2) {
			return ApiResult.resultInfo("1", "执行成功", groupgkService.selectSYAlarmForThreeYear(groupid));
		} else {
			return ApiResult.resultInfo("0", "执行失败,请选择筛选的单位", null);
		}
	}

	/**
	 * 剩余电流趋势图
	 * 
	 * @return
	 * @throws MyException
	 */
	@PostMapping("sydl/AlarmingTrendForSYDL")
	public ApiResult AlarmingTrendForSYDL(Integer queryType, Integer userid, Integer groupid) throws MyException {

		return ApiResult.resultInfo("1", "成功!!", groupgkService.AlarmingTrendForSYDL(queryType, userid, groupid));

	}

	/**
	 * 烟感趋势图
	 * 
	 * @return
	 * @throws MyException
	 */
	@PostMapping("YGDevice/smokeAlarmingTrend")
	public ApiResult selectSmokeAlarmingTrend(Integer userid, Integer moduleid, Integer queryType, Integer groupid)
			throws MyException {
		if (queryType == 1) {
			ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功",
					groupgkService.smokeAlarmingTrendDay(userid, moduleid, queryType, groupid));
			return resultInfo;
		} else if (queryType == 2) {
			ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功",
					groupgkService.smokeAlarmingTrendThreeYear2(userid, moduleid, queryType, groupid));
			return resultInfo;
		} else if (queryType == 3) {
			ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功",
					groupgkService.smokeAlarmingTrendHalfYear(userid, moduleid, queryType, groupid));
			return resultInfo;
		} else {
			return null;
		}
	}

	/**
	 * 液位计趋势图
	 * 
	 * @return
	 * @throws MyException
	 */
	@PostMapping("ywj/AlarmingTrendForWhichHasBoundaryValue")
	public ApiResult AlarmingTrendForWhichHasBoundaryValue(Integer queryType, Integer userid, Integer groupid) {
		return ApiResult.resultInfo("1", "成功",
				groupgkService.AlarmingTrendForWhichHasBoundaryValue(queryType, userid, groupid));
	}

	/**
	 * 公共模快,报警处理类型
	 * 
	 * @return
	 * @throws MyException
	 */
	@PostMapping("Device/selectAlarmNums")
	public ApiResult selectAlarmTypeNums(Integer moduleID, Integer userid, Integer queryType) throws MyException {
		ApiResult apiResult = new ApiResult("1", "执行成功", groupgkService.selectAlarmNums(moduleID, userid, queryType));
		return apiResult;
	}

	/**
	 * 公共模快,所有报警处理率
	 * 
	 * @return
	 * @throws MyException
	 */
	@PostMapping("Device/selectAlarmHandleNums")
	public ApiResult selectAlarmHandleNums(Integer moduleID, Integer userid, Integer queryType) throws MyException {
		ApiResult apiResult = new ApiResult("1", "执行成功",
				groupgkService.selectAlarmHandleNums(moduleID, userid, queryType));
		return apiResult;
	}

}