package com.hotcomm.data.web.interceptor;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotcomm.data.bean.entity.sys.Resource;
import com.hotcomm.data.bean.vo.sys.MemberResource;
import com.hotcomm.data.comm.Constant;
import com.hotcomm.data.service.MemberTokenService;
import com.hotcomm.framework.annotation.Function;
import com.hotcomm.framework.log.LogManager;
import com.hotcomm.framework.utils.PropertiesHelper;
import com.hotcomm.framework.utils.RedisHelper;
import com.hotcomm.framework.utils.SpringUtil;
import com.hotcomm.framework.web.exception.HKException;

/**
 * @Description:
 * @author wanpeng http://www.hotcomm.net/
 * @date 2018年3月16日 下午4:17:29
 */
public class AuthorizeInterceptor implements HandlerInterceptor {

	LogManager logManager;

	RedisHelper redisHelper;

	public AuthorizeInterceptor() {
		
	}

	public AuthorizeInterceptor(LogManager logManager) {
		this.logManager = logManager;
	}

	public AuthorizeInterceptor(LogManager logManager, RedisHelper redisHelper) {
		super();
		this.logManager = logManager;
		this.redisHelper = redisHelper;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Logger log = logManager.getServiceLog();
		String filterURL = request.getRequestURI();
		log.info(String.format("【权限拦截器】拦截地址：%s", filterURL));
		if (PropertiesHelper.devModel.equals("test"))
			// 开发模式通行
			return true;
		if (!(handler instanceof HandlerMethod))
			return true;
		final HandlerMethod handlerMethod = (HandlerMethod) handler;
		final Method method = handlerMethod.getMethod();
		final boolean exists = method.isAnnotationPresent(Function.class);
		if (!exists)
			return true;
		String token = request.getParameter("token");
		if (token == null)
			throw new HKException("TOKEN-001", "缺失令牌");
		String userJson = redisHelper.get(token);
		if (userJson == null)
			throw new HKException("TOKEN-002", "令牌无效");
		ObjectMapper mapper = new ObjectMapper();

		MemberResource memberResource = mapper.readValue(userJson, MemberResource.class);
		if (memberResource == null) {
			throw new HKException("TOKEN-002", "令牌无效");
		}
		List<Resource> resources = memberResource.getResources();
		boolean existsIndex = false;
		for (Resource resource : resources) {
			if (resource.getPath().equals(filterURL)) {
				existsIndex = true;
			}
		}
		if (!existsIndex) {
			throw new HKException("TOKEN-0003", "接口未授权");
		}
		long tokenExpire = redisHelper.getKeyExpire(token);
		if (tokenExpire < Constant.MIN_EXPIRE) {
			redisHelper.set(token, userJson, Constant.DEFAULT_EXPIRE);
			MemberTokenService tokenSerice = SpringUtil.getBean(MemberTokenService.class);
			tokenSerice.updateTokenExpire(token);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
