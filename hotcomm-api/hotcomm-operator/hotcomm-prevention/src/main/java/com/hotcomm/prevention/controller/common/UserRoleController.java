package com.hotcomm.prevention.controller.common;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.prevention.bean.mysql.common.entity.TUserPgrouRelationp;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.common.TUserPgrouRelationpService;
import com.hotcomm.prevention.utils.ApiResult;
import com.hotcomm.prevention.utils.ConverUtil;

@RestController
public class UserRoleController {
	@Autowired
	TUserPgrouRelationpService userPgrouRelationpService;
	
	/**
	 * 系统设置->用户管理->用户与角色绑定、解除绑定
	 * @param roleid 角色ID
	 * @param id 被绑定人ID
	 * @param userid 当前登陆用户ID
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/UserRole/userRoleRelation")
	public ApiResult userRoleRelation(Integer roleid, Integer id, Integer userid) throws MyException {
		Integer selectRoleid = userPgrouRelationpService.selectRoleid(id);
		if (roleid == null) {
			if (selectRoleid == null) {
				return ApiResult.resultInfo("1", "此用户没有绑定的角色", null);
			}
			// 解除绑定
			TUserPgrouRelationp relation = new TUserPgrouRelationp();
			relation.setUserid(id);
			relation.setIsenable(false);
			relation.setIsdelete(true);
			userPgrouRelationpService.update(relation);
			return ApiResult.resultInfo("1", "解除绑定成功", null);
		} else if (selectRoleid == null) {
			TUserPgrouRelationp relation = new TUserPgrouRelationp();
			relation.setUserid(id);
			relation.setAdduserid(userid);
			relation.setGroupid(roleid);
			relation.setAddtime(ConverUtil.dateForString(new Date()));
			userPgrouRelationpService.insert(relation);
			return ApiResult.resultInfo("1", "绑定成功", null);
		} else {
			TUserPgrouRelationp relation = new TUserPgrouRelationp();
			relation.setGroupid(roleid);
			relation.setUserid(id);
			userPgrouRelationpService.update(relation);
			return ApiResult.resultInfo("1", "绑定成功", null);
		}
	}

}
