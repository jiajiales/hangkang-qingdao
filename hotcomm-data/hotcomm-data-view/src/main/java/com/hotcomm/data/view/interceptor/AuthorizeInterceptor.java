package com.hotcomm.data.view.interceptor;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hotcomm.data.view.socket.WebSocketHandler;
import com.hotcomm.framework.utils.RedisHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthorizeInterceptor implements HandlerInterceptor {
	
	RedisHelper redisHelper;
	
	final List<String> afterUrls = Arrays.asList(new String[] {"/","/login"});
	
	final String validateLoginUrl = "/validateMember";
	
	String [] validateBeforeLoginUrls = {"/","/login"};
	
	public AuthorizeInterceptor() {
	}

	public AuthorizeInterceptor(RedisHelper redisHelper) {
		this.redisHelper = redisHelper;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String filterURL = request.getRequestURI();
		log.debug("拦截页面路径:{}",filterURL);
		if(request.getParameter("dialog") != null && request.getParameter("dialog").equals("true")) {
			return true;
		}
		if(filterURL.equals(validateLoginUrl)) 
			return true;
		if(afterUrls.contains(filterURL)) {
			Object loginIndexObj = request.getSession().getAttribute("loginIndex");
			if(loginIndexObj==null||!loginIndexObj.toString().equals("1")) {
				return true;
			}
			 response.sendRedirect(request.getContextPath() + "index");
		}
		//处理正常拦截路径
		Object isLogin = request.getSession().getAttribute("loginIndex");
		if(filterURL.endsWith("logout")) {
			return true;
		}
		WebSocketHandler.updateSessionKeeps(request.getSession().getId());
		if(isLogin==null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}

}
