package com.hotcomm.prevention.controller.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.prevention.bean.mysql.common.params.UserItemParam;
import com.hotcomm.prevention.bean.mysql.common.vo.ModuleItemNode;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.common.TUserDgroupRelationService;
import com.hotcomm.prevention.utils.ApiResult;

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
	public ApiResult ModuleItemNode(Integer userid,Integer systemuserid) throws MyException {
		List<ModuleItemNode> moduleItemNode = userDgroupRelationService.ModuleItemNode(systemuserid);
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
	public ApiResult userItemRelation(UserItemParam param) throws MyException {
		userDgroupRelationService.userItemRelation(param);
		return ApiResult.resultInfo("1", "操作成功！！", null);
	}
}
