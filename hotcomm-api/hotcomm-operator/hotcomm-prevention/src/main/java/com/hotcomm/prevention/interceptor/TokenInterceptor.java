package com.hotcomm.prevention.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hotcomm.prevention.config.PropertiesHelper;
import com.hotcomm.prevention.config.RedisHelper;
import com.hotcomm.prevention.controller.common.LoginController;
import com.hotcomm.prevention.exception.ExceptionManage;

public class TokenInterceptor implements HandlerInterceptor {

	// private Logger log = (Logger) LoggerFactory.getLogger(LogAspect.class);

	private RedisHelper redisHelper;
	private ExceptionManage manage;

	public TokenInterceptor(RedisHelper redisHelper, ExceptionManage manage) {
		this.redisHelper = redisHelper;
		this.manage = manage;
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		if (PropertiesHelper.devModel.equals("dev")) {
			System.out.println(PropertiesHelper.devModel);
			return true;
		}
		String key = null;
		//String userid = request.getParameter("userid");
		String token = request.getParameter("token");
		if (token == null || "".equals(token)) {
			throw manage.creat("TOKEN00002");
		}
		// 鑾峰彇璇锋眰澶达紝鍒ゆ柇鏄痑pp鎴杙c绔闂�
		String requestHeader = request.getHeader("user-agent");
		boolean bool = LoginController.isMobileDevice(requestHeader);
		if (bool) {
			key = token+"_app";
		}else {
			key = token+"_pc";
		}	
		String tokenjson=redisHelper.get(key);
		if (tokenjson == null) {
			throw manage.creat("TOKEN00001");
		}
		if (!token.equals(tokenjson)) {
			throw manage.creat("TOKEN00003");
		}
		if (bool) {
			redisHelper.set(key, token);
		}else {
			redisHelper.set(key, token, 30 * 60);
		}
		
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}
}
