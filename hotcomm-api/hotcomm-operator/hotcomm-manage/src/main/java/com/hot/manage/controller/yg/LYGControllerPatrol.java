package com.hot.manage.controller.yg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hot.manage.annotation.Permissions;
import com.hot.manage.controller.PageController;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.yg.LKTCode;
import com.hot.manage.entity.yg.LKTPatrols;
import com.hot.manage.entity.yg.LKTPatrolsperson;
import com.hot.manage.entity.yg.LKTSelectUserApp;
import com.hot.manage.entity.yg.LKTSignDeviceListApp;
import com.hot.manage.entity.yg.LKTSignGroupList;
import com.hot.manage.entity.yg.LKTSignHistory;
import com.hot.manage.entity.yg.LKTSignList;
import com.hot.manage.entity.yg.LKTSignListApp;
import com.hot.manage.entity.yg.item.LKTPatrolscondition;
import com.hot.manage.entity.yg.item.LKTSignDeviceListAppVaule;
import com.hot.manage.entity.yg.item.LKTSignDeviceListUserVaule;
import com.hot.manage.entity.yg.item.LKTSignDeviceVaule;
import com.hot.manage.entity.yg.item.LKTSignHistorycondition;
import com.hot.manage.entity.yg.item.LKTSignListUpdateVaule;
import com.hot.manage.entity.yg.item.LKTSignListcondition;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.yg.LKTYGService;
import com.hot.manage.utils.ApiResult;

@RestController
public class LYGControllerPatrol implements PageController<LKTPatrolscondition, LKTPatrols> {

	@Autowired
	private LKTYGService lktygService;

	@PostMapping("/yg/LKTPatrols")
	@Permissions("item:yg:read:Patrols")
	@ResponseBody
	// ("巡查人员列表")
	@Override
	public ApiResult page( LKTPatrolscondition lktPatrolscondition) throws MyException {
		PageInfo<LKTPatrols> lktPatrols = lktygService.LKTPatrols(lktPatrolscondition);
		return ApiResult.resultInfo("1", "成功", lktPatrols);
	}

	@PostMapping("/yg/LKTSignList")
	@Permissions("item:yg:read:SignList")
	@ResponseBody
	// ("摇一摇签到列表")
	public ApiResult LKTSignList( LKTSignListcondition lktSignListcondition)
			throws MyException {
		PageInfo<LKTSignList> lktSignList = lktygService.LKTSignList(lktSignListcondition);
		return ApiResult.resultInfo("1", "成功", lktSignList);
	}

	@PostMapping("/yg/LKTSignHistory")
	@Permissions("item:yg:read:SignHistory")
	@ResponseBody
	// ("签到历史记录")
	public ApiResult LKTSignHistory( LKTSignHistorycondition lktSignHistorycondition)
			throws MyException {
		PageInfo<LKTSignHistory> lktSignHistory = lktygService.LKTSignHistory(lktSignHistorycondition);
		return ApiResult.resultInfo("1", "成功", lktSignHistory);
	}

	@PostMapping("/yg/LKTPatrolsFrozen")
	@Permissions("item:yg:update:PatrolsFrozen")
	@ResponseBody
	// ("巡查人员冻结与解冻")
	public ApiResult LKTPatrolsFrozen(Integer userid, Integer isenable) {
		LKTCode lktPatrolsFrozen = lktygService.LKTPatrolsFrozen(userid, isenable);
		return ApiResult.resultInfo("1", "成功", lktPatrolsFrozen);
	}

	@PostMapping("/yg/LKTPatrolsDelete")
	@Permissions("item:yg:update:PatrolsDelete")
	@ResponseBody
	// ("巡查人员删除")
	public ApiResult LKTPatrolsDelete(Integer patrolsid) {
		LKTCode lktPatrolsDelete = lktygService.LKTPatrolsDelete(patrolsid);
		return ApiResult.resultInfo("1", "成功", lktPatrolsDelete);
	}

	@PostMapping("/yg/LKTPatrolsInsert")
	@Permissions("item:yg:add:PatrolsInsert")
	@ResponseBody
	// ("巡查人员添加")
	public ApiResult LKTPatrolsInsert(Integer adduserid) {
		LKTCode lktPatrolsInsert = lktygService.LKTPatrolsInsert(adduserid);
		return ApiResult.resultInfo("1", "成功", lktPatrolsInsert);
	}

	@PostMapping("/yg/LKTPatrolsperson")
	@Permissions("item:yg:read:Patrolsperson")
	@ResponseBody
	// ("查询可添加为巡检人员的用户列表")
	public ApiResult LKTPatrolsperson(Integer userid) {
		List<LKTPatrolsperson> lktPatrolsperson = lktygService.LKTPatrolsperson(userid);
		return ApiResult.resultInfo("1", "成功", lktPatrolsperson);
	}

	@PostMapping("/yg/LKTPatrolsuser")
	@Permissions("item:yg:read:Patrolsuser")
	@ResponseBody
	// ("查询可更换的巡检人员列表")
	public ApiResult LKTPatrolsuser(Integer userid) {
		List<LKTPatrolsperson> lktPatrolsuser = lktygService.LKTPatrolsuser(userid);
		return ApiResult.resultInfo("1", "成功", lktPatrolsuser);
	}

	@PostMapping("/yg/LKTPatrolChange")
	@Permissions("item:yg:read:PatrolChange")
	@ResponseBody
	// ("更换巡检人员")
	public ApiResult LKTPatrolChange(Integer patrolsided, Integer patrolsid) {
		LKTCode lktPatrolChange = lktygService.LKTPatrolChange(patrolsided, patrolsid);
		return ApiResult.resultInfo("1", "成功", lktPatrolChange);
	}

