package com.hotcomm.data.web.controller.service;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.data.bean.params.service.devicegroup.DeviceGroupPageParams;
import com.hotcomm.data.bean.params.service.devicegroup.DeviceGroupParams;
import com.hotcomm.data.bean.vo.devicegroup.DeviceGroupPageVO;
import com.hotcomm.data.bean.vo.devicegroup.DeviceGroupVO;
import com.hotcomm.data.bean.vo.sys.MemberVO;
import com.hotcomm.data.comm.PageInfo;
import com.hotcomm.data.service.DeviceGroupService;
import com.hotcomm.data.web.controller.comm.BaseController;
import com.hotcomm.data.web.controller.comm.CrudController;
import com.hotcomm.data.web.controller.comm.PageController;
import com.hotcomm.framework.annotation.Function;
import com.hotcomm.framework.annotation.LogEvent;
import com.hotcomm.framework.annotation.Param;
import com.hotcomm.framework.annotation.ParamsValidate;
import com.hotcomm.framework.web.exception.HKException;
import com.hotcomm.framework.web.result.ApiResult;

@RestController
public class DeviceGroupController extends BaseController implements CrudController<DeviceGroupParams, Integer, DeviceGroupVO>,
		PageController<DeviceGroupPageParams, DeviceGroupPageVO> {

	@Resource
	private DeviceGroupService devicegroupservice;

	// 设备组获取
	@Function(key = "device-group-get")
	@PostMapping("/device/group/get")
	@LogEvent(code = "DEVICEGROUP_GET")
	@ParamsValidate(validateParams = { @Param(key = "groupId", code = "DEVICEGROUP_F01") })
	@Override
	public ApiResult get(Integer groupId) throws HKException {
		return ApiResult.success(devicegroupservice.getBean(groupId));
	}

	// 设备组更新
	@Function(key = "device-group-update")
	@PostMapping("/device/group/update")
	@LogEvent(code = "DEVICEGROUP_UPD")
	@ParamsValidate(validateParams = { @Param(key = "groupId", code = "DEVICEGROUP_F01"), 
									   @Param(key = "groupName", code = "DEVICEGROUP_F02"), 
									   @Param(key = "groupStatus", code = "DEVICEGROUP_F04"), 
									   @Param(key = "maxNums", code = "DEVICEGROUP_F05") })
	@Override
	public ApiResult update(DeviceGroupParams params) throws HKException {
		devicegroupservice.updateBean(params);
		return ApiResult.success();
	}

	// 设备组删除
	@Function(key = "device-group-del")
	@PostMapping("/device/group/del")
	@ParamsValidate(validateParams = { @Param(key = "groupId", code = "DEVICEGROUP_F01") })
	@LogEvent(code = "DEVICEGROUP_DEL")
	@Override
	public ApiResult del(Integer groupId) throws HKException {
		devicegroupservice.delBean(groupId);
		return ApiResult.success();
	}

	// 设备组新增
	@Function(key = "device-group-add")
	@PostMapping("/device/group/add")
	@ParamsValidate(validateParams = { @Param(key = "groupName", code = "DEVICEGROUP_F02"), 
									   @Param(key = "groupStatus", code = "DEVICEGROUP_F04"),
									   @Param(key = "maxNums", code = "DEVICEGROUP_F05") })
	@LogEvent(code = "DEVICEGROUP_ADD")
	@Override
	public ApiResult add(DeviceGroupParams params) throws HKException {
		devicegroupservice.addBean(params);
		return ApiResult.success();
	}

	// 设备组分页查询
	@Function(key = "device-group-page")
	@PostMapping("/device/group/page")
	@LogEvent(code = "DEVICEGROUP_PAG")
	@Override
	public PageInfo<DeviceGroupPageVO> page(DeviceGroupPageParams params) throws HKException {
//		MemberVO member = getLoginMember();
//		params.setMemberId(member.getId());
//		params.setMemberType(member.getUserType());
		return devicegroupservice.queryPage(params);
	}

	// 设备组列表
	@Function(key = "device-group-list")
	@PostMapping("/device/group/list")
	@LogEvent(code = "DEVICEGROUP_LIST")
	public ApiResult list(Integer selectMemberId) {
		MemberVO member = getLoginMember();
		return ApiResult.success(devicegroupservice.getGroupList(member.getId(), member.getUserType(), selectMemberId));
	}

	// 设备组分派用户
	@Function(key = "device-group-use_members")
	@PostMapping("/device/group/use_members")
	@ParamsValidate(validateParams = { @Param(key = "groupId", code = "DEVICEGROUP_F01") })
	@LogEvent(code = "DEVICEGROUP_USE")
	public ApiResult useMembers(Integer groupId, String groupMembers) {
		Integer loginMemberId = getLoginMember().getId();
		devicegroupservice.groupAssignedToMember(loginMemberId, groupId, groupMembers);
		return ApiResult.success();
	}

}
