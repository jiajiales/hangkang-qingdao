package com.hot.manage.controller.system;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hot.manage.annotation.Permissions;
import com.hot.manage.entity.system.TUserPgrouRelationp;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.system.TUserPgrouRelationpService;
import com.hot.manage.utils.ApiResult;
import com.hot.manage.utils.ConverUtil;

@RestController
public class UserRoleController {
	@Autowired
	private TUserPgrouRelationpService userPgrouRelationpService;

/*/**
	 * 用户角色绑定
	 * 
	 * @param roleid
	 *            角色ID
	 * @param id
	 *            被绑定用户ID
	 * @param userid
	 *            登陆用户ID
	 * @return
	 * @throws MyException
	 *//*
	@PostMapping("/UserRole/insertRelation")
	@Permissions("userrole:add")
	public ApiResult insertRelation(Integer roleid, Integer id, Integer userid) throws MyException {
		List<TUserPgrouRelationp> one = userPgrouRelationpService.selectOne(id);
		if (one.size() != 0) {
			throw new MyException("0", "此用户已绑定角色");
		}
		TUserPgrouRelationp relation = new TUserPgrouRelationp();
		relation.setAddtime(ConverUtil.timeForString(new Date()));
		relation.setAdduserid(userid);
		relation.setGroupid(roleid);
		relation.setUserid(id);
		Integer in = userPgrouRelationpService.insertRelation(relation);
		if (in <= 0) {
			return ApiResult.resultInfo("0", "绑定失败", null);
		}
		return ApiResult.resultInfo("0", "绑定成功", null);
	}

	*//**
	 * 用户角色解除绑定
	 * 
	 * @param relation
	 * @return
	 * @throws MyException
	 *//*
	@PostMapping("/UserRole/delRelation")
	@Permissions("userrole:del")
	public ApiResult delRelation(TUserPgrouRelationp relation) throws MyException {
		ApiResult resultInfo = null;
		Integer update = userPgrouRelationpService.update(relation);
		if (update <= 0) {
			resultInfo = ApiResult.resultInfo("0", "解除绑定失败！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "解除绑定成功！", null);
		}
		return resultInfo;
	}*/

	/**
	 * 用户与角色绑定、解除绑定
	 * 
	 * @param roleid
	 *            角色ID
	 * @param id
	 *            被绑定用户ID
	 * @param userid
	 *            登陆用户ID
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/UserRole/userRoleRelation")
	@Permissions("UserRole:userRoleRelation:update")
	public ApiResult userRoleRelation(Integer roleid, Integer id, Integer userid) throws MyException {
		List<TUserPgrouRelationp> one = userPgrouRelationpService.selectOne(id);
		if (roleid == null) {
			if (one.size() == 0) {
				return ApiResult.resultInfo("1", "角色为空", null);
			}
			TUserPgrouRelationp relation = new TUserPgrouRelationp();
			relation.setUserid(id);
			Integer update = userPgrouRelationpService.update(relation);
			if (update <= 0) {
				return ApiResult.resultInfo("0", "解除绑定失败", null);
			}
			return ApiResult.resultInfo("1", "解除绑定成功", null);
		} else {
			if (one.size() == 0) {
				TUserPgrouRelationp relation = new TUserPgrouRelationp();
				relation.setAddtime(ConverUtil.timeForString(new Date()));
				relation.setAdduserid(userid);
				relation.setGroupid(roleid);
				relation.setUserid(id);
				Integer in = userPgrouRelationpService.insertRelation(relation);
				if (in <= 0) {
					return ApiResult.resultInfo("0", "绑定失败", null);
				}
			} else {
				// 修改
				Integer replaceRole = userPgrouRelationpService.replaceRole(roleid, id);
				if (replaceRole <= 0) {
					ApiResult.resultInfo("0", "绑定失败", null);
				}
			}
		}
		return ApiResult.resultInfo("1", "绑定成功", null);
	}
}
