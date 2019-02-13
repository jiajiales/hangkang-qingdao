package com.hotcomm.prevention.controller.datashow.sj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.datashow.sj.WaterImmersionService;
import com.hotcomm.prevention.utils.ApiResult;

@RestController
@RequestMapping("waterImmersion")
public class WaterImmersionController {

	@Autowired
	private WaterImmersionService waterImmersionService;

	@PostMapping("/selectDevState")
	// ("设备状态图")
	public ApiResult selectDevState(Integer Time, Integer moduleid, Integer userid) throws MyException {
		return ApiResult.resultInfo("1", "成功", waterImmersionService.selectDevState(Time, moduleid, userid));
	}

	@PostMapping("/AlarmHandleForType")
	// ("报警类型统计")
	public ApiResult AlarmHandleForType(Integer Time, Integer moduleid, Integer userid) throws MyException {
		return ApiResult.resultInfo("1", "成功", waterImmersionService.AlarmHandleForType(Time, moduleid, userid));
	}

	// 根据模块ID查询设备电量分布
	@PostMapping("/selectBattery")
	public ApiResult selectBattery(String starttime, String endtime, Integer moduleid, Integer userid)
			throws MyException {
		return ApiResult.resultInfo("1", "成功",
				waterImmersionService.selectBattery(starttime, endtime, moduleid, userid));

	}

	// 根据模块ID查询设备开启次数
	@PostMapping("/selectDevOpenTimes")
	public ApiResult selectDevOpenTimes(Integer moduleid, Integer querytype, Integer userid) throws MyException {

		return ApiResult.resultInfo("1", "成功", waterImmersionService.selectDevOpenTimes(moduleid, querytype, userid));

	}

	// 根据模块ID查询设备老化率
	@PostMapping("/selectDevAgingRate")
	public ApiResult selectDevAgingRate(Integer moduleid, Integer userid) throws MyException {

		return ApiResult.resultInfo("1", "成功", waterImmersionService.selectDevAgingRate(moduleid, userid));

	}

	// 根据模块ID查询单个设备信息
	@PostMapping("/selectOneDevInfo")
	public ApiResult selectOneDevInfo(Integer year, Integer deviceid, Integer mouduleid, Integer userid)
			throws MyException {
		return ApiResult.resultInfo("1", "成功",
				waterImmersionService.selectOneDevInfo(year, deviceid, mouduleid, userid));

	}

	// 查询项目楼层图片
	@PostMapping("/SeleteMap")
	public ApiResult SeleteMap(Integer groupid, Integer userid) throws MyException {
		return ApiResult.resultInfo("1", "成功", waterImmersionService.SeleteMap(groupid, userid));

	}

	// 项目组平面图设备查看
	@PostMapping("/GroupListDev")
	public ApiResult GroupListDev(Integer moduleid, Integer groupid, String site) throws MyException {
		return ApiResult.resultInfo("1", "成功", waterImmersionService.GroupListDev(moduleid, groupid, site));

	}

	// 根据模块ID查询设备损坏率
	@PostMapping("/selectFailureRate")
	public ApiResult selectFailureRate(Integer moduleid, Integer userid, String startTime, String endTime)
			throws MyException {
		return ApiResult.resultInfo("1", "成功",
				waterImmersionService.selectFailureRate(moduleid, userid, startTime, endTime));

	}

	// 根据模块ID和时间查询报警处理
	@PostMapping("/selectDevAlarmHandleByTime")
	public ApiResult selectDevAlarmHandleByTime(Integer moduleid, Integer userid, String startTime, String endTime,
			Integer queryType) throws MyException {
		return ApiResult.resultInfo("1", "成功",
				waterImmersionService.selectDevAlarmHandleByTime(moduleid, userid, startTime, endTime, queryType));

	}

	// 查询设备信息
	@PostMapping("/selectDev")

	public ApiResult selectDev(Integer devid, Integer moduleid) throws MyException {

		return ApiResult.resultInfo("1", "成功", waterImmersionService.selectDev(devid, moduleid));

	}

	// 查询设备报警数量
	@PostMapping("/AlarmList")
	public ApiResult AlarmList(Integer Time, Integer moduleid, Integer userid, Integer devid) throws MyException {
		return ApiResult.resultInfo("1", "成功", waterImmersionService.AlarmList(Time, moduleid, userid, devid));

	}

	// 根据模块ID查询设备报警类型统计(水位设备不适用)
	@PostMapping("/selectDevAlarmHandleType")
	public ApiResult selectDevAlarmHandleType(Integer Time, Integer moduleid, Integer userid) throws MyException {
		return ApiResult.resultInfo("1", "成功", waterImmersionService.selectDevAlarmHandleType(Time, moduleid, userid));

	}

	// 根据模块ID查询不同状态的设备数量(地磁不适用)
	@PostMapping("/selectDevStateCount")
	public ApiResult selectDevStateCount(Integer moduleid, Integer userid, String startTime, String endTime)
			throws MyException {

		return ApiResult.resultInfo("1", "成功",
				waterImmersionService.selectDevStateCount(moduleid, userid, startTime, endTime));

	}

	// 地图标注所有的项目组
	@PostMapping("/selectDevList")
	public ApiResult selectDevList(Integer userid, Integer moduleid, String code) {

		return ApiResult.resultInfo("1", "成功", waterImmersionService.selectDevList(userid, moduleid, code));

	}
}
