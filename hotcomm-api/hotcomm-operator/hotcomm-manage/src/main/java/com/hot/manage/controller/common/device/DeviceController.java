package com.hot.manage.controller.common.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hot.manage.annotation.Permissions;
import com.hot.manage.entity.common.DeviceForDevnum;
import com.hot.manage.entity.common.DeviceInsertParam;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.common.dev.DeviceService;
import com.hot.manage.service.dc.DCDeviceService;
import com.hot.manage.utils.ApiResult;

@RestController
@RequestMapping("Device")
public class DeviceController {

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private DCDeviceService dcDeviceService;

	// app设备详情
	@Permissions("Device:selectAPPDeviceInfo:query")
	@PostMapping("/selectAPPDeviceInfo")
	public ApiResult selectAPPDeviceInfo(Integer deviceid, Integer moduleid) throws MyException {
		ApiResult apiResult = new ApiResult("1", "执行成功", deviceService.selectAPPDeviceInfo(deviceid, moduleid));
		return apiResult;
	}

	// app设备安装
	@Permissions("Device:insertAppDevice:add")
	@PostMapping("/insertAppDevice")
	public ApiResult insertAppDevice(Integer moduleid, Integer userid, DeviceInsertParam deviceInsertParam)
			throws MyException {
		ApiResult apiResult = new ApiResult("1", "执行成功",
				deviceService.insertAppDevice(moduleid, userid, deviceInsertParam));
		return apiResult;
	}

	// app可选项目组
	@Permissions("Device:selectOptionalGroup:query")
	@PostMapping("/selectOptionalGroup")
	public ApiResult selectOptionalGroup(Integer userid) throws MyException {
		ApiResult apiResult = new ApiResult("1", "执行成功", deviceService.selectOpaitonalGroup(userid));
		return apiResult;
	}

	// app可选责任人
	@Permissions("Device:selectOptionalUser:query")
	@PostMapping("/selectOptionalUser")
	public ApiResult selectOptionalUser(Integer userid) throws MyException {
		ApiResult apiResult = new ApiResult("1", "执行成功", deviceService.selectOpationalUserid(userid));
		return apiResult;
	}

	// app根据编号查找设备
	@Permissions("Device:selectAppDeviceForDevnum:query")
	@PostMapping("/selectAppDeviceForDevnum")
	public ApiResult selectAppDeviceForDevnum(String devnum) throws MyException {
		DeviceForDevnum devnum2 = deviceService.selectAppDeviceForDevnum(devnum);
		ApiResult apiResult;
		if (devnum2 != null) {
			apiResult = new ApiResult("1", "执行成功", devnum2);
		} else {
			apiResult = new ApiResult("0", "该设备不存在", null);
		}
		return apiResult;
	}

	// app根据mac查找设备
	@Permissions("")
	@PostMapping("/selectAppDeviceForMac")
	public ApiResult selectAppDeviceForMac(String mac) throws MyException {
		DeviceForDevnum devnum2 = deviceService.selectAppDeviceForMac(mac);
		ApiResult apiResult;
		if (devnum2 != null) {
			apiResult = new ApiResult("1", "执行成功", devnum2);
		} else {
			apiResult = new ApiResult("0", "该设备不存在", null);
		}
		return apiResult;
	}

	// app根据项目组id获取楼层信息
	@Permissions("Device:selectDeviceToFloor:query")
	@PostMapping("/selectDeviceToFloor")
	public ApiResult selectDeviceToFloor(Integer userid, Integer groupid) throws MyException {
		ApiResult apiResult = new ApiResult("1", "执行成功", dcDeviceService.selectDeviceToFloor(groupid));
		return apiResult;
	}

	// app根据模块获取设备类型
	@Permissions("")
	@PostMapping("/selectAPPDeviceType")
	public ApiResult selectAPPDeviceType(Integer moduleid) throws MyException {
		ApiResult apiResult = new ApiResult("1", "执行成功", deviceService.selectAppDeviceType(moduleid));
		return apiResult;
	}

	// 报警类型统计

	@Permissions("Devcie:selectAlarmTypeNums")
	@PostMapping("/selectAlarmNums")
	public ApiResult selectAlarmTypeNums(Integer moduleID, Integer userid, Integer queryType) throws MyException {
		ApiResult apiResult = new ApiResult("1", "执行成功", deviceService.selectAlarmNums(moduleID, userid, queryType));
		return apiResult;
	}
	// 报警处理统计

	@Permissions("Devcie:selectAlarmHandleNums")
	@PostMapping("/selectAlarmHandleNums")
	public ApiResult selectAlarmHandleNums(Integer moduleID, Integer userid, Integer queryType) throws MyException {
		ApiResult apiResult = new ApiResult("1", "执行成功",
				deviceService.selectAlarmHandleNums(moduleID, userid, queryType));
		return apiResult;
	}

	// 组报警处理统计

	@Permissions("Devcie:selectGroupAlarmHandleNums")
	@PostMapping("/selectGroupAlarmHandleNums")
	public ApiResult selectGroupAlarmHandleNums(Integer moduleID, Integer userid, Integer queryType, Integer groupid)
			throws MyException {
		ApiResult apiResult = new ApiResult("1", "执行成功",
				deviceService.selectGroupAlarmHandleNums(moduleID, userid, queryType, groupid));
		return apiResult;
	}
	// 组报警类型统计

	@Permissions("Devcie:selectGroupAlarmNums")
	@PostMapping("/selectGroupAlarmNums")
	public ApiResult selectGroupAlarmNums(Integer moduleID, Integer userid, Integer queryType, Integer groupid)
			throws MyException {
		ApiResult apiResult = new ApiResult("1", "执行成功",
				deviceService.selectGroupAlarmNums(moduleID, userid, queryType, groupid));
		return apiResult;
	}

	// 组报警类型统计

	@Permissions("Devcie:selectGroupInfo")
	@PostMapping("/selectGroupInfo")
	public ApiResult selectGroupInfo(Integer moduleid, Integer groupid) throws MyException {
		ApiResult apiResult = new ApiResult("1", "执行成功", deviceService.selectGroupInfo(moduleid, groupid));
		return apiResult;
	}

	@Permissions("Devcie:selectAllGroupByUserId")
	@PostMapping("/selectAllGroupByUserId")
	public ApiResult selectAllGroupByUserId(Integer moduleid, Integer userid) throws MyException {
		ApiResult apiResult = new ApiResult("1", "执行成功", deviceService.selectAllGroupByUserId(moduleid, userid));
		return apiResult;
	}

	@Permissions("Devcie:selectAllDevByUserid")
	@PostMapping("/selectAllDevByUserid")
	public ApiResult selectAllDevByUserid(Integer moduleid, Integer userid) throws MyException {
		ApiResult apiResult = new ApiResult("1", "执行成功", deviceService.selectAllDevByUserid(moduleid, userid));
		return apiResult;
	}

	@Permissions("Devcie:selectAllDevAndGroupByUserId")
	@PostMapping("/selectAllDevAndGroupByUserId")
	public ApiResult selectAllDevAndGroupByUserId(Integer moduleid, Integer userid) throws MyException {
		ApiResult apiResult = new ApiResult("1", "执行成功", deviceService.selectAllDevAndGroupByUserId(moduleid, userid));
		return apiResult;
	}
}
