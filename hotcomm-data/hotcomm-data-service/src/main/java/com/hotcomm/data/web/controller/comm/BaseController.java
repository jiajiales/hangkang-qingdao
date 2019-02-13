package com.hotcomm.data.web.controller.comm;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotcomm.data.bean.vo.sys.MemberResource;
import com.hotcomm.data.bean.vo.sys.MemberVO;
import com.hotcomm.framework.utils.PropertiesHelper;
import com.hotcomm.framework.utils.RedisHelper;
import com.hotcomm.framework.web.exception.HKException;

@Component
public class BaseController {

	@Resource
	HttpServletRequest request;

	@Resource
	private RedisHelper redisHelper;

	protected MemberVO getLoginMember() {
		MemberVO result = null;
		try {
			String token = request.getParameter("token");
			if (PropertiesHelper.devModel.equals("test")&&token==null) {
				result = new MemberVO();
				result.setMemberName("admin");
				return result;
			}
			String userJson = redisHelper.get(token);
			ObjectMapper mapper = new ObjectMapper();
			MemberResource memberResource = mapper.readValue(userJson, MemberResource.class);
			result = memberResource.getMember();
		} catch (Exception e) {
			throw new HKException("USER_TOKEN_001", "获取当前登入用户信息失败");
		}
		return result;
	}

}
