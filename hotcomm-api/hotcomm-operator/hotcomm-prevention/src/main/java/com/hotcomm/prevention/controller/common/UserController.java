package com.hotcomm.prevention.controller.common;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.prevention.bean.mysql.common.entity.TUser;
import com.hotcomm.prevention.bean.mysql.common.params.UserPageParam;
import com.hotcomm.prevention.bean.mysql.common.vo.TUserVo;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.common.UserService;
import com.hotcomm.prevention.utils.ApiResult;
import com.hotcomm.prevention.utils.ConverUtil;
import com.hotcomm.prevention.utils.MD5Util;

@RestController
public class UserController {
	@Autowired
	UserService userService;

	/**
	 * 系统设置->用户管理->添加用户
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/user/insertUser")
	public ApiResult insertUser(TUser user) throws MyException {
		userService.insert(user);
		return ApiResult.resultInfo("1", "请求成功", null);
	}

	/**
	 * 系统设置->用户管理->修改用户
	 * 
	 * @param user
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/user/update")
	public ApiResult update(TUser user) throws MyException {
		userService.updateById(user);
		return ApiResult.resultInfo("1", "请求成功", null);
	}

	/**
	 * 系统设置->用户管理->删除用户
	 * 
	 * @param id
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/user/delete")
	public ApiResult delete(Integer id) throws MyException {
		userService.delete(id);
		return ApiResult.resultInfo("1", "请求成功", null);
	}

	/**
	 * 系统设置->用户管理->用户列表分页
	 * 
	 * @param param
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/user/page")
	public ApiResult page(UserPageParam param) throws MyException {
		PageInfo<TUserVo> info = userService.selectPageUsers(param);
		return ApiResult.resultInfo("1", "成功", info);
	}

	/**
	 * 查询当前用户以及其子用户
	 * 
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/user/selectChildren")
	public ApiResult selectChildren(Integer userid) throws MyException {
		List<TUserVo> selectChildren = userService.selectChildren(userid);
		return ApiResult.resultInfo("1", "成功", selectChildren);
	}

	/**
	 * 系统设置->用户管理->修改密码
	 * 
	 * @param userid
	 * @param password
	 * @param newPassword
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/user/updatepassword")
	public ApiResult updatePassword(Integer id, String newPassword) throws MyException {
		if (newPassword == null) {
			return ApiResult.resultInfo("0", "新密码不能为空！", null);
		}
		TUser dataUser = userService.selectUserById(id);
		//boolean bool = MD5Util.isPasswordInvalid(dataUser.getPassword(), password, MD5Util.SALT);
		//if (bool) {
			String encodePassword = MD5Util.encodePassword(newPassword, MD5Util.SALT);
			TUser user = new TUser();
			user.setId(id);
			user.setPassword(encodePassword);
			userService.updateById(user);
			return ApiResult.resultInfo("1", "密码修改成功", null);
		//} else {
			//return ApiResult.resultInfo("0", "原始密码不正确！", null);
		//}
	}

	/**
	 * 系统设置->用户管理->重置密码
	 * 
	 * @param id
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/user/resetpassword")
	public ApiResult resetPassword(Integer id) throws MyException {
		TUser params = new TUser();
		params.setId(id);
		String encodePassword = MD5Util.encodePassword("000000", MD5Util.SALT);
		params.setPassword(encodePassword);
		userService.updateById(params);
		return ApiResult.resultInfo("1", "请求成功", null);
	}
	
	/**
	 * 查询单个的用户
	 * @param id
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/user/selectUserById")
	public ApiResult selectUserById(Integer id) throws MyException {
		TUserVo vo = new TUserVo();
		TUser user = userService.selectUserById(id);
		BeanUtils.copyProperties(user, vo);
		return ApiResult.resultInfo("1", "成功", vo);
	}
}
