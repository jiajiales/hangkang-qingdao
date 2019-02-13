package com.hot.manage.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hot.manage.annotation.Permissions;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.system.TPowerGroupRelationService;
import com.hot.manage.utils.ApiResult;

@RestController
public class RoleResouceController {

	@Autowired
	private TPowerGroupRelationService powerGroupRelationService;

	/**
	 * 角色资源绑定或解除绑定
	 */
	@PostMapping("/RoleResouce/RoleResouceRelation")
	@Permissions("RoleResouce:RoleResouceRelation:update")
	public ApiResult RoleResouceRelation(Integer roleid, String powerid) throws MyException {
		powerGroupRelationService.RoleResouceRelation(roleid, powerid);
		return ApiResult.resultInfo("1", "成功", null);
	}
	
	/**
	 * 根据角色查询资源
	 * @param roleid
	 * @return
	 */
	@PostMapping("/RoleResouce/selectResByRole")
	@Permissions("RoleResouce:selectResByRole:query")
	public ApiResult selectResByRole(Integer roleid){
		List<Integer> selectResByRole = powerGroupRelationService.selectResByRole(roleid);
		return ApiResult.resultInfo("1", "成功", selectResByRole);
	}
	
	
	/**
	 * 根据角色和父类id查询资源 
	 * @param roleid   
	 * @param fatherId
	 * @return
	 */
	@PostMapping("/RoleResouce/selectResByFatherId")
	@Permissions("RoleResouce:selectResByFatherId:query")
	public ApiResult selectResByFatherId(Integer roleid ,Integer fatherId){
		String selectResByFatherId = powerGroupRelationService.selectResFatherId(roleid,fatherId);
		return ApiResult.resultInfo("1", "成功", selectResByFatherId);
	}
}
