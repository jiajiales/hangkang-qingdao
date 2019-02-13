package com.hot.manage.controller.krq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hot.manage.annotation.Permissions;
import com.hot.manage.controller.PageController;
import com.hot.manage.entity.krq.LKTKRQDevList;
import com.hot.manage.entity.krq.vaule.KRQChangeUser;
import com.hot.manage.entity.krq.vaule.LKTKRQAddDevVaule;
import com.hot.manage.entity.krq.vaule.LKTKRQDevListVaule;
import com.hot.manage.entity.krq.vaule.LKTKRQUpdateDevVaule;
import com.hot.manage.entity.video.DevRelationVideoParam;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.krq.LKTKRQDevService;
import com.hot.manage.service.video.VideoService;
import com.hot.manage.utils.ApiResult;

@RestController
public class LKTKRQDevController implements PageController<LKTKRQDevListVaule, LKTKRQDevList> {

	@Autowired
	private VideoService videoService;
	@Autowired
	private LKTKRQDevService lktKRQDevService;

	@PostMapping("/krq/LKTKRQDevList")
	@Permissions("krq:LKTKRQDevList:query")
	// vaule=设备列表数据
	@Override
	public ApiResult page(LKTKRQDevListVaule p) throws MyException {
		return ApiResult.resultInfo("1", "成功!!", lktKRQDevService.LKTKRQDevList(p));
	}

	@PostMapping("/krq/LKTKRQUpdateDev")
	@Permissions("krq:LKTKRQUpdateDev:update")
	// vaule=修改设备数据
	public ApiResult LKTKRQUpdateDev(LKTKRQUpdateDevVaule lktkrqUpdateDevVaule) {
		ApiResult resultInfo = null;
		Integer insertObject = lktKRQDevService.LKTKRQUpdateDev(lktkrqUpdateDevVaule);
		if (insertObject == 201) {
			resultInfo = ApiResult.resultInfo("0", "设备编号已存在！！", null);
		} else if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	@PostMapping("/krq/LKTKRQDeleteDev")
	@Permissions("krq:LKTKRQDeleteDev:del")
	// vaule=删除设备
	public ApiResult LKTKRQDeleteDev(Integer moduleid, Integer devid) {
		ApiResult resultInfo = null;
		Integer insertObject = lktKRQDevService.LKTKRQDeleteDev(moduleid, devid);
		if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	@PostMapping("/krq/LKTKRQSelectOnId")
	@Permissions("krq:LKTKRQSelectOnId:query")
	// vaule=根据设备id查询设备信息
	public ApiResult LKTKRQSelectOnId(Integer moduleid, Integer userid, Integer devid) throws MyException {
		return ApiResult.resultInfo("1", "成功!!", lktKRQDevService.LKTKRQSelectOnId(moduleid, userid, devid));
	}

	@PostMapping("/krq/LKTKRQAddDev")
	@Permissions("krq:LKTKRQAddDev:add")
	// vaule=添加可燃气设备
	public ApiResult LKTKRQAddDev(LKTKRQAddDevVaule lktkrqAddDevVaule) {
		ApiResult resultInfo = null;
		Integer insertObject = lktKRQDevService.LKTKRQAddDev(lktkrqAddDevVaule);
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
	@PostMapping("/krq/devConectVideo")
	@Permissions("krq:devConectVideo:add")
	public ApiResult devConectVideo(DevRelationVideoParam param) throws MyException {
		Integer result = videoService.devConectVideo(param);
		return ApiResult.resultInfo("1", "成功", result);
	}

	// 更换设备
	@PostMapping("/krq/LKTKRQChangeDev")
	@Permissions("krq:LKTKRQChangeDev:update")
	public ApiResult LKTKRQChangeDev(LKTKRQUpdateDevVaule lktkrqUpdateDevVaule) {
		ApiResult resultInfo = null;
		Integer insertObject = lktKRQDevService.LKTKRQChangeDev(lktkrqUpdateDevVaule);
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
	@PostMapping("/krq/LKTKRQChangeDevOwn")
	@Permissions("krq:LKTKRQChangeDevOwn:update")
	public ApiResult LKTHWChangeUsersDev(KRQChangeUser kRQChangeUser) throws MyException {
		return ApiResult.resultInfo("1", "成功", lktKRQDevService.changeDevOwn(kRQChangeUser));

	}

	@PostMapping("/krq/LKTAddSignDevList")
	@Permissions("krq:LKTAddSignDevList:add")
	// vaule=加入设备签到列表
	public ApiResult LKTAddSignDevList(Integer moduleid, String devid, Integer patrolid) {
		ApiResult resultInfo = null;
		Integer insertObject = lktKRQDevService.LKTAddSignDevList(moduleid, devid, patrolid);
		if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	/**
	 * 可燃气报警趋势图
	 * 
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/krq/selectKRQAlarmTendency")
	@Permissions("")
	public ApiResult selectKRQAlarmTendency(Integer userid, Integer type, Integer groupid) throws MyException {
		if (type == 1) {
			return ApiResult.resultInfo("1", "执行成功", lktKRQDevService.selectKRQAlarmForDay(groupid));
		} else if (type == 3) {
			return ApiResult.resultInfo("1", "执行成功", lktKRQDevService.selectKRQAlarmForMonth(groupid));
		} else if (type == 2) {
			return ApiResult.resultInfo("1", "执行成功", lktKRQDevService.selectKRQAlarmForThreeYear(groupid));
		} else {
			return ApiResult.resultInfo("0", "执行失败,请选择筛选的单位", null);
		}
	}

	/**
	 * 可燃气报警处理统计
	 * 
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/krq/selectKRQAlarmForWeeken")
	@Permissions("")
	public ApiResult selectKRQAlarmForWeeken(Integer groupid) throws MyException {
		return ApiResult.resultInfo("1", "执行成功", lktKRQDevService.selectKRQAlarmForWeeken(groupid));
	}

	/**
	 * 可燃气报警类型统计
	 * 
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/krq/selectKRQAlarmForState")
	@Permissions("")
	public ApiResult selectKRQAlarmForState(Integer groupid, Integer TheType) throws MyException {
		return ApiResult.resultInfo("1", "执行成功", lktKRQDevService.selectKRQAlarmForState(groupid, TheType));
	}
}
