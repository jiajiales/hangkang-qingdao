package com.hotcomm.data.web.controller.service;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.data.bean.params.service.device.DeviceTypePageParams;
import com.hotcomm.data.bean.params.service.device.DeviceTypeParams;
import com.hotcomm.data.bean.vo.device.DeviceTypePageVO;
import com.hotcomm.data.bean.vo.device.DeviceTypeVO;
import com.hotcomm.data.comm.PageInfo;
import com.hotcomm.data.service.DeviceTypeService;
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
public class DeviceTypeController extends BaseController implements CrudController<DeviceTypeParams, Integer, DeviceTypeVO>,
		PageController<DeviceTypePageParams, DeviceTypePageVO> {

	@Resource
	private DeviceTypeService deviceTypeService;

	/*
	 * 设备类型-获取
	 */
	@Function(key = "device-type-get")
	@ParamsValidate(validateParams = { @Param(key = "typeId", code = "DEVICETYPE_F01") })
	@RequestMapping("/device/type/get")
	@LogEvent(code = "DEVICETYPE_GET")
	@Override
	public ApiResult get(Integer typeId) throws HKException {
		return ApiResult.success(deviceTypeService.getBean(typeId));
	}

	/*
	 * 设备类型-更新
	 */
	@Function(key = "device-type-update")
	@ParamsValidate(validateParams = { @Param(key = "typeId", code = "DEVICETYPE_F01"), 
			                           @Param(key = "typeName", code = "DEVICETYPE_F03") })
	@RequestMapping("/device/type/update")
	@LogEvent(code = "DEVICETYPE_UPD")
	@Override
	public ApiResult update(DeviceTypeParams params) throws HKException {
		deviceTypeService.updateBean(params);
		return ApiResult.success("");
	}

	/*
	 * 设备类型-删除
	 */
	@Function(key = "device-type-del")
	@ParamsValidate(validateParams = { @Param(key = "typeId", code = "DEVICETYPE_F01") })
	@RequestMapping("/device/type/del")
	@LogEvent(code = "DEVICETYPE_DEL")
	@Override
	public ApiResult del(Integer typeId) throws HKException {
		deviceTypeService.delBean(typeId);
		return ApiResult.success();
	}

	/*
	 * 设备类型-增加
	 */
	@Function(key = "device-type-add")
	@ParamsValidate(validateParams = { @Param(key = "typeName", code = "DEVICETYPE_F03") })
	@RequestMapping("/device/type/add")
	@LogEvent(code = "DEVICETYPE_ADD")
	@Override
	public ApiResult add(DeviceTypeParams params) throws HKException {
		params.setCreateUser(getLoginMember().getMemberName()); // 获取登入用户
		deviceTypeService.addBean(params);
		return ApiResult.success();
	}

	/*
	 * 设备类型-分页查询
	 */
	@Function(key = "device-type-page")
	@ParamsValidate(validateParams = { @Param(key = "page", code = "DEVICETYPE_F04"),
		       			               @Param(key = "rows", code = "DEVICETYPE_F05") })
	@RequestMapping("/device/type/page")
	@LogEvent(code = "DEVICETYPE_PAG")
	@Override
	public PageInfo<DeviceTypePageVO> page(DeviceTypePageParams params) throws HKException {
		PageInfo<DeviceTypePageVO> page = deviceTypeService.queryPage(params);
		return page;
	}

	/*
	 * 设备类型-列表
	 */
	@Function(key = "device-type-list")
	@RequestMapping("/device/type/list")
	@LogEvent(code = "DEVICETYPE_LIST")
	public ApiResult queryList() throws HKException {
		return ApiResult.success(deviceTypeService.queryList());
	}

}
