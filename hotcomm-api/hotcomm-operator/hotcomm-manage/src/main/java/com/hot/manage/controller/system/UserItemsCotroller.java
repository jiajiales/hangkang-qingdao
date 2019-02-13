package com.hot.manage.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hot.manage.annotation.Permissions;
import com.hot.manage.entity.ModuleItemNode;
import com.hot.manage.entity.item.UserItemParam;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.item.TUserDgroupRelationService;
import com.hot.manage.utils.ApiResult;

@RestController
public class UserItemsCotroller {

	@Autowired
	private TUserDgroupRelationService userDgroupRelationService;

	/**
	 * 当前用户所拥有的模块、项目节点（树形菜单）
	 * 
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/userItems/ModuleItemNode")
	@Permissions("useritems:ModuleItemNode:query")
	public ApiResult ModuleItemNode(Integer userid) throws MyException {
		List<ModuleItemNode> moduleItemNode = userDgroupRelationService.ModuleItemNode(userid);
		return ApiResult.resultInfo("1", "成功", moduleItemNode);
	}
	
	/**
	 * 用户与项目绑定或解除
	 * 
	 * @param param
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/userItems/userItemRelation")
	@Permissions("useritems:userItemRelation:update")
	public ApiResult userItemRelation(UserItemParam param) throws MyException {
		userDgroupRelationService.userItemRelation(param);
		return ApiResult.resultInfo("1", "操作成功！！", null);
	}
}
