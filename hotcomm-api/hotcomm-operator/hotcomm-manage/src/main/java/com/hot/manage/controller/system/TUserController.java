package com.hot.manage.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hot.manage.annotation.Permissions;
import com.hot.manage.controller.CommonController;
import com.hot.manage.controller.PageController;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.PageParam;
import com.hot.manage.entity.system.TUser;
import com.hot.manage.entity.system.TUserVo;
import com.hot.manage.entity.system.UserPageParam;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.system.TUserService;
import com.hot.manage.utils.ApiResult;
import com.hot.manage.utils.MD5Util;

@RestController
public class TUserController implements CommonController<TUser, TUserVo, Integer>, PageController<PageParam, TUserVo> {

	@Autowired
	private TUserService userService;

	@PostMapping("/user/page")
	@Permissions("user:page:query")
	public ApiResult page(UserPageParam param) throws MyException {
		PageInfo<TUserVo> info = userService.selectPage(param);
		return ApiResult.resultInfo("1", "成功", info);
	}

	@Override
	public ApiResult insert(TUser user, Integer id) throws MyException {
		return null;
	}
	
	/**
	 * 用户添加
	 * @param user
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/user/insertUser")
	@Permissions("user:insertUser:add")
	public ApiResult insertUser(TUser user) throws Exception {
		ApiResult resultInfo = null;
		Integer add = userService.insertUser(user);
		if (add <= 0) {
			resultInfo = ApiResult.resultInfo("0", "添加用户失败！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "用户添加成功！",null);
		}
		return resultInfo;
	}
	
	/**
	 * 修改用户
	 */
	@PostMapping("/user/update")
	@Permissions("user:update")
	@Override
	public ApiResult update(TUser user) throws MyException {
		ApiResult resultInfo = null;
		Integer update = userService.updateObject(user);
		if (update <= 0) {
			resultInfo = ApiResult.resultInfo("0", "用户修改失败！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "用户修改成功！",null);
		}
		return resultInfo;
	}
	/**
	 * 删除用户
	 */
	@PostMapping("/user/delete")
	@Permissions("user:del")
	@Override
	public ApiResult delete(@RequestParam("id") Integer id) throws MyException {
		ApiResult resultInfo = null;
		Integer del = userService.delObject(id);
		if (del <= 0) {
			resultInfo = ApiResult.resultInfo("0", "删除失败！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "删除成功！",null);
		}
		return resultInfo;
	}

	@Override
	public ApiResult select(Integer r, Integer b) throws MyException {
		return null;
	}
	
	/**
	 * 查询单个的用户
	 * @param id
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/user/selectUserById")
	@Permissions("user:selectUserById:query")
	public ApiResult selectUserById(@RequestParam("userid") Integer userid) throws MyException {
		TUserVo selectObject = userService.selectObject(userid);
		return ApiResult.resultInfo("1", "成功", selectObject);
	}
	
	/**
	 * 查询当前用户下的子用户
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/user/selectChildren")
	@Permissions("user:selectChildren:query")
	public ApiResult selectChildren(Integer userid) throws MyException {
		List<TUserVo> selectChildren = userService.selectChildren(userid);
		return ApiResult.resultInfo("1", "成功", selectChildren);
	}

	/**
	 * 修改密码
	 * @param userid
	 * @param password
	 * @param newPassword
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/user/updatepassword")
	@Permissions("user:updatepassword:update")
	public ApiResult updatePassword(Integer userid, String password, String newPassword) throws MyException {
		ApiResult resultInfo = null;
		if (password == null || newPassword == null) {
			return ApiResult.resultInfo("0", "原始密码或者新密码不能为空！",null);
		}
		TUser dataUser = userService.selectUserById(userid);
		boolean bool = MD5Util.isPasswordInvalid(dataUser.getPassword(), password, MD5Util.SALT);
		if (bool) {
			String encodePassword = MD5Util.encodePassword(newPassword, MD5Util.SALT);
			TUser user = new TUser();
			user.setId(userid);
			user.setPassword(encodePassword);
			Integer update = userService.updateObject(user);
			if (update <= 0) {
				resultInfo = ApiResult.resultInfo("0", "修改失败！",null);
			} else {
				resultInfo = ApiResult.resultInfo("1", "修改成功！",null);
			}
		} else {
			resultInfo = ApiResult.resultInfo("0", "原始密码不正确！",null);
		}
		return resultInfo;
	}

	/**
	 * 重置密码
	 * @param id 需要修改的账户ID
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/user/resetpassword")
	@Permissions("user:resetpassword:update")
	public ApiResult resetPassword(Integer id) throws MyException {
		ApiResult resultInfo = null;
		TUser params = new TUser();
		params.setId(id);
		String encodePassword = MD5Util.encodePassword("000000", MD5Util.SALT);
		params.setPassword(encodePassword);
		Integer update = userService.updateObject(params);
		if (update <= 0) {
			resultInfo = ApiResult.resultInfo("0", "密码重置失败！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "密码重置成功！",null);
		}
		return resultInfo;
	}

	@Override
	public ApiResult page(PageParam p) throws MyException {
		return null;
	}

}
