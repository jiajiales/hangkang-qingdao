package com.hot.manage.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hot.manage.annotation.Permissions;
import com.hot.manage.controller.CommonController;
import com.hot.manage.entity.system.TPowerGroup;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.system.TPowerGroupService;
import com.hot.manage.utils.ApiResult;

@RestController
public class RolesController implements CommonController<TPowerGroup, TPowerGroup, Integer> {
	@Autowired
	private TPowerGroupService powerGroupService;

	/**
	 * 添加角色
	 */
	@PostMapping("/roles/insert")
	@Permissions("roles:add")
	@Override
	public ApiResult insert(TPowerGroup role, Integer userid) throws MyException {
		ApiResult resultInfo = null;
		Integer insert = powerGroupService.insertObject(role, userid);
		if (insert <= 0) {
			resultInfo = ApiResult.resultInfo("0", "添加角色失败！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "添加角色成功！！",null);
		}
		return resultInfo;
	}
	
	/**
	 * 修改角色
	 */
	@PostMapping("/roles/update")
	@Permissions("roles:update")
	@Override
	public ApiResult update(TPowerGroup p) throws MyException {
		ApiResult resultInfo = null;
		Integer update = powerGroupService.updateObject(p);
		if (update <= 0) {
			resultInfo = ApiResult.resultInfo("0", "修改角色失败！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "修改角色成功！！",null);
		}
		return resultInfo;
	}

	/**
	 * 删除角色
	 */
	@PostMapping("/roles/delete")
	@Permissions("roles:del")
	@Override
	public ApiResult delete(Integer id) throws MyException {
		ApiResult resultInfo = null;
		Integer del = powerGroupService.delObject(id);
		if (del <= 0) {
			resultInfo = ApiResult.resultInfo("0", "删除角色失败！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "删除角色成功！！",null);
		}
		return resultInfo;
	}

	@Override
	public ApiResult select(Integer r, Integer b) throws MyException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 根据用户ID查询所有的角色
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/roles/selectAllRoles")
	@Permissions("roles:selectAllRoles:query")
	public ApiResult selectAllRoles(Integer userid) throws MyException {
		List<TPowerGroup> selectAllRoleByUserId = powerGroupService.selectAllRoleByUserId(userid);
		return ApiResult.resultInfo("1", "成功", selectAllRoleByUserId);
	}
	
	/**
	 * 查询指定用户的角色
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/roles/selectByUserId")
	@Permissions("roles:selectByUserId:query")
	public ApiResult selectByUserId(Integer userid) throws MyException {
		TPowerGroup selectByUserId = powerGroupService.selectByUserId(userid);
		return ApiResult.resultInfo("1", "成功", selectByUserId);
	}
}
