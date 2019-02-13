package com.hotcomm.data.web.controller.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotcomm.data.bean.enums.MemberEnum.MemberType;
import com.hotcomm.data.bean.params.sys.CustomerMemberParams;
import com.hotcomm.data.bean.params.sys.MemberPageParams;
import com.hotcomm.data.bean.params.sys.MemberParams;
import com.hotcomm.data.bean.params.sys.MemberPwdParams;
import com.hotcomm.data.bean.vo.sys.MemberVO;
import com.hotcomm.data.comm.PageInfo;
import com.hotcomm.data.service.UserService;
import com.hotcomm.data.web.controller.comm.BaseController;
import com.hotcomm.data.web.controller.comm.CrudController;
import com.hotcomm.data.web.controller.comm.PageController;
import com.hotcomm.framework.annotation.Function;
import com.hotcomm.framework.annotation.LogEvent;
import com.hotcomm.framework.annotation.Param;
import com.hotcomm.framework.annotation.ParamsValidate;
import com.hotcomm.framework.web.exception.HKException;
import com.hotcomm.framework.web.result.ApiResult;

@RestController
public class UserController extends BaseController
		implements CrudController<MemberParams, Integer, MemberVO>, PageController<MemberPageParams, MemberVO> {

	@Autowired
	private UserService userService;

	@Function(key = "user-page")
	@ParamsValidate(validateParams = { @Param(key = "page", code = "MEMBER_F10"),
									   @Param(key = "rows", code = "MEMBER_F11") })
	@RequestMapping("/user/page")
	@LogEvent(code = "MEMBER_PAG")
	@Override
	public PageInfo<MemberVO> page(MemberPageParams params) throws HKException {
		params.setUserType(MemberType.SYSTEM.getValue());
		PageInfo<MemberVO> page = userService.queryPage(params);
		return page;
	}

//	@Function(key = "user-get")
	@ParamsValidate(validateParams = { @Param(key = "id", code = "MEMBER_F01") })
	@RequestMapping("/user/get")
	@LogEvent(code = "MEMBER_GET")
	@Override
	public ApiResult get(Integer id) throws HKException {
		return ApiResult.success(userService.getBean(id));
	}

//	@Function(key = "user-update")
	@ParamsValidate(validateParams = { @Param(key = "id", code = "MEMBER_F01"),
									   @Param(key = "memberName", code = "MEMBER_F02"), 
									   @Param(key = "realName", code = "MEMBER_F03") })
	@RequestMapping("/user/update")
	@LogEvent(code = "MEMBER_UPD")
	@Override
	public ApiResult update(MemberParams params) throws HKException {
		userService.updateBean(params);
		return ApiResult.success("");
	}

	@Function(key = "user-del")
	@ParamsValidate(validateParams = { @Param(key = "id", code = "MEMBER_F01") })
	@RequestMapping("/user/del")
	@LogEvent(code = "MEMBER_DEL")
	@Override
	public ApiResult del(Integer id) throws HKException {
		userService.delBean(id);
		return ApiResult.success("");
	}

	@Function(key = "user-add")
	@ParamsValidate(validateParams = { @Param(key = "memberName", code = "MEMBER_F02"), 
									   @Param(key = "password", code = "MEMBER_F04"), 
									   @Param(key = "status", code = "MEMBER_F08"), 
									   @Param(key = "realName", code = "MEMBER_F03") })
	@RequestMapping("/user/add")
	@LogEvent(code = "MEMBER_ADD")
	@Override
	public ApiResult add(MemberParams params) throws HKException {
		MemberVO member = getLoginMember();
		params.setCreateUser(member.getMemberName());
		params.setUserType(MemberType.SYSTEM.getValue());
		userService.addBean(params);
		return ApiResult.success("");
	}

	@Function(key = "member-password-reset")
	@ParamsValidate(validateParams = { @Param(key = "id", code = "MEMBER_F01") })
	@RequestMapping("/member/password/reset")
	@LogEvent(code = "MEMBER_PWD_RESET")
	public ApiResult pwdRest(MemberParams params) throws HKException {
		userService.resetPwd(params.getId());
		return ApiResult.success("");
	}

//	@Function(key = "member-password-set")
	@ParamsValidate(validateParams = { @Param(key = "id", code = "MEMBER_F01"), 
			                           @Param(key = "oldPassword", code = "MEMBER_F05"), 
			                           @Param(key = "newPassword", code = "MEMBER_F06"), 
			                           @Param(key = "newPassword2", code = "MEMBER_F07") })
	@RequestMapping("/member/password/set")
	@LogEvent(code = "MEMBER_PWD_SET")
	public ApiResult setPwd(MemberPwdParams params) throws HKException {
		userService.setPwd(params);
		return ApiResult.success("");
	}

	@Function(key = "member-add-customerMember")
	@ParamsValidate(validateParams = { @Param(key = "memberId", code = "MEMBER_F01") })
	@RequestMapping("/member/add/customerMember")
	@LogEvent(code = "MEMBER_ADD_CUSTOMERMEMBER")
	public ApiResult addCustomerMember(CustomerMemberParams params) throws HKException {
		userService.addCustomerMember(params);
		return ApiResult.success("");
	}

}
