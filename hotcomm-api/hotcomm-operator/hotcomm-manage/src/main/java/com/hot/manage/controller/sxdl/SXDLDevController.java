package com.hot.manage.controller.sxdl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.annotation.Permissions;
import com.hot.manage.controller.PageController;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.sxdl.SXDLDevList;
import com.hot.manage.entity.sxdl.value.SXDLAddDevVaule;
import com.hot.manage.entity.sxdl.value.SXDLChangeUser;
import com.hot.manage.entity.sxdl.value.SXDLDevListVaule;
import com.hot.manage.entity.sxdl.value.SXDLUpdateDevVaule;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.sxdl.SXDLDevService;
import com.hot.manage.service.video.VideoService;
import com.hot.manage.utils.ApiResult;
import com.hot.manage.utils.ConverUtil;
import com.hot.manage.entity.sxdl.SXDLSelectOnId;

@RestController
public class SXDLDevController implements PageController<SXDLDevListVaule, SXDLDevList> {

	@Autowired
	private VideoService videoService;

	@Autowired
	private SXDLDevService sXDLDevService;

	@PostMapping("/SXDL/SXDLDevList")
	@Permissions("SXDL:SXDLDevList:query")
	// vaule=设备列表数据
	@Override
	public ApiResult page(SXDLDevListVaule p) throws MyException {
		PageHelper.startPage(p.getPageNum(), p.getPageSize());
		Page<SXDLDevList> page = sXDLDevService.SXDLDevList(p);
		List<SXDLDevList> list = ConverUtil.converPage(page, SXDLDevList.class);
		PageInfo<SXDLDevList> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		return ApiResult.resultInfo("1", "成功", pageinfo);
	}
	
	@PostMapping("/SXDL/AlarmingTrendForSXDL")
	@Permissions("SXDL:AlarmingTrendForSXDL:query")
	public ApiResult AlarmingTrendForSXDL(Integer queryType, Integer userid, Integer groupid) throws MyException {
		return ApiResult.resultInfo("1", "成功", sXDLDevService.AlarmingTrendForSXDL(queryType, userid, groupid));
	}

	@PostMapping("/SXDL/SXDLUpdateDev")
	@Permissions("SXDL:SXDLUpdateDev:update")
	// vaule=修改设备数据
	public ApiResult SXDLUpdateDev(SXDLUpdateDevVaule SXDLUpdateDevVaule) {
		ApiResult resultInfo = null;
		Integer insertObject = sXDLDevService.SXDLUpdateDev(SXDLUpdateDevVaule);
		if (insertObject == 201) {
			resultInfo = ApiResult.resultInfo("0", "设备编号已存在！！", null);
		} else if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	@PostMapping("/SXDL/SXDLDeleteDev")
	@Permissions("SXDL:SXDLDeleteDev:del")
	// vaule=删除设备
	public ApiResult SXDLDeleteDev(Integer moduleid, Integer devid) {
		ApiResult resultInfo = null;
		Integer insertObject = sXDLDevService.SXDLDeleteDev(moduleid, devid);
		if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	@PostMapping("/SXDL/SXDLSelectOnId")
	@Permissions("SXDL:SXDLSelectOnId:query")
	// vaule=根据设备id查询设备信息
	public List<SXDLSelectOnId> SXDLSelectOnId(Integer moduleid, Integer userid, Integer devid) throws MyException {
		return sXDLDevService.SXDLSelectOnId(moduleid, userid, devid);
	}

	@PostMapping("/SXDL/SXDLAddDev")
	@Permissions("SXDL:SXDLAddDev:add")
	// vaule=添加可燃气设备
	public ApiResult SXDLAddDev(SXDLAddDevVaule SXDLAddDevVaule) {
		ApiResult resultInfo = null;
		Integer insertObject = sXDLDevService.SXDLAddDev(SXDLAddDevVaule);
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
	@PostMapping("/SXDL/devConectVideo")
	@Permissions("SXDL:devConectVideo:add")
	public ApiResult devConectVideo(Integer moduleid, Integer devid, String videoid) throws MyException {
		Integer result = videoService.reRelation(moduleid, devid, videoid);
		return ApiResult.resultInfo("1", "成功", result);
	}

	// 更换设备
	@PostMapping("/SXDL/SXDLChangeDev")
	@Permissions("SXDL:SXDLChangeDev:update")
	public ApiResult SXDLChangeDev(SXDLUpdateDevVaule SXDLUpdateDevVaule) {
		ApiResult resultInfo = null;
		Integer insertObject = sXDLDevService.SXDLChangeDev(SXDLUpdateDevVaule);
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
	@PostMapping("/SXDL/SXDLChangeDevOwn")
	@Permissions("SXDL:SXDLChangeDevOwn:update")
	public ApiResult LKTHWChangeUsersDev(SXDLChangeUser SXDLChangeUser) throws MyException {
		return ApiResult.resultInfo("1", "成功", sXDLDevService.changeDevOwn(SXDLChangeUser));

	}

	@PostMapping("/SXDL/LKTAddSignDevList")
	@Permissions("SXDL:LKTAddSignDevList:add")
	// vaule=加入设备签到列表
	public ApiResult LKTAddSignDevList(Integer moduleid, String devid, Integer patrolid) {
		ApiResult resultInfo = null;
		Integer insertObject = sXDLDevService.LKTAddSignDevList(moduleid, devid, patrolid);
		if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		}
		return resultInfo;
	}

}
