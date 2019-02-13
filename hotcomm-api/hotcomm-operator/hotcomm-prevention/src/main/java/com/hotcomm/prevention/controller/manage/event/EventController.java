package com.hotcomm.prevention.controller.manage.event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.bean.mysql.manage.event.AppEventList;
import com.hotcomm.prevention.bean.mysql.manage.event.EventDeviceRele;
import com.hotcomm.prevention.bean.mysql.manage.event.EventHandling;
import com.hotcomm.prevention.bean.mysql.manage.event.EventInstructRele;
import com.hotcomm.prevention.bean.mysql.manage.event.EventListParam;
import com.hotcomm.prevention.bean.mysql.manage.event.EventParam;
import com.hotcomm.prevention.bean.mysql.manage.event.T_event_state;
import com.hotcomm.prevention.bean.mysql.manage.event.VO.EventFinishVo;
import com.hotcomm.prevention.bean.mysql.manage.event.VO.EventInfoVo;
import com.hotcomm.prevention.bean.mysql.manage.event.VO.EventListVo;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.manage.event.EventService;
import com.hotcomm.prevention.utils.ApiResult;
import com.hotcomm.prevention.utils.ConverUtil;

@RestController
@RequestMapping("Event")
public class EventController {

	@Autowired
	private EventService eventService;

	// 事件总数
	@PostMapping("/selectEventCount")
	public ApiResult selectEventCount(Integer userid, Integer moduleid) throws MyException {
		Integer selectEventCount = eventService.selectEventCount(userid, moduleid);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", selectEventCount);
		return resultInfo;
	}

	// 事件工作指示
	@PostMapping("/selectWorkInstruction")
	public ApiResult selectWorkInstruction(Integer userid, Integer eventid, Integer moduleid) throws MyException {
		List<EventInstructRele> list = eventService.selectWorkInstruction(eventid, moduleid);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", list);
		return resultInfo;
	}

	// 事件关联设备
	@PostMapping("/selectEventDeviceRe")
	public ApiResult selectEventDeviceRe(Integer userid, Integer eventid, Integer moduleid) throws MyException {
		List<EventDeviceRele> list = eventService.selectEventDeviceRe(eventid, moduleid);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", list);
		return resultInfo;
	}

	// 事件详情
	@PostMapping("/selectEventInfo")
	public ApiResult selectEventInfo(Integer userid, Integer eventid, Integer moduleid) throws MyException {
		EventInfoVo eventInFoVo = eventService.selectEventInfo(eventid, moduleid);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", eventInFoVo);
		return resultInfo;
	}

	// 事件处理中详情
	@PostMapping("/selectEventHandling")
	public ApiResult selectEventHandling(Integer userid, Integer eventid, Integer moduleid) throws MyException {
		EventHandling eventHandling = eventService.selectEventHandling(eventid, moduleid);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", eventHandling);
		return resultInfo;
	}

	// 事件处理完成详情
	@PostMapping("/selectEventFinish")
	public ApiResult selectEventFinish(Integer userid, Integer eventid, Integer moduleid) throws MyException {
		EventFinishVo eventFinishVo = eventService.selectEventFinish(eventid, moduleid);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", eventFinishVo);
		return resultInfo;
	}

	// 根据条件筛选列表
	@PostMapping("/selectEventList")
	public ApiResult selectEventList(Integer userid, EventListParam eventListParam, Integer moduleid, Integer pageNum,
			Integer pageSize) throws MyException {
		PageHelper.startPage(pageNum, pageSize);
		Page<EventListVo> page = eventService.selectEventList(eventListParam, userid, moduleid);
		List<EventListVo> list = ConverUtil.converPage(page, EventListVo.class);
		if (list.size() == 0) {
			list = null;
		}
		PageInfo<EventListVo> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", pageinfo);
		return resultInfo;
	}

