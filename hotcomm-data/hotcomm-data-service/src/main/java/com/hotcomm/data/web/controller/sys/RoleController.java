package com.hotcomm.data.web.controller.sys;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.data.bean.params.sys.RoleParams;
import com.hotcomm.data.bean.vo.sys.RoleVO;
import com.hotcomm.data.service.RoleService;
import com.hotcomm.data.web.controller.comm.BaseController;
import com.hotcomm.data.web.controller.comm.CrudController;
import com.hotcomm.framework.annotation.Function;
import com.hotcomm.framework.annotation.Param;
import com.hotcomm.framework.annotation.ParamsValidate;
import com.hotcomm.framework.web.exception.HKException;
import com.hotcomm.framework.web.result.ApiResult;

@RestController
public class RoleController extends BaseController implements CrudController<RoleParams, Integer, RoleVO> {

	@Resource
	private RoleService roleService;

	@Function(key = "sys-role-get")
	@ParamsValidate(validateParams = { @Param(key = "id", code = "ROLE_F01") })
	@RequestMapping("/sys/role/get")
	@Override
	public ApiResult get(Integer id) throws HKException {
		return ApiResult.success(roleService.getBean(id));
	}

	@Function(key = "sys-role-update")
	@ParamsValidate(validateParams = { @Param(key = "id", code = "ROLE_F01"),
									   @Param(key = "roleName", code = "权限名称不能为空") })
	@RequestMapping("/sys/role/update")
	@Override
	public ApiResult update(RoleParams params) throws HKException {
		roleService.updateBean(params);
		return ApiResult.success();
	}

	@Function(key = "sys-role-del")
	@ParamsValidate(validateParams = { @Param(key = "id", code = "ROLE_F01") })
	@RequestMapping("/sys/role/del")
	@Override
	public ApiResult del(Integer id) throws HKException {
		roleService.delBean(id);
		return ApiResult.success();
	}

	@Function(key = "sys-role-add")
	@ParamsValidate(validateParams = { @Param(key = "roleName", code = "ROLE_F02") })
	@RequestMapping("/sys/role/add")
	@Override
	public ApiResult add(RoleParams params) throws HKException {
		roleService.addBean(params);
		return ApiResult.success();
	}

	@Function(key = "sys-role-list")
	@RequestMapping("/sys/role/list")
	public ApiResult list() {
		return ApiResult.success(roleService.queryList());
	}

	@Function(key = "sys-role-chexisRole")
	@ParamsValidate(validateParams = { @Param(key = "roleId", code = "ROLE_F01") })
	@RequestMapping("/sys/role/chexisRole")
	public ApiResult chexisRole(Integer roleId) {
		return ApiResult.success(roleService.chexisRole(roleId));
	}

	@Function(key = "sys-role-addRoleResource")
	@RequestMapping("/sys/role/addRoleResource")
	@ParamsValidate(validateParams = { @Param(key = "id", code = "ROLE_F01") })
	public ApiResult addRoleResource(RoleParams params) throws HKException {
		roleService.saveRoleResources(params);
		return ApiResult.success();
	}

	@Function(key = "sys-role-getRoleResource")
	@RequestMapping("/sys/role/getRoleResource")
	@ParamsValidate(validateParams = { @Param(key = "roleId", code = "ROLE_F01") })
	public ApiResult getRoleResource(Integer roleId) throws HKException {
		return ApiResult.success(roleService.getRoleResources(roleId));
	}

}
