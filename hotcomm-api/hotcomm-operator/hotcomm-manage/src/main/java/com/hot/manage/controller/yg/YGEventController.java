package com.hot.manage.controller.yg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.annotation.Permissions;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.yg.param.YGEventParam;
import com.hot.manage.entity.yg.vo.YGEventPartic;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.yg.YGAppService;
import com.hot.manage.service.yg.YGEventInfoService;
import com.hot.manage.service.yg.YGEventParticService;
import com.hot.manage.utils.ApiResult;
import com.hot.manage.utils.ConverUtil;

@RestController
public class YGEventController {

	@Autowired
	private YGEventParticService ygEventParticService;

	@Autowired
	private YGEventInfoService eventInfoService;

	@Autowired
	private YGAppService ygAppService;

	@PostMapping("YGDevice/selectYGEventParticForTime")
	@Permissions("item:yg:read:EventParticForTime")
	// ("根据时间，内容等筛选所有事件(分页)")
	public ApiResult selectAllForTime(Integer userid, String startTime, String endTime, String message, Integer pageNum,
			Integer pageSize) throws MyException {
		PageHelper.startPage(pageNum, pageSize);
		Page<YGEventPartic> page = ygEventParticService.selectAllEventParticForTime(userid, startTime, endTime,
				message);
		List<YGEventPartic> list = ConverUtil.converPage(page, YGEventPartic.class);
		PageInfo<YGEventPartic> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", pageinfo);
		return resultInfo;

	}

	@PostMapping("YGDevice/selectYGEventParticForState")
	@Permissions("item:yg:read:EventParticForState")
	// ("根据事件描述，事件状态筛选所有事件(分页)")
	public ApiResult selectAllForState(Integer userid, Integer eventstateid, Integer state, Integer pageNum,
			Integer pageSize) throws MyException {
		PageHelper.startPage(pageNum, pageSize);
		Page<YGEventPartic> page = ygEventParticService.selectAllEventParticForState(userid, eventstateid, state);
		List<YGEventPartic> list = ConverUtil.converPage(page, YGEventPartic.class);
		PageInfo<YGEventPartic> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", pageinfo);
		return resultInfo;
	}
	
	
//("根据时间，根据事件描述，事件状态,内容等筛选所有事件(分页)")
	
	@PostMapping("YGDevice/selectYGEventParticForMas")
	@Permissions("item:yg:read:EventParticForMas")
	
	public ApiResult selectAllForMas(Integer userid, String startTime, String endTime, String message, Integer eventstateid, Integer state, Integer pageNum,
			Integer pageSize) throws MyException {
		PageHelper.startPage(pageNum, pageSize);
		Page<YGEventPartic> page = ygEventParticService.selectAllEventParticForMas(startTime, endTime,
				message,userid, eventstateid, state);
		List<YGEventPartic> list = ConverUtil.converPage(page, YGEventPartic.class);
		PageInfo<YGEventPartic> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", pageinfo);
		return resultInfo;
	}
	
	
	
	@PostMapping("YGDevice/selectYGEventInfo")
	@Permissions("item:yg:read:EventInfo")
	// ("事件内容")
	public ApiResult selectYGEventInfo(Integer userid, Integer eventid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", eventInfoService.selectYGEventInfo(eventid));
		return resultInfo;

	}

	@PostMapping("YGDevice/selectYGEventDeviceRele")
	@Permissions("item:yg:read:EventDeviceRele")
	// ("关联设备")
	public ApiResult selectEventDeviceRele(Integer userid, Integer eventid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", eventInfoService.selectEventDevice(eventid));
		return resultInfo;

	}

	@PostMapping("YGDevice/selectYGEventInstructRele")
	@Permissions("item:yg:read:EventInstructRele")
	// ("关联的工作指示")
	public ApiResult selectEventInstructRele(Integer userid, Integer eventid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", eventInfoService.selectEventInstruct(eventid));
		return resultInfo;

	}

	@PostMapping("YGDevice/selectYGEventHandling")
	@Permissions("item:yg:read:EventHandling")
	// ("事件处理（处理中）")
	public ApiResult selectEventHandling(Integer userid, Integer eventid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", eventInfoService.selectEventHandling(eventid));
		return resultInfo;

	}

	@PostMapping("YGDevice/selectYGEventFinish")
	@Permissions("item:yg:read:EventFinish")
	// ("事件处理（完成）")
	public ApiResult selectEventFinish(Integer userid, Integer eventid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", eventInfoService.selectEventFinish(eventid));
		return resultInfo;

	}

	@PostMapping("YGDevice/APPinsertEvent")
	@Permissions("item:yg:read:insertEvent")
	// ("事件上报")
	public ApiResult Event(Integer userid, YGEventParam ygEventParam) throws MyException {

		ApiResult resultInfo = null;
		Integer innerobject = ygAppService.insertEvent(ygEventParam);
		if (innerobject == 1) {
			resultInfo = ApiResult.resultInfo("1", "成功！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("0", "失败！！",null);
		}
		return resultInfo;

	}
}
