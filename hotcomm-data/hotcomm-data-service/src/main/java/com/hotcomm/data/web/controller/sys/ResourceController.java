package com.hotcomm.data.web.controller.sys;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.data.bean.params.sys.ResourceParams;
import com.hotcomm.data.bean.vo.sys.ResourceVO;
import com.hotcomm.data.service.ResourceService;
import com.hotcomm.data.web.controller.comm.CrudController;
import com.hotcomm.framework.annotation.Function;
import com.hotcomm.framework.annotation.Param;
import com.hotcomm.framework.annotation.ParamsValidate;
import com.hotcomm.framework.web.exception.HKException;
import com.hotcomm.framework.web.result.ApiResult;

@RestController
public class ResourceController implements CrudController<ResourceParams, Integer, ResourceVO> {

	@Resource
	private ResourceService resourceService;

	@Function(key = "sys-resource-get")
	@ParamsValidate(validateParams = { @Param(key = "id", code = "RES_F01") })
	@RequestMapping("/sys/resource/get")
	@Override
	public ApiResult get(Integer id) throws HKException {
		return ApiResult.success(resourceService.getBean(id));
	}

	@Function(key = "sys-resource-update")
	@ParamsValidate(validateParams = { @Param(key = "id", code = "RES_F01"), @Param(key = "name", code = "RES_F02") })
	@RequestMapping("/sys/resource/update")
	@Override
	public ApiResult update(ResourceParams params) throws HKException {
		resourceService.updateBean(params);
		return ApiResult.success();
	}

	@Function(key = "sys-resource-del")
	@ParamsValidate(validateParams = { @Param(key = "id", code = "RES_F01") })
	@RequestMapping("/sys/resource/del")
	@Override
	public ApiResult del(Integer id) throws HKException {
		resourceService.delBean(id);
		return ApiResult.success();
	}

	@Function(key = "sys-resource-add")
	@ParamsValidate(validateParams = { @Param(key = "name", code = "RES_F02"), 
									   @Param(key = "path", code = "RES_F03"), 
									   @Param(key = "type", code = "RES_F04"), 
									   @Param(key = "weight", code = "RES_F05"), 
									   @Param(key = "status", code = "RES_F06"), 
									   @Param(key = "key", code = "RES_F08") })
	@RequestMapping("/sys/resource/add")
	@Override
	public ApiResult add(ResourceParams params) throws HKException {
		resourceService.addBean(params);
		return ApiResult.success();
	}

	@Function(key = "sys-resource-list")
	@RequestMapping("/sys/resource/list")
	public ApiResult list() {
		return ApiResult.success(resourceService.queryTree());
	}

	@Function(key = "sys-resource-menus")
	@RequestMapping("/sys/resource/menus")
	public ApiResult getMenus() {
		return ApiResult.success(resourceService.getMenus());
	}

}
