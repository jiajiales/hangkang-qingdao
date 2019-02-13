package com.hotcomm.prevention.controller.manage.alarm;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.bean.mysql.manage.alarm.AlarmState;
import com.hotcomm.prevention.bean.mysql.manage.alarm.AppAlarmList;
import com.hotcomm.prevention.bean.mysql.manage.alarm.VO.AlarmListVo;
import com.hotcomm.prevention.bean.mysql.manage.alarm.param.AlarmHandleParam;
import com.hotcomm.prevention.bean.mysql.manage.alarm.param.AlarmListParam;
import com.hotcomm.prevention.bean.mysql.manage.alarm.param.AlarmStateParam;
import com.hotcomm.prevention.bean.mysql.manage.appPush.T_hk_apppush;
import com.hotcomm.prevention.bean.mysql.manage.appPush.T_hk_apppush_msg;
import com.hotcomm.prevention.bean.mysql.manage.workorder.vaule.WorkNewVaule;
import com.hotcomm.prevention.db.mysql.manage.workorder.WorkMapper;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.manage.alarm.AlarmService;
import com.hotcomm.prevention.service.manage.appPush.AppPushService;
import com.hotcomm.prevention.utils.ApiResult;
import com.hotcomm.prevention.utils.ConverUtil;
import com.hotcomm.prevention.utils.JSONUtil;
import com.hotcomm.prevention.utils.PushUtil;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;

@RestController
@RequestMapping("Alarm")
public class AlarmController {
	@Autowired
	private AlarmService alarmService;
	@Autowired
	AppPushService appPushService;
	@Autowired
	WorkMapper workMapper;

	// 根据条件筛选报警记录
	@PostMapping("/selectAlarmList")
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
	public ApiResult selectAlarmMpas(Integer userid, Integer moduleid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", alarmService.selectAlarmMaps(userid, moduleid));
		return resultInfo;
	}

	// 根据id 模块id 关闭报警设备
	@PostMapping("/closealarmById")
	public ApiResult closealarmById(Integer userid, Integer alarmid, Integer moduleid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", alarmService.closealarmById(alarmid, moduleid));
		return resultInfo;
	}

