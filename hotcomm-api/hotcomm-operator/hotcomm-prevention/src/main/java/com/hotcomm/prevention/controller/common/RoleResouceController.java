package com.hotcomm.prevention.controller.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.common.TPowerGroupRelationService;
import com.hotcomm.prevention.utils.ApiResult;

@RestController
public class RoleResouceController {
	@Autowired
	TPowerGroupRelationService powerGroupRelationService;
	
	/**
	 * 系统设置->角色管理->角色与资源绑定、解绑
	 * @param roleid
	 * @param powerid 字符串以逗号分割
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/RoleResouce/RoleResouceRelation")
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
	public ApiResult selectResByRole(Integer roleid){
		List<Integer> selectResByRole = powerGroupRelationService.selectResByRole(roleid);
		return ApiResult.resultInfo("1", "成功", selectResByRole);
	}

}
