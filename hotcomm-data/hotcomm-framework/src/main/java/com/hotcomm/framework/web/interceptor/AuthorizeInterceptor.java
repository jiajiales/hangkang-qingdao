package com.hotcomm.framework.web.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hotcomm.framework.annotation.Function;
import com.hotcomm.framework.log.LogManager;
import com.hotcomm.framework.utils.PropertiesHelper;
import com.hotcomm.framework.utils.RedisHelper;
import com.hotcomm.framework.web.exception.HKException;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 
 * @author  wanpeng http://www.hotcomm.net/
 * @date 2018年3月16日 下午4:17:29
 */
@Slf4j
public class AuthorizeInterceptor implements HandlerInterceptor{
	
	LogManager logManager; 
	
	RedisHelper redisHelper;
	
	public AuthorizeInterceptor() {
	}
	
	public AuthorizeInterceptor(LogManager logManager) {
		this.logManager = logManager;
	}
	
	public AuthorizeInterceptor(LogManager logManager,RedisHelper redisHelper) {
		super();
		this.logManager = logManager;
		this.redisHelper = redisHelper;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String filterURL = request.getRequestURI();
		log.info(String.format("【权限拦截器】拦截地址：%s", filterURL));
		if(PropertiesHelper.devModel.equals("test")) 
			//开发模式通行
			return true;
		if(!(handler instanceof HandlerMethod)) 
			return true;
		final HandlerMethod handlerMethod = (HandlerMethod) handler;
		final Method method = handlerMethod.getMethod();
		final boolean exists = method.isAnnotationPresent(Function.class);
		if(!exists) 
			return true;
		String token = request.getParameter("token");
		if(token==null) 
			throw new HKException("TOKEN-001", "缺失令牌");
		String sessionId = redisHelper.get(token);
		if(sessionId==null) 
			throw new HKException("TOKEN-002", "令牌无效");
		//memberResource memberResource = session.getAttribute("users");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}
	
  
}
