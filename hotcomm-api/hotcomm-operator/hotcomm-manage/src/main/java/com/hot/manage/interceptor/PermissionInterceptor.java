package com.hot.manage.interceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hot.manage.annotation.Permissions;
import com.hot.manage.config.PropertiesHelper;
import com.hot.manage.config.RedisHelper;
import com.hot.manage.controller.common.LoginController;
import com.hot.manage.exception.ExceptionManage;

public class PermissionInterceptor implements HandlerInterceptor {

	private RedisHelper redisHelper;
	private ExceptionManage manage;

	public PermissionInterceptor(RedisHelper redisHelper, ExceptionManage manage) {
		super();
		this.redisHelper = redisHelper;
		this.manage = manage;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		if (PropertiesHelper.devModel.equals("test"))
			return true;
		//获取参数拼接注解参数
		String userid = request.getParameter("userid");
		String moduleid = request.getParameter("moduleid");	
		HandlerMethod handlerMethod = (HandlerMethod) obj;
		Method method = handlerMethod.getMethod();
		boolean exist = method.isAnnotationPresent(Permissions.class);
		if (!exist)
			return true;
		Permissions permissions = method.getAnnotation(Permissions.class);
		String value = permissions.value();
		value=value+"-"+moduleid;
		boolean bool = LoginController.isMobileDevice(request.getHeader("user-agent"));
		String auth="";
		if (bool) {
			auth = "mp_" + userid;
		}else{
			auth = "power_" + userid;
		}
		String str = redisHelper.get(auth);	
		if (str==null||"".equals(str)) {
			throw  manage.creat("PERMISSION00001");
		}
		String[] ss = str.split(",");
		List<String> list = new ArrayList<>();
		if (ss.length != 0) {
			for (String s : ss) {
				list.add(s);
			}
		}
		if (list.size() == 0)
			throw manage.creat("PERMISSION00001");
		if (!list.contains(value))
			throw manage.creat("PERMISSION00002");
		return true;
	}

}
