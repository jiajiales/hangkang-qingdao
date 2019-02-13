package com.hot.manage.controller.sxdy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.annotation.Permissions;
import com.hot.manage.controller.PageController;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.sxdy.SXDYDevList;
import com.hot.manage.entity.sxdy.SXDYSelectOnId;
import com.hot.manage.entity.sxdy.value.SXDYAddDevVaule;
import com.hot.manage.entity.sxdy.value.SXDYChangeUser;
import com.hot.manage.entity.sxdy.value.SXDYDevListVaule;
import com.hot.manage.entity.sxdy.value.SXDYUpdateDevVaule;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.sxdy.SXDYDevService;
import com.hot.manage.service.video.VideoService;
import com.hot.manage.utils.ApiResult;
import com.hot.manage.utils.ConverUtil;

@RestController
public class SXDYDevController implements PageController<SXDYDevListVaule, SXDYDevList> {

	@Autowired
	private VideoService videoService;

	@Autowired
	private SXDYDevService sXDYDevService;

	@PostMapping("/SXDY/SXDYDevList")
	@Permissions("SXDY:SXDYDevList:query")
	// vaule=设备列表数据
	@Override
	public ApiResult page(SXDYDevListVaule p) throws MyException {
		PageHelper.startPage(p.getPageNum(), p.getPageSize());
		Page<SXDYDevList> page = sXDYDevService.SXDYDevList(p);
		List<SXDYDevList> list = ConverUtil.converPage(page, SXDYDevList.class);
		PageInfo<SXDYDevList> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		return ApiResult.resultInfo("1", "成功", pageinfo);
	}
	
	@PostMapping("/SXDY/AlarmingTrendForSXDY")
	@Permissions("SXDY:query:AlarmingTrendForSXDY")
	// 报警趋势图
	public ApiResult AlarmingTrendForSXDY(Integer queryType, Integer userid, Integer groupid) throws MyException {
		return ApiResult.resultInfo("1", "成功", sXDYDevService.AlarmingTrendForSXDY(queryType, userid, groupid));
	}

	@PostMapping("/SXDY/SXDYUpdateDev")
	@Permissions("SXDY:SXDYUpdateDev:update")
	// vaule=修改设备数据
	public ApiResult SXDYUpdateDev(SXDYUpdateDevVaule SXDYUpdateDevVaule) {
		ApiResult resultInfo = null;
		Integer insertObject = sXDYDevService.SXDYUpdateDev(SXDYUpdateDevVaule);
		if (insertObject == 201) {
			resultInfo = ApiResult.resultInfo("0", "设备编号已存在！！", null);
		} else if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	@PostMapping("/SXDY/SXDYDeleteDev")
	@Permissions("SXDY:SXDYDeleteDev:del")
	// vaule=删除设备
	public ApiResult SXDYDeleteDev(Integer moduleid, Integer devid) {
		ApiResult resultInfo = null;
		Integer insertObject = sXDYDevService.SXDYDeleteDev(moduleid, devid);
		if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	@PostMapping("/SXDY/SXDYSelectOnId")
	@Permissions("SXDY:SXDYSelectOnId:query")
	// vaule=根据设备id查询设备信息
	public List<SXDYSelectOnId> SXDYSelectOnId(Integer moduleid, Integer userid, Integer devid) throws MyException {
		return sXDYDevService.SXDYSelectOnId(moduleid, userid, devid);
	}

	@PostMapping("/SXDY/SXDYAddDev")
	@Permissions("SXDY:SXDYAddDev:add")
	// vaule=添加可燃气设备
	public ApiResult SXDYAddDev(SXDYAddDevVaule SXDYAddDevVaule) {
		ApiResult resultInfo = null;
		Integer insertObject = sXDYDevService.SXDYAddDev(SXDYAddDevVaule);
		if (insertObject == 201) {
			resultInfo = ApiResult.resultInfo("0", "设备编号已存在！！", null);
		} else if (insertObject == 202) {
			resultInfo = ApiResult.resultInfo("0", "设备mac已存在！！", null);
		} else if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	// 设备关联摄像头
	@PostMapping("/SXDY/devConectVideo")
	@Permissions("SXDY:devConectVideo:add")
	public ApiResult devConectVideo(Integer moduleid, Integer devid, String videoid) throws MyException {
		Integer result = videoService.reRelation(moduleid, devid, videoid);
		return ApiResult.resultInfo("1", "成功", result);
	}

	// 更换设备
	@PostMapping("/SXDY/SXDYChangeDev")
	@Permissions("SXDY:SXDYChangeDev:update")
	public ApiResult SXDYChangeDev(SXDYUpdateDevVaule SXDYUpdateDevVaule) {
		ApiResult resultInfo = null;
		Integer insertObject = sXDYDevService.SXDYChangeDev(SXDYUpdateDevVaule);
		if (insertObject == 201) {
			resultInfo = ApiResult.resultInfo("0", "失败，设备mac已存在！！", null);
		} else if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;

	}

	// 批量修改责任人
	@PostMapping("/SXDY/SXDYChangeDevOwn")
	@Permissions("SXDY:SXDYChangeDevOwn:update")
	public ApiResult LKTHWChangeUsersDev(SXDYChangeUser SXDYChangeUser) throws MyException {
		return ApiResult.resultInfo("1", "成功", sXDYDevService.changeDevOwn(SXDYChangeUser));

	}

	@PostMapping("/SXDY/LKTAddSignDevList")
	@Permissions("SXDY:LKTAddSignDevList:add")
	// vaule=加入设备签到列表
	public ApiResult LKTAddSignDevList(Integer moduleid, String devid, Integer patrolid) {
		ApiResult resultInfo = null;
		Integer insertObject = sXDYDevService.LKTAddSignDevList(moduleid, devid, patrolid);
		if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		}
		return resultInfo;
	}

}
