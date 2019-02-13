package com.hotcomm.data.web.controller.sys;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotcomm.data.bean.params.sys.MemberParams;
import com.hotcomm.data.bean.vo.sys.MemberResource;
import com.hotcomm.data.bean.vo.sys.MemberVO;
import com.hotcomm.data.comm.Constant;
import com.hotcomm.data.service.MemberTokenService;
import com.hotcomm.data.service.ResourceService;
import com.hotcomm.data.service.UserService;
import com.hotcomm.data.utils.UUIDUtils;
import com.hotcomm.framework.annotation.LogEvent;
import com.hotcomm.framework.annotation.Param;
import com.hotcomm.framework.annotation.ParamsValidate;
import com.hotcomm.framework.utils.CodeUtils;
import com.hotcomm.framework.utils.RedisHelper;
import com.hotcomm.framework.web.result.ApiResult;

@RestController
public class LoginController {

	@Resource
	private RedisHelper redisHelper;
	
	@Resource
	private RedisTemplate<Object, Object> redisTemplate;
	
	@Resource
	private UserService userService;

	@Resource
	private ResourceService resourceService;

	@Resource
	private MemberTokenService memberTokenService;

	// 授权-登入
	@ParamsValidate(validateParams = { @Param(key = "userName", code = "LOG_F01"), @Param(key = "password", code = "LOG_F02") })
	@PostMapping("/member/login")
	@LogEvent(code = "LOGING")
	public ApiResult memberLogin(String userName, String password, String sessionId) throws Exception {
		String token = UUIDUtils.get32BitUUID();

		MemberParams params = new MemberParams(CodeUtils.md5EncodeData(password), userName);

		MemberVO memberVO = userService.checkMember(params);
		Integer memberId = memberVO.getId();
		memberTokenService.saveMemberToken(memberId, token, sessionId);
		
		List<com.hotcomm.data.bean.entity.sys.Resource> resources = resourceService.queryListByMemberId(memberId);
		Map<Object,Map<String, Boolean>> authButtons = resourceService.getResourcButtons(memberId);
		
		MemberResource memberResource = new MemberResource();
		memberResource.setResources(resources);
		memberResource.setMember(memberVO);
		memberResource.setAuthButtons(authButtons);
		
		ObjectMapper mapper = new ObjectMapper();
		String loginRsJson = mapper.writeValueAsString(memberResource);
		System.out.println(loginRsJson);
		redisHelper.set(token,loginRsJson, Constant.DEFAULT_EXPIRE);
		return ApiResult.success(token);
	}

	@PostMapping("/member/logout")
	@LogEvent(code = "LOGOUT")
	public ApiResult memberLogout(@PathVariable("type") String type, String token) throws Exception {
		redisHelper.del(token);
		memberTokenService.delToken(token);
		return ApiResult.success();
	}

}
