package com.hot.manage.controller.common.event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.annotation.Permissions;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.common.event.AppEventList;
import com.hot.manage.entity.common.event.EventDeviceRele;
import com.hot.manage.entity.common.event.EventFinishVo;
import com.hot.manage.entity.common.event.EventHandling;
import com.hot.manage.entity.common.event.EventInfoVo;
import com.hot.manage.entity.common.event.EventInstructRele;
import com.hot.manage.entity.common.event.EventListParam;
import com.hot.manage.entity.common.event.EventListVo;
import com.hot.manage.entity.common.event.EventParam;
import com.hot.manage.entity.common.event.T_event_state;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.common.event.EventService;
import com.hot.manage.utils.ApiResult;
import com.hot.manage.utils.ConverUtil;

@RestController
@RequestMapping("Event")
public class EventController {

	@Autowired
	private EventService eventService;

	// 事件总数
	@PostMapping("/selectEventCount")
	@Permissions("Event:selectEventCount:query")
	public ApiResult selectEventCount(Integer userid, Integer moduleid) throws MyException {
		Integer selectEventCount = eventService.selectEventCount(userid, moduleid);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", selectEventCount);
		return resultInfo;
	}

	// 事件工作指示
	@PostMapping("/selectWorkInstruction")
	@Permissions("Event:selectWorkInstruction:query")
	public ApiResult selectWorkInstruction(Integer userid, Integer eventid, Integer moduleid) throws MyException {
		List<EventInstructRele> list = eventService.selectWorkInstruction(eventid, moduleid);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", list);
		return resultInfo;
	}

	// 事件关联设备
	@PostMapping("/selectEventDeviceRe")
	@Permissions("Event:selectEventDeviceRe:query")
	public ApiResult selectEventDeviceRe(Integer userid, Integer eventid, Integer moduleid) throws MyException {
		List<EventDeviceRele> list = eventService.selectEventDeviceRe(eventid, moduleid);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", list);
		return resultInfo;
	}

	// 事件详情
	@PostMapping("/selectEventInfo")
	@Permissions("Event:selectEventInfo:query")
	public ApiResult selectEventInfo(Integer userid, Integer eventid, Integer moduleid) throws MyException {
		EventInfoVo eventInFoVo = eventService.selectEventInfo(eventid, moduleid);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", eventInFoVo);
		return resultInfo;
	}

	// 事件处理中详情
	@PostMapping("/selectEventHandling")
	@Permissions("Event:selectEventHandling:query")
	public ApiResult selectEventHandling(Integer userid, Integer eventid, Integer moduleid) throws MyException {
		EventHandling eventHandling = eventService.selectEventHandling(eventid, moduleid);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", eventHandling);
		return resultInfo;
	}

	// 事件处理完成详情
	@PostMapping("/selectEventFinish")
	@Permissions("Event:selectEventFinish:query")
	public ApiResult selectEventFinish(Integer userid, Integer eventid, Integer moduleid) throws MyException {
		EventFinishVo eventFinishVo = eventService.selectEventFinish(eventid, moduleid);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", eventFinishVo);
		return resultInfo;
	}

	// 根据条件筛选列表
	@PostMapping("/selectEventList")
	@Permissions("Event:selectEventList:query")
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
	@Permissions("Event:selectEventListMaps:query")
	public ApiResult selectEventListMaps(Integer userid, Integer moduleid) throws MyException {
		List<EventListVo> selectEventListMaps = eventService.selectEventListMaps(userid, moduleid);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", selectEventListMaps);
		return resultInfo;
	}

	// 查询所有事件状态
	@PostMapping("/selectAllEventState")
	@Permissions("Event:selectAllEventState:query")
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
	@Permissions("Event:selectAllEventStateToPage:query")
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
	@Permissions("Event:selectEventStateByOne:query")
	public ApiResult selectEventStateByOne(Integer userid, Integer stateid, Integer moduleid) throws MyException {
		T_event_state selectEventStateByOne = eventService.selectEventStateByOne(stateid, moduleid);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", selectEventStateByOne);
		return resultInfo;
	}

	// 根据id修改事件状态
	@PostMapping("/updateEventStateByOne")
	@Permissions("Event:updateEventStateByOne:update")
	public ApiResult updateEventStateByOne(Integer userid, T_event_state eventAlarm) throws MyException {
		Integer i = eventService.updateEventStateByOne(eventAlarm);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", i);
		return resultInfo;
	}

	// 根据id删除状态
	@PostMapping("/deleteEventStateByOne")
	@Permissions("Event:deleteEventStateByOne:del")
	public ApiResult deleteEventStateByOne(Integer userid, Integer stateid, Integer moduleid) throws MyException {
		Integer i = eventService.deleteEventStateByOne(stateid, moduleid);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", i);
		return resultInfo;
	}

	// 新增事件状态
	@PostMapping("/insertEventStateOne")
	@Permissions("Event:insertEventStateOne:add")
	public ApiResult insertEventStateOne(Integer userid, T_event_state eventAlarm) throws MyException {
		Integer i = eventService.insertEventStateOne(eventAlarm);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", i);
		return resultInfo;
	}

	// APP端事件上报
	@PostMapping("/insertOneEvent")
	@Permissions("Event:insertOneEvent:add")
	public ApiResult insertOneEvent(Integer userid, EventParam eventParam, Integer moduleid) throws MyException {
		Integer i = eventService.insertOneEvent(userid, eventParam, moduleid);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", i);
		return resultInfo;
	}

	// App端事件详情
	@PostMapping("/selectAPPEventInfo")
	@Permissions("Event:selectAPPEventInfo:query")
	public ApiResult selectAPPEventInfo(Integer userid, Integer moduleid, Integer eventid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", eventService.selectAPPEventInfo(moduleid, eventid));
		return resultInfo;
	}

	// 事件关闭操作
	@PostMapping("/closeEvent")
	@Permissions("Event:closeEvent:update")
	public ApiResult closeEvent(Integer userid, Integer eventid, Integer moduleid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", eventService.closeEvent(eventid, moduleid));
		return resultInfo;
	}

	// 可选模块
	@PostMapping("/selectAPPmodule")
	@Permissions("Event:selectAPPmodule:query")
	public ApiResult selectAPPmodule(Integer userid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", eventService.selectAPPModule());
		return resultInfo;
	}

	// APP上报记录
	@PostMapping("/selectAppEventList")
	@Permissions("Event:selectAppEventList:query")
	public ApiResult selectAppEventList(Integer userid, Integer moduleid, Integer timeout, String message,
			Integer pageNum, Integer pageSize) throws MyException {
		PageHelper.startPage(pageNum, pageSize);
		Page<AppEventList> page = (Page<AppEventList>) eventService.selectAppEventList(moduleid, timeout, message);
		PageInfo<AppEventList> pageInfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(),
				page.getResult());
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", pageInfo);
		return resultInfo;
	}
}