	// 事件地图展示
	@PostMapping("/selectEventListMaps")
	public ApiResult selectEventListMaps(Integer userid, Integer moduleid) throws MyException {
		List<EventListVo> selectEventListMaps = eventService.selectEventListMaps(userid, moduleid);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", selectEventListMaps);
		return resultInfo;
	}

	// 查询所有事件状态
	@PostMapping("/selectAllEventState")
	public ApiResult selectAllEventState(Integer userid, Integer moduleid) throws MyException {
		List<T_event_state> selectAllEventState = eventService.selectAllEventState(moduleid);
		if (selectAllEventState.size() == 0) {
			selectAllEventState = null;
		}
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", selectAllEventState);
		return resultInfo;
	}

	// 展示所有事件状态(分页)
	@PostMapping("/selectAllEventStateToPage")
	public ApiResult selectAllEventStateToPage(Integer userid, Integer pageNum, Integer pageSize, Integer moduleid)
			throws MyException {
		PageHelper.startPage(pageNum, pageSize);
		Page<T_event_state> page = (Page<T_event_state>) eventService.selectAllEventState(moduleid);
		List<T_event_state> list = page.getResult();
		if (page.getTotal() == 0) {
			list = null;
		}
		PageInfo<T_event_state> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", pageinfo);
		return resultInfo;
	}

	// 查询单个事件状态
	@PostMapping("/selectEventStateByOne")
	public ApiResult selectEventStateByOne(Integer userid, Integer stateid, Integer moduleid) throws MyException {
		T_event_state selectEventStateByOne = eventService.selectEventStateByOne(stateid, moduleid);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", selectEventStateByOne);
		return resultInfo;
	}

	// 根据id修改事件状态
	@PostMapping("/updateEventStateByOne")
	public ApiResult updateEventStateByOne(Integer userid, T_event_state eventAlarm) throws MyException {
		Integer i = eventService.updateEventStateByOne(eventAlarm);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", i);
		return resultInfo;
	}

	// 根据id删除状态
	@PostMapping("/deleteEventStateByOne")
	public ApiResult deleteEventStateByOne(Integer userid, Integer stateid, Integer moduleid) throws MyException {
		Integer i = eventService.deleteEventStateByOne(stateid, moduleid);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", i);
		return resultInfo;
	}

	// 新增事件状态
	@PostMapping("/insertEventStateOne")
	public ApiResult insertEventStateOne(Integer userid, T_event_state eventAlarm) throws MyException {
		Integer i = eventService.insertEventStateOne(eventAlarm);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", i);
		return resultInfo;
	}

	// APP端事件上报
	@PostMapping("/insertOneEvent")
	public ApiResult insertOneEvent(Integer userid, EventParam eventParam, Integer moduleid) throws MyException {
		Integer i = eventService.insertOneEvent(userid, eventParam, moduleid);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", i);
		return resultInfo;
	}

	// App端事件详情
	@PostMapping("/selectAPPEventInfo")
	public ApiResult selectAPPEventInfo(Integer userid, Integer moduleid, Integer eventid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", eventService.selectAPPEventInfo(moduleid, eventid));
		return resultInfo;
	}

	// 事件关闭操作
	@PostMapping("/closeEvent")
	public ApiResult closeEvent(Integer userid, Integer eventid, Integer moduleid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", eventService.closeEvent(eventid, moduleid));
		return resultInfo;
	}

	// 可选模块
	@PostMapping("/selectAPPmodule")
	public ApiResult selectAPPmodule(Integer userid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", eventService.selectAPPModule());
		return resultInfo;
	}

	// APP上报记录
	@PostMapping("/selectAppEventList")
	public ApiResult selectAppEventList(Integer userid, Integer moduleid, Integer timeout, String message,
			Integer pageNum, Integer pageSize) throws MyException {
		PageHelper.startPage(pageNum, pageSize);
		Page<AppEventList> page = (Page<AppEventList>) eventService.selectAppEventList(userid, moduleid, timeout,
				message);
		PageInfo<AppEventList> pageInfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(),
				page.getResult());
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", pageInfo);
		return resultInfo;
	}
}
