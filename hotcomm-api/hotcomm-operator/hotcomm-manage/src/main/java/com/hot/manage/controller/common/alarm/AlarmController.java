package com.hot.manage.controller.common.alarm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.annotation.Permissions;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.common.alarm.AlarmHandleParam;
import com.hot.manage.entity.common.alarm.AlarmListParam;
import com.hot.manage.entity.common.alarm.AlarmListVo;
import com.hot.manage.entity.common.alarm.AlarmState;
import com.hot.manage.entity.common.alarm.AlarmStateParam;
import com.hot.manage.entity.common.alarm.AppAlarmList;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.common.alarm.AlarmService;
import com.hot.manage.utils.ApiResult;
import com.hot.manage.utils.ConverUtil;

@RestController
@RequestMapping("Alarm")
public class AlarmController {

	@Autowired
	private AlarmService alarmService;

	// 根据条件筛选报警记录
	@PostMapping("/selectAlarmList")
	@Permissions("Alarm:selectAlarmList:query")
	public ApiResult selectAlarmList(Integer userid, Integer moduleid, Integer pageNum, Integer pageSize,
			AlarmListParam alarmListParam) throws MyException {
		Page<AlarmListVo> page = alarmService.selectAlarmList(pageNum, pageSize, userid, moduleid, alarmListParam);
		ApiResult resultInfo = null;
		if (page != null) {
			List<AlarmListVo> list = ConverUtil.converPage(page, AlarmListVo.class);
			PageInfo<AlarmListVo> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(),
					list);
			resultInfo = ApiResult.resultInfo("1", "执行成功", pageinfo);
		} else {
			resultInfo = ApiResult.resultInfo("1", "执行成功", null);
		}
		return resultInfo;
	}

	// 查询所有报警设备、报警信息
	@PostMapping("/selectAlarmMpas")
	@Permissions("Alarm:selectAlarmMpas:query")
	public ApiResult selectAlarmMpas(Integer userid, Integer moduleid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", alarmService.selectAlarmMaps(userid, moduleid));
		return resultInfo;
	}

	// 根据id 模块id 关闭报警设备
	@PostMapping("/closealarmById")
	@Permissions("Alarm:closealarmById:update")
	public ApiResult closealarmById(Integer userid, Integer alarmid, Integer moduleid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", alarmService.closealarmById(alarmid, moduleid));
		return resultInfo;
	}

	// 查询设备报警历史数据
	@PostMapping("/selectHistoricalDate")
	@Permissions("Alarm:selectHistoricalDate:query")
	public ApiResult selectHistoricalDate(Integer userid, Integer deviceid, Integer Theyear, Integer moduleid)
			throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功",
				alarmService.selectHistoricalDate(deviceid, Theyear, moduleid));
		return resultInfo;

	}

	// 根据设备id与年份查询历史报警类型
	@PostMapping("/selectHistoricalStateDate")
	@Permissions("Alarm:selectHistoricalStateDate:query")
	public ApiResult selectHistoricalStateDate(Integer userid, Integer deviceid, Integer Theyear, Integer moduleid)
			throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功",
				alarmService.selectHistoricalStateDate(deviceid, Theyear, moduleid));
		return resultInfo;
	}

	// 报警设备详情
	@PostMapping("/selectByAlarmid")
	@Permissions("Alarm:selectByAlarmid:query")
	public ApiResult selectByAlarmid(Integer userid, Integer alarmid, Integer moduleid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", alarmService.selectByAlarmid(alarmid, moduleid));
		return resultInfo;
	}

	// 报警处理中情况
	@PostMapping("/selectAlarmDevicHanding")
	@Permissions("Alarm:selectAlarmDevicHanding:query")
	public ApiResult selectAlarmDevicHanding(Integer userid, Integer alarmid, Integer moduleid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功",
				alarmService.selectAlarmDevicHanding(alarmid, moduleid));
		return resultInfo;
	}

	// 报警已处理情况
	@PostMapping("/selectAlarmDeviceFinish")
	@Permissions("Alarm:selectAlarmDeviceFinish:query")
	public ApiResult selectAlarmDeviceFinish(Integer userid, Integer alarmid, Integer moduleid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功",
				alarmService.selectAlarmDeviceFinish(alarmid, moduleid));
		return resultInfo;
	}

	// 根据模块查询所有状态
	@PostMapping("/selectAllState")
	@Permissions("Alarm:selectAllState:query")
	public ApiResult selectAllState(Integer userid, Integer moduleid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", alarmService.selectAllState(moduleid));
		return resultInfo;
	}

	// 展示所有状态(分页)
	@PostMapping("/selectAllStateToPage")
	@Permissions("Alarm:selectAllStateToPage:query")
	public ApiResult selectAllStateToPage(Integer userid, Integer moduleid, Integer pageSize, Integer pageNum)
			throws MyException {
		PageHelper.startPage(pageNum, pageSize);
		Page<AlarmState> page = (Page<AlarmState>) alarmService.selectAllState(moduleid);
		PageInfo<AlarmState> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(),
				page.getResult());
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", pageinfo);
		return resultInfo;
	}

	// 根据状态id 查询单个信息
	@PostMapping("/selectStateOneByid")
	@Permissions("Alarm:selectStateOneByid:query")
	public ApiResult selectStateOneByid(Integer userid, Integer stateid, Integer moduleid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", alarmService.selectStateOneByid(stateid, moduleid));
		return resultInfo;
	}

	// 修改状态
	@PostMapping("/updateStateOneByid")
	@Permissions("Alarm:updateStateOneByid:update")
	public ApiResult updateStateOneByid(Integer userid, AlarmStateParam alarmStateParam) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", alarmService.updateStateOneByid(alarmStateParam));
		return resultInfo;
	}

	// 删除状态
	@PostMapping("/deleteStateOneByid")
	@Permissions("Alarm:deleteStateOneByid:del")
	public ApiResult deleteStateOneByid(Integer userid, Integer stateid, Integer moduleid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", alarmService.deleteStateOneByid(stateid, moduleid));
		return resultInfo;
	}

	// 添加状态
	@PostMapping("/insertStateOne")
	@Permissions("Alarm:insertStateOne:add")
	public ApiResult insertStateOne(Integer userid, AlarmStateParam alarmStateParam) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", alarmService.insertStateOne(alarmStateParam));
		return resultInfo;
	}

	// app端处理完成详情
	@PostMapping("/selectAppAlarmFinish")
	@Permissions("Alarm:selectAppAlarmFinish:query")
	public ApiResult selectAppAlarmFinish(Integer userid, Integer alarmid, Integer moduleid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", alarmService.selectAppAlarmFinish(alarmid, moduleid));
		return resultInfo;
	}

	// App端报警处理
	@PostMapping("/APPAlarmHandle")
	@Permissions("Alarm:APPAlarmHandle:update")
	public ApiResult APPAlarmHandle(Integer userid, Integer moduleid, AlarmHandleParam alarmHandleParam)
			throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功",
				alarmService.APPAlarmHandle(userid, moduleid, alarmHandleParam));
		return resultInfo;
	}

	// App端报警信息
	@PostMapping("/selectAppAlarmList")
	@Permissions("Alarm:selectAppAlarmList:query")
	public ApiResult selectAppAlarmList(Integer pageNum, Integer pageSize, Integer userid, Integer moduleid,
			Integer state, Integer timeout, String message) throws MyException {
		PageHelper.startPage(pageNum, pageSize);
		Page<AppAlarmList> page = (Page<AppAlarmList>) alarmService.selectAppAlarmList(userid, moduleid, state, timeout,
				message);
		PageInfo<AppAlarmList> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(),
				page.getResult());
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", pageinfo);
		return resultInfo;
	}

	// 终端报警设备总数
	@PostMapping("/selectAlarmCount")
	@Permissions("Alarm:selectAlarmCount:query")
	public ApiResult selectAlarmCount(Integer userid, Integer moduleid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", alarmService.selectAlarmCount(userid, moduleid));
		return resultInfo;
	}

	/**
	 * 查询当前用户拥有模块和报警数量（消息推送）
	 * 
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/queryAlarmCountFor")
	public ApiResult queryAlarmCountFor(Integer userid) throws MyException {
		return ApiResult.resultInfo("1", "执行成功", alarmService.queryAlarmCount(userid));
	}

	/**
	 * app轮询属于当前巡检人员的未处理报警
	 * 
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/queryUnhandleAlarm")
	@Permissions("Alarm:queryUnhandleAlarm:query")
	public ApiResult queryUnhandleAlarm(Integer userid) throws MyException {
		return ApiResult.resultInfo("1", "执行成功", alarmService.queryUnhandleAlarm(userid));
	}
}
