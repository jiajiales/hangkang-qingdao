package com.hot.manage.controller.yg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.annotation.Permissions;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.yg.param.TAlarmDispose;
import com.hot.manage.entity.yg.vo.YGAlarmPartic;
import com.hot.manage.entity.yg.vo.YGHistoricalDate;
import com.hot.manage.entity.yg.vo.YGHistoricalStateDate;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.yg.YGAlarmParticService;
import com.hot.manage.service.yg.YGAppService;
import com.hot.manage.service.yg.YGDeviceInfoService;
import com.hot.manage.utils.ApiResult;
import com.hot.manage.utils.ConverUtil;

@RestController
public class YGAlarmController {

	@Autowired
	private YGAlarmParticService ygAlarmParticService;

	@Autowired
	private YGDeviceInfoService deviceInfoService;

	@Autowired
	private YGAppService ygAppService;

	@PostMapping("YGDevice/selectYGAlarmParticForState")
	@Permissions("item:yg:read:AlarmParticForState")
	// ("根据处理状态，报警原因筛选报警记录(分页)")
	public ApiResult selectAllForState(Integer handlestate, Integer stateid, Integer pageNum,
			Integer pageSize, Integer userid) throws MyException {
		PageHelper.startPage(pageNum, pageSize);
		Page<YGAlarmPartic> page = ygAlarmParticService.selectForState(handlestate, stateid);
		List<YGAlarmPartic> list = ConverUtil.converPage(page, YGAlarmPartic.class);
		PageInfo<YGAlarmPartic> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功" ,pageinfo);
		return resultInfo;

	}
	
	@PostMapping("YGDevice/YGAlarmTypeCount")
	@Permissions("item:yg:read:YGAlarmTypeCount")
	// ("报警类型")
	public ApiResult YGAlarmTypeCount(Integer userid, Integer year) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功" ,ygAlarmParticService.YGAlarmTypeCount(userid, year));
		return resultInfo;
	}

	
	@PostMapping("YGDevice/smokeAlarmingTrend")
	@Permissions("item:yg:read:smokeAlarmingTrend")
	// ("报警趋势图")
	public ApiResult selectSmokeAlarmingTrend(Integer userid, Integer moduleid,Integer queryType,Integer groupid) throws MyException {
		if (queryType==1) {
			ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功" ,ygAlarmParticService.smokeAlarmingTrendDay(userid, moduleid, queryType,groupid));
			return resultInfo;
		}else if (queryType==2) {
			ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功" ,ygAlarmParticService.smokeAlarmingTrendThreeYear2(userid, moduleid,queryType,groupid));
			return resultInfo;
		}else if (queryType==3) {
			ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功" ,ygAlarmParticService.smokeAlarmingTrendHalfYear(userid, moduleid, queryType,groupid));
			return resultInfo;
		}else {
			return null;
		}
	}
	
	@PostMapping("YGDevice/smokeAlarmingTrendByGroup")
	@Permissions("item:yg:read:smokeAlarmingTrendByGroup")
	// ("组报警趋势图")
	public ApiResult smokeAlarmingTrendByGroup(Integer userid, Integer moduleid,Integer queryType,Integer groupid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功",
				ygAlarmParticService.smokeAlarmingTrendByGroup(userid, moduleid, queryType, groupid));
		return resultInfo;
	}
	
	@PostMapping("YGDevice/YGselectDevAlarmHandleByTime")
	@Permissions("item:yg:read:YGselectDevAlarmHandleByTime")
	// ("报警处理统计")
	public ApiResult YGselectDevAlarmHandleByTime(Integer queryType, String startTime, String endTime,Integer userid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功" ,ygAlarmParticService.YGselectDevAlarmHandleByTime(queryType, startTime, endTime,userid));
		return resultInfo;
	}

	@PostMapping("YGDevice/selectYGAlarmParticForTime")
	@Permissions("item:yg:read:AlarmParticForTime")
	// ("根据报警时间，等筛选报警记录(分页)")
	public ApiResult selectAllForTime(String startTime, String endTime, String message, Integer pageNum,
			Integer pageSize, Integer userid) throws MyException {
		PageHelper.startPage(pageNum, pageSize);
		Page<YGAlarmPartic> page = ygAlarmParticService.selectForTime(startTime, endTime, message);
		List<YGAlarmPartic> list = ConverUtil.converPage(page, YGAlarmPartic.class);
		PageInfo<YGAlarmPartic> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功" ,pageinfo);
		return resultInfo;

	}

	@PostMapping("YGDevice/selectHistoricalDate")
	@Permissions("item:yg:read:HistoricalDate")
	// ("报警历史数据")
	public ApiResult selectHistoricalDate(Integer userid, Integer deviceid, Integer theyear)
			throws MyException {
		List<YGHistoricalDate> list = ygAlarmParticService.selectHistoricalDate(deviceid, theyear);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功" ,list);
		return resultInfo;
	}

	@PostMapping("YGDevice/selectHistoricalStateDate")
	@Permissions("item:yg:read:HistoricalStateDate")
	// ("报警类型历史数据")
	public ApiResult selectHistoricalStateDate(Integer userid, Integer deviceid, Integer theyear)
			throws MyException {
		List<YGHistoricalStateDate> list = ygAlarmParticService.selectHistoricalStateDate(deviceid, theyear);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功" ,list);
		return resultInfo;
	}

	@PostMapping("YGDevice/selectYGDeviceInfo")
	@Permissions("item:yg:read:DeviceInfo")
	// ("设备报警信息与设备信息")
	public ApiResult selectYGDeviceInfo(Integer userid, Integer ygalarmid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功" ,deviceInfoService.selectYGDeviceInfo(ygalarmid));
		return resultInfo;

	}

	@PostMapping("YGDevice/selectDevInfoDispose")
	@Permissions("item:yg:read:DevInfoDispose")
	public ApiResult selectDevInfoDispose(Integer userid, Integer ygalarmid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功" ,deviceInfoService.selectDevInfoDispose(ygalarmid));
		return resultInfo;

	}

	@PostMapping("YGDevice/selectDevInfoFinish")
	@Permissions("item:yg:read:DevInfoFinish")
	// ("设备报警详情（处理完成）")
	public ApiResult selectDevInfoFinish(Integer userid, Integer ygalarmid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功" ,deviceInfoService.selectDevInfoFinish(ygalarmid));
		return resultInfo;


	}

	@PostMapping("YGDevice/YGAPPDevDispose")
	@Permissions("item:yg:update:DevDispose")
	public ApiResult YGDevDispose(Integer userid, TAlarmDispose taAlarmDispose) throws MyException {
		ApiResult resultInfo = null;
		Integer innerobject = ygAppService.DevInfoDispose(taAlarmDispose);
		if (innerobject == 1) {
			resultInfo = ApiResult.resultInfo("1", "成功！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("0", "失败！！",null);
		}
		return resultInfo;
	}

	@PostMapping("YGDevice/closeYGAlarm")
	@Permissions("item:yg:update:closeAlarm")
	public ApiResult closeYGAlarm(Integer alarmid) throws MyException {
		ApiResult resultInfo = null;
		Integer innerobject = ygAlarmParticService.closeYGAlarm(alarmid);
		if (innerobject == 1) {
			resultInfo = ApiResult.resultInfo("1", "成功！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("0", "失败！！",null);
		}
		return resultInfo;
	}
}