	@PostMapping("/yg/LKTSignListDelete")
	@Permissions("item:yg:del:SignListDelete")
	@ResponseBody
	// ("摇一摇签到列表数据删除")
	public ApiResult LKTSignListDelete(Integer id) {
		LKTCode lktSignListDelete = lktygService.LKTSignListDelete(id);
		return ApiResult.resultInfo("1", "成功", lktSignListDelete);
	}

	@PostMapping("/yg/LKTSignGroupList")
	@Permissions("item:yg:read:SignGroupList")
	@ResponseBody
	// ("添加签到设备可选的关联项目")
	public ApiResult LKTSignGroupList() {
		List<LKTSignGroupList> lktSignGroupList = lktygService.LKTSignGroupList();
		return ApiResult.resultInfo("1", "成功", lktSignGroupList);
	}

	@PostMapping("/yg/LKTSignDeviceUser")
	@Permissions("item:yg:read:SignDevice")
	@ResponseBody
	// ("添加签到设备")
	public ApiResult LKTSignDeviceUser(LKTSignDeviceVaule lktSignDeviceVaule) {
		LKTCode lktSignDeviceUser = lktygService.LKTSignDeviceUser(lktSignDeviceVaule);
		return ApiResult.resultInfo("1", "成功", lktSignDeviceUser);
	}

	@PostMapping("/yg/LKTSignListUpdateUser")
	@Permissions("item:yg:update:SignListUpdate")
	@ResponseBody
	// ("修改签到列表数据")
	public ApiResult LKTSignListUpdateUser(LKTSignListUpdateVaule lktSignListUpdateVaule) {
		LKTCode lktSignListUpdateUser = lktygService.LKTSignListUpdateUser(lktSignListUpdateVaule);
		return ApiResult.resultInfo("1", "成功", lktSignListUpdateUser);
	}

	@PostMapping("/yg/LKTSignDeviceList")
	@Permissions("item:yg:read:SignDeviceList")
	@ResponseBody
	// ("设备签到列表")
	public ApiResult LKTSignDeviceList(LKTSignListcondition lktSignListcondition) throws MyException {
		PageInfo<LKTSignList> lktSignDeviceList = lktygService.LKTSignDeviceList(lktSignListcondition);
		return ApiResult.resultInfo("1", "成功", lktSignDeviceList);
	}

	@PostMapping("/yg/LKTSignDeviceDelete")
	@Permissions("item:yg:del:SignDeviceDelete")
	@ResponseBody
	// ("设备签到列表数据删除")
	public ApiResult LKTSignDeviceDelete(Integer id) {
		LKTCode lktSignDeviceDelete = lktygService.LKTSignDeviceDelete(id);
		return ApiResult.resultInfo("1", "成功", lktSignDeviceDelete);
	}

	@PostMapping("/yg/LKTSignDeviceHistory")
	@Permissions("item:yg:read:SignDeviceHistory")
	@ResponseBody
	// ("设备签到历史记录")
	public ApiResult LKTSignDeviceHistory(LKTSignHistorycondition lktSignHistorycondition)
			throws MyException {
		// TODO Auto-generated method stub
		PageInfo<LKTSignHistory> lktSignDeviceHistory = lktygService.LKTSignDeviceHistory(lktSignHistorycondition);
		return ApiResult.resultInfo("1", "成功", lktSignDeviceHistory);
	}

	@PostMapping("/yg/LKTSignDeviceListUser")
	@Permissions("item:yg:update:SignDeviceListUser")
	@ResponseBody
	// ("分配设备巡检人员")
	public ApiResult LKTSignDeviceListUser(LKTSignDeviceListUserVaule lktSignDeviceListUserVaule) {
		LKTCode lktSignDeviceListUser = lktygService.LKTSignDeviceListUser(lktSignDeviceListUserVaule);
		return ApiResult.resultInfo("1", "成功", lktSignDeviceListUser);
	}

	@PostMapping("/yg/LKTSignDeviceListApp")
	@Permissions("item:yg:read:SignDeviceListApp")
	@ResponseBody
	// ("APP巡检人员设备签到列表")
	public ApiResult LKTSignDeviceListApp(LKTSignDeviceListAppVaule lktSignDeviceListAppVaule) {
		List<LKTSignDeviceListApp> lktSignDeviceListApp = lktygService.LKTSignDeviceListApp(lktSignDeviceListAppVaule);
		return ApiResult.resultInfo("1", "成功", lktSignDeviceListApp);
	}

	@PostMapping("/yg/LKTSelectUserApp")
	@Permissions("item:yg:read:UserApp")
	@ResponseBody
	// ("根据userid查询用户工号姓名APP")
	public ApiResult LKTSelectUserApp(Integer userid) {
		LKTSelectUserApp lktSelectUserApp = lktygService.LKTSelectUserApp(userid);
		return ApiResult.resultInfo("1", "成功", lktSelectUserApp);
	}

	@PostMapping("/yg/LKTSignDeviceApp")
	@Permissions("item:yg:add:SignDeviceApp")
	@ResponseBody
	// ("App巡检人员签到")
	public ApiResult LKTSignDeviceApp(Integer userid, Integer id, Integer state, Integer type) {
		LKTCode lktSignDeviceApp = lktygService.LKTSignDeviceApp(userid, id, state, type);
		return ApiResult.resultInfo("1", "成功", lktSignDeviceApp);
	}

	@PostMapping("/yg/LKTSignListApp")
	@Permissions("item:yg:read:SignListApp")
	@ResponseBody
	// ("App巡检-摇一摇查询巡检设备")
	public ApiResult LKTSignListApp(Integer userid) {
		List<LKTSignListApp> lktSignListApp = lktygService.LKTSignListApp(userid);
		return ApiResult.resultInfo("1", "成功", lktSignListApp);
	}
}
