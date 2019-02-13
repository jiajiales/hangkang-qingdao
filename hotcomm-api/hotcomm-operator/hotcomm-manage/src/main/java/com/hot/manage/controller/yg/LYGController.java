package com.hot.manage.controller.yg;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hot.manage.annotation.Permissions;
import com.hot.manage.controller.PageController;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.yg.LKTCode;
import com.hot.manage.entity.yg.LKTDeviceAll;
import com.hot.manage.entity.yg.LKTEstablishygAlarmAndEvent;
import com.hot.manage.entity.yg.LKTEstablishygDevice;
import com.hot.manage.entity.yg.LKTInstructionsAll;
import com.hot.manage.entity.yg.LKTMyproject;
import com.hot.manage.entity.yg.LKTSelctDevOnid;
import com.hot.manage.entity.yg.LKTSelectAllevent;
import com.hot.manage.entity.yg.LKTSelectGroupWorkFather;
import com.hot.manage.entity.yg.LKTSelectHandle;
import com.hot.manage.entity.yg.LKTSelectWork;
import com.hot.manage.entity.yg.LKTSelectdevicenum;
import com.hot.manage.entity.yg.LKTTureWork;
import com.hot.manage.entity.yg.LKTWorkDetailsApp;
import com.hot.manage.entity.yg.LKTWorkHandleApp;
import com.hot.manage.entity.yg.LKTWorkTobetreatedApp;
import com.hot.manage.entity.yg.LKTWorkUntreated;
import com.hot.manage.entity.yg.item.LKTNewWorkVaule;
import com.hot.manage.entity.yg.item.LKTSelectGroupWorkcondition;
import com.hot.manage.entity.yg.item.LKTWorkGbAppVaule;
import com.hot.manage.entity.yg.item.LKTWorkRepairAppVaule;
import com.hot.manage.entity.yg.item.LKTWorkReplaceAppVaule;
import com.hot.manage.entity.yg.item.LKTWorkUntreatedcondition;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.yg.LKTYGService;
import com.hot.manage.utils.ApiResult;

@RestController
public class LYGController implements PageController<LKTSelectGroupWorkcondition, LKTSelectGroupWorkFather> {

	@Autowired
	private LKTYGService lktygService;

	@PostMapping("/yg/LKTMyproject")
	@Permissions("item:yg:read:Myproject")
	@ResponseBody
	// ("我的项目数据and地图项目xy，工单数")
	public ApiResult LKTMyproject(Integer moduleid, Integer userid) throws MyException {
		List<LKTMyproject> lkTMyproject = lktygService.LkTMyproject(moduleid, userid);
		return ApiResult.resultInfo("1", "成功", lkTMyproject);
	}

	@PostMapping("/yg/LKTSelectdevicenum")
	@Permissions("item:yg:read:Devicenum")
	@ResponseBody
	// ("地图设备总数查询")
	public ApiResult LKTSelectdevicenum(Integer moduleid, Integer userid) throws MyException {
		LKTSelectdevicenum lktSelectdevicenum = lktygService.LKTSelectdevicenum(moduleid, userid);
		return ApiResult.resultInfo("1", "成功", lktSelectdevicenum);
	}

	@PostMapping("/yg/LKTSelectGroupWork")
	@Permissions("item:yg:read:GroupWork")
	@ResponseBody
	// ("查询全部工单列表（可按项目组查）")
	@Override
	public ApiResult page(LKTSelectGroupWorkcondition lktSelectGroupWorkcondition)
			throws MyException {
		PageInfo<LKTSelectGroupWorkFather> lktSelectGroupWork = lktygService.LKTSelectGroupWork(lktSelectGroupWorkcondition);
		return ApiResult.resultInfo("1", "成功", lktSelectGroupWork);
	}

	@PostMapping("/yg/LKTEstablishygAlarmAndEvent")
	@Permissions("item:yg:read:EstablishygAlarmAndEvent")
	@ResponseBody
	// ("查询未处理报警与未处理事件自动带入设备")
	public ApiResult LKTEstablishygAlarmAndEvent() {
		List<LKTEstablishygAlarmAndEvent> lktEstablishygAlarmAndEvent = lktygService.LKTEstablishygAlarmAndEvent();
		return ApiResult.resultInfo("1", "成功", lktEstablishygAlarmAndEvent);
	}

	@PostMapping("/yg/LKTSelectAlarmAndEvent")
	@Permissions("item:yg:read:AlarmAndEvent")
	@ResponseBody
	// ("根据id查询未处理报警与未处理事件自动带入设备")
	public ApiResult LKTSelectAlarmAndEvent(Integer type, String id) {
		List<LKTEstablishygAlarmAndEvent> lktSelectAlarmAndEvent = lktygService.LKTSelectAlarmAndEvent(type, id);
		return ApiResult.resultInfo("1", "成功", lktSelectAlarmAndEvent);
	}

