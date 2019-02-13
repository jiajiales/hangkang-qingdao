package com.hotcomm.prevention.controller.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.prevention.bean.mysql.common.entity.TPowerGroup;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.common.TPowerGroupService;
import com.hotcomm.prevention.utils.ApiResult;

@RestController
public class RolesController {
	@Autowired
	TPowerGroupService powerGroupService;

	/**
	 * 系统设置->角色管理->添加角色
	 */
	@PostMapping("/roles/insert")
	public ApiResult insert(TPowerGroup role) throws MyException {
		powerGroupService.insert(role);
		return ApiResult.resultInfo("1", "请求成功", null);
	}

	/**
	 * 系统设置->角色管理->修改角色
	 */
	@PostMapping("/roles/update")
	public ApiResult update(TPowerGroup role) throws MyException {
		powerGroupService.update(role);
		return ApiResult.resultInfo("1", "请求成功", null);
	}

	/**
	 * 系统设置->角色管理->删除角色
	 */
	@PostMapping("/roles/delete")
	public ApiResult delete(Integer id) throws MyException {
		powerGroupService.delete(id);
		return ApiResult.resultInfo("1", "请求成功", null);
	}

	/**
	 * 系统设置->角色管理->查看所有的角色
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/roles/selectAllRoles")
	public ApiResult selectAllRoles(Integer userid) throws MyException {
		List<TPowerGroup> roles = powerGroupService.selectAllRoles(userid);
		return ApiResult.resultInfo("1", "请求成功", roles);
	}

	/**
	 * 查询指定的用户角色
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/roles/selectByUserId")
	public ApiResult selectByUserId(Integer id) throws MyException {
		TPowerGroup role = powerGroupService.selectByUserid(id);
		return ApiResult.resultInfo("1", "请求成功", role);
	}

}
