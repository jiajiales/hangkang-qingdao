package com.hot.manage.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hot.manage.config.PropertiesHelper;
import com.hot.manage.config.RedisHelper;
import com.hot.manage.exception.ExceptionManage;

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

		/*if (PropertiesHelper.devModel.equals("test")) {
			// System.out.println(PropertiesHelper.devModel);
			return true;
		}*/
		String url = request.getRequestURL().toString();
		if (url.contains("oneFileUpload") || url.contains("moreFileUpload") || url.contains("selectLastOne")
				|| url.contains("queryAlarmCountFor")) {
			return true;
		}
		String rToken = "token_" + request.getParameter("userid");
		String token = request.getParameter("token");
		// log.info("token:" + token);
		if (token == null || "".equals(token)) {
			throw manage.creat("TOKEN00002");
		}
		String tokenjson = redisHelper.get(rToken);
		if (tokenjson == null) {
			throw manage.creat("TOKEN00001");
		}
		if (!token.equals(tokenjson)) {
			throw manage.creat("TOKEN00003");
		}
		redisHelper.set(rToken, token, 30 * 60);
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}
}