	@PostMapping("/yg/LKTEstablishygDevice")
	@Permissions("item:yg:read:EstablishygDevice")
	@ResponseBody
	// ("手动添加设备列表查询")
	public ApiResult LKTEstablishygDevice(Integer moduleid, Integer userid) {
		List<LKTEstablishygDevice> lktEstablishygDevice = lktygService.LKTEstablishygDevice(moduleid, userid);
		return ApiResult.resultInfo("1", "成功", lktEstablishygDevice);
	}

	@PostMapping("/yg/LKTSelctDevOnid")
	@Permissions("item:yg:read:DevOnid")
	@ResponseBody
	// ("根据设备id查询设备下的未处理报警与未处理事件")
	public ApiResult LKTSelctDevOnid(String id) {
		List<Integer> list = new ArrayList<Integer>();
		String[] sourceStrArray = id.split(",");
		for (int i = 0; i < sourceStrArray.length; i++) {
			list.add(Integer.valueOf(sourceStrArray[i]));
		}
		List<LKTSelctDevOnid> lktSelctDevOnid = lktygService.LKTSelctDevOnid(list);
		return ApiResult.resultInfo("1", "成功", lktSelctDevOnid);
	}

	@PostMapping("/yg/LKTSelectHandle")
	@Permissions("item:yg:read:Handle")
	@ResponseBody
	// ("根据创建工单用户id查询其组下可分配任务的处理人列表")
	public ApiResult LKTSelectHandle(Integer userid) {
		List<LKTSelectHandle> lktSelectHandle = lktygService.LKTSelectHandle(userid);
		return ApiResult.resultInfo("1", "成功", lktSelectHandle);
	}

	@PostMapping("/yg/LKTNewWork")
	@Permissions("item:yg:add:NewWork")
	@ResponseBody
	// ("创建工单 ")
	public ApiResult LKTNewWork(LKTNewWorkVaule lktNewWorkVaule) {
		if (lktNewWorkVaule.getDev_id() == null || lktNewWorkVaule.getAdduserid() == null
				|| lktNewWorkVaule.getAlarm_id() == null || lktNewWorkVaule.getAssignid() == null
				|| lktNewWorkVaule.getComplete_time() == null || lktNewWorkVaule.getDescrition() == null
				|| lktNewWorkVaule.getEvent_id() == null || lktNewWorkVaule.getTitle() == null) {
			throw new MyException("0", "必填字段请勿留空！！");
		}
		ApiResult resultInfo = null;
		LKTCode insertObject = lktygService.LKTNewWork(lktNewWorkVaule);
		if (insertObject.getCode() <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！",null);
		}
		return resultInfo;
	}

	@PostMapping("/yg/LKTSelectWork")
	@Permissions("item:yg:read:Work")
	@ResponseBody
	// ("工单内容查询 ")
	public ApiResult LKTSelectWork(Integer workid) {
		LKTSelectWork lktSelectWork = lktygService.LKTSelectWork(workid);
		return ApiResult.resultInfo("1", "成功", lktSelectWork);
	}

	@PostMapping("/yg/LKTSelectAllevent")
	@Permissions("item:yg:read:Allevent")
	@ResponseBody
	// ("工单关联事件")
	public ApiResult LKTSelectAllevent(Integer workid) {
		List<LKTSelectAllevent> lktSelectAllevent = lktygService.LKTSelectAllevent(workid);
		return ApiResult.resultInfo("1", "成功", lktSelectAllevent);
	}

	@PostMapping("/yg/LKTDeviceAll")
	@Permissions("item:yg:read:DeviceAll")
	@ResponseBody
	// ("工单关联设备")
	public ApiResult LKTDeviceAll(Integer workid) {
		List<LKTDeviceAll> lktDeviceAll = lktygService.LKTDeviceAll(workid);
		return ApiResult.resultInfo("1", "成功", lktDeviceAll);
	}

	@PostMapping("/yg/LKTInstructionsAll")
	@Permissions("item:yg:read:InstructionsAll")
	@ResponseBody
	// ("工单关联工作指示")
	public ApiResult LKTInstructionsAll(Integer workid) {
		List<LKTInstructionsAll> lktInstructionsAll = lktygService.LKTInstructionsAll(workid);
		return ApiResult.resultInfo("1", "成功", lktInstructionsAll);
	}

	@PostMapping("/yg/LKTTureWork")
	@Permissions("item:yg:read:TureWork")
	@ResponseBody
	// ("查询已处理或挂起工单内容")
	public ApiResult LKTTureWork(Integer workid) {
		List<LKTTureWork> lktTureWork = lktygService.LKTTureWork(workid);
		return ApiResult.resultInfo("1", "成功", lktTureWork);
	}