	// 查询设备报警历史数据
	@PostMapping("/selectHistoricalDate")
	public ApiResult selectHistoricalDate(Integer userid, Integer deviceid, Integer Theyear, Integer moduleid)
			throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功",
				alarmService.selectHistoricalDate(deviceid, Theyear, moduleid));
		return resultInfo;

	}

	// 根据设备id与年份查询历史报警类型
	@PostMapping("/selectHistoricalStateDate")
	public ApiResult selectHistoricalStateDate(Integer userid, Integer deviceid, Integer Theyear, Integer moduleid)
			throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功",
				alarmService.selectHistoricalStateDate(deviceid, Theyear, moduleid));
		return resultInfo;
	}

	// 报警设备详情
	@PostMapping("/selectByAlarmid")
	public ApiResult selectByAlarmid(Integer userid, Integer alarmid, Integer moduleid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", alarmService.selectByAlarmid(alarmid, moduleid));
		return resultInfo;
	}

	// 报警处理中情况
	@PostMapping("/selectAlarmDevicHanding")
	public ApiResult selectAlarmDevicHanding(Integer userid, Integer alarmid, Integer moduleid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功",
				alarmService.selectAlarmDevicHanding(alarmid, moduleid));
		return resultInfo;
	}

	// 报警已处理情况
	@PostMapping("/selectAlarmDeviceFinish")
	public ApiResult selectAlarmDeviceFinish(Integer userid, Integer alarmid, Integer moduleid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功",
				alarmService.selectAlarmDeviceFinish(alarmid, moduleid));
		return resultInfo;
	}

	// 根据模块查询所有状态
	@PostMapping("/selectAllState")
	public ApiResult selectAllState(Integer userid, Integer moduleid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", alarmService.selectAllState(moduleid));
		return resultInfo;
	}

	// 展示所有状态(分页)
	@PostMapping("/selectAllStateToPage")
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
	public ApiResult selectStateOneByid(Integer userid, Integer stateid, Integer moduleid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", alarmService.selectStateOneByid(stateid, moduleid));
		return resultInfo;
	}

	// 修改状态
	@PostMapping("/updateStateOneByid")
	public ApiResult updateStateOneByid(Integer userid, AlarmStateParam alarmStateParam) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", alarmService.updateStateOneByid(alarmStateParam));
		return resultInfo;
	}

	// 删除状态
	@PostMapping("/deleteStateOneByid")
	public ApiResult deleteStateOneByid(Integer userid, Integer stateid, Integer moduleid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", alarmService.deleteStateOneByid(stateid, moduleid));
		return resultInfo;
	}

	// 添加状态
	@PostMapping("/insertStateOne")
	public ApiResult insertStateOne(Integer userid, AlarmStateParam alarmStateParam) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", alarmService.insertStateOne(alarmStateParam));
		return resultInfo;
	}

	// app端处理完成详情
	@PostMapping("/selectAppAlarmFinish")
	public ApiResult selectAppAlarmFinish(Integer userid, Integer alarmid, Integer moduleid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", alarmService.selectAppAlarmFinish(alarmid, moduleid));
		return resultInfo;
	}

	// App端报警处理
	@PostMapping("/APPAlarmHandle")
	public ApiResult APPAlarmHandle(Integer userid, Integer moduleid, AlarmHandleParam alarmHandleParam)
			throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功",
				alarmService.APPAlarmHandle(userid, moduleid, alarmHandleParam));
		return resultInfo;
	}

	@PostMapping("/APPpush")
	public ApiResult APPpush(Integer userid) throws MyException {
		// PushUtil.sendAllsetNotification("可以刷新未处理列表了", "1","1", null, 1);
		// System.out.println(123);
		// 查出用户regid
		T_hk_apppush t_hk_apppush = workMapper.getUserRegid(userid);
		// Integer code = PushUtil.sendAllsetNotification("工单消息", "内容",
		// JSONUtil.toJson(ApiResult.resultInfo("2", "工单", t_hk_apppush)),
		// t_hk_apppush.getRegid(), 86400);
		Integer code = PushUtil.sendAllsetNotification("0", "0", "\"systate\":\"0\",\"msg\":\"0\"", t_hk_apppush.getRegid(), 86400);
		return ApiResult.resultInfo("1", "1", "推送了" + code);
	}

	// App端报警信息
	@PostMapping("/selectAppAlarmList")
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
	public ApiResult queryAlarmCountFor(Integer userid, Integer fatherid) throws MyException {
		return ApiResult.resultInfo("1", "执行成功", alarmService.queryAlarmCount(userid, fatherid));
	}

	// /**
	// * app轮询属于当前巡检人员的未处理报警
	// *
	// * @param userid
	// * @return
	// * @throws MyException
	// */
	// @PostMapping("/queryUnhandleAlarm")
	// public ApiResult queryUnhandleAlarm(Integer userid) throws MyException {
	// return ApiResult.resultInfo("1", "执行成功",
	// alarmService.queryUnhandleAlarm(userid));
	// }

	@PostMapping("/queryUnhandleAlarm")
	public ApiResult queryUnhandleAlarm(Integer userid) throws MyException {
		return ApiResult.resultInfo("1", "执行成功", alarmService.queryUnhandleAlarm(userid));
	}

	@PostMapping("/appIndexInfoAboutAlarmAndEvent")
	public ApiResult appIndexInfoAboutAlarmAndEvent(Integer userid) throws MyException, ParseException {
		return ApiResult.resultInfo("1", "执行成功", alarmService.appIndexInfoAboutAlarmAndEvent(userid));
	}

}