	@PostMapping("/yg/LKTDeleteWork")
	@Permissions("item:yg:del:Work")
	@ResponseBody
	// ("删除工单")
	public ApiResult LKTDeleteWork(Integer workid) {
		ApiResult resultInfo = null;
		LKTCode insertObject = lktygService.LKTDeleteWork(workid);
		if (insertObject.getCode() <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！",null);
		}
		return resultInfo;
	}

	@PostMapping("/yg/LKTWorkExamine")
	@Permissions("item:yg:update:WorkExamine")
	@ResponseBody
	// ("审核工单")
	public ApiResult LKTWorkExamine(Integer userid, Integer workid, Integer audit, Integer pdid) {
		ApiResult resultInfo = null;
		LKTCode insertObject = lktygService.LKTWorkExamine(userid, workid, audit, pdid);
		if (insertObject.getCode() <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！",null);
		}
		return resultInfo;
	}

	@PostMapping("/yg/LKTWorkUntreated")
	@Permissions("item:yg:read:UntreatedApp")
	@ResponseBody
	// ("App待处理工单")
	public ApiResult LKTWorkUntreated(LKTWorkUntreatedcondition lktWorkUntreatedcondition) {
		List<LKTWorkUntreated> lktWorkUntreated = lktygService.LKTWorkUntreated(lktWorkUntreatedcondition);
		return ApiResult.resultInfo("1", "成功", lktWorkUntreated);
	}

	@PostMapping("/yg/LKTWorkDetailsApp")
	@Permissions("item:yg:read:DetailsApp")
	@ResponseBody
	// ("App待处理工单详情")
	public ApiResult LKTWorkDetailsApp(Integer woid) {
		LKTWorkDetailsApp lktWorkDetailsApp = lktygService.LKTWorkDetailsApp(woid);
		return ApiResult.resultInfo("1", "成功", lktWorkDetailsApp);
	}

	@PostMapping("/yg/LKTWorkRepairApp")
	@Permissions("item:yg:update:RepairApp")
	@ResponseBody
	// ("App工单处理-设备维修")
	public ApiResult LKTWorkRepairApp(LKTWorkRepairAppVaule lktWorkRepairAppVaule) {
		ApiResult resultInfo = null;
		LKTCode insertObject = lktygService.LKTWorkRepairApp(lktWorkRepairAppVaule);
		if (insertObject.getCode() <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！",null);
		}
		return resultInfo;
	}

	@PostMapping("/yg/LKTWorkGbApp")
	@Permissions("item:yg:update:GbApp")
	@ResponseBody
	// ("App工单处理-设备挂起")
	public ApiResult LKTWorkGbApp(LKTWorkGbAppVaule lktWorkGbAppVaule) {
		ApiResult resultInfo = null;
		LKTCode insertObject = lktygService.LKTWorkGbApp(lktWorkGbAppVaule);
		if (insertObject.getCode() <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！",null);
		}
		return resultInfo;
	}

	@PostMapping("/yg/lktWorkReplaceAppVaule")
	@Permissions("item:yg:update:ReplaceApp")
	@ResponseBody
	// ("App工单详情-设备更换")
	public ApiResult LKTWorkReplaceApp(LKTWorkReplaceAppVaule lktWorkReplaceAppVaule) {
		ApiResult resultInfo = null;
		LKTCode insertObject = lktygService.LKTWorkReplaceApp(lktWorkReplaceAppVaule);
		if (insertObject.getCode() <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！",null);
		}
		return resultInfo;
	}

	@PostMapping("/yg/LKTWorkHandleApp")
	@Permissions("item:yg:update:HandleApp")
	@ResponseBody
	// ("App工单详情-已处理或挂起")
	public ApiResult LKTWorkHandleApp(Integer woid) {
		LKTWorkHandleApp lktWorkHandleApp = lktygService.LKTWorkHandleApp(woid);
		return ApiResult.resultInfo("1", "成功", lktWorkHandleApp);
	}

	@PostMapping("/yg/LKTWorkTobetreatedApp")
	@Permissions("item:yg:read:TobetreatedApp")
	@ResponseBody
	// ("App已处理工单列表")
	public ApiResult LKTWorkTobetreatedApp(LKTWorkUntreatedcondition lktWorkUntreatedcondition) {
		List<LKTWorkTobetreatedApp> lktWorkTobetreatedApp = lktygService.LKTWorkTobetreatedApp(lktWorkUntreatedcondition);
		return ApiResult.resultInfo("1", "成功", lktWorkTobetreatedApp);
	}

	@PostMapping("/yg/LKTReminder")
	@Permissions("item:yg:read:LKTReminder")
	@ResponseBody
	// ("催单")
	public ApiResult LKTReminder(Integer userid, Integer workid, Integer assignid) {
		ApiResult resultInfo = null;
		LKTCode insertObject = lktygService.LKTReminder(userid, workid, assignid);
		if (insertObject.getCode() <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！",null);
		}
		return resultInfo;
	}
}
