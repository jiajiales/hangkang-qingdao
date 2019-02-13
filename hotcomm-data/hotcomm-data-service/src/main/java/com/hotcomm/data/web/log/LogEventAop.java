package com.hotcomm.data.web.log;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotcomm.data.bean.entity.sys.Log;
import com.hotcomm.data.bean.vo.sys.MemberResource;
import com.hotcomm.data.service.LogService;
import com.hotcomm.framework.annotation.LogEvent;
import com.hotcomm.framework.utils.RedisHelper;
import com.hotcomm.framework.web.result.ApiResult;

@Aspect
@Component
public class LogEventAop {

	@Autowired
	private Environment environment;

	@Autowired
	private LogService log;

	@Resource
	private RedisHelper redisHelper;

	public static final String ASPECT_EXCUTIONS = "execution(public * com.hotcomm.data.web.controller.*.*.*(..)) "
			+ "||  execution(public * com.hotcomm.data.web.controller.*.*(..))";

	@Pointcut(value = ASPECT_EXCUTIONS)
	public void logPointCut() {
		
	}

	@AfterReturning(value = "logPointCut()", argNames = "retVal", returning = "retVal")
	public void logInfo(JoinPoint joinPoint, Object retVal) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		String methodName = method.getName();
		LogEvent event = method.getAnnotation(LogEvent.class);
		if (event == null)
			return;
		String code = event.code();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String token = request.getParameter("token");
		if (!methodName.equals("memberLogin")) {
			if (token == null)
				return;
		}
		boolean executeSuccess = true;
		if (retVal instanceof ApiResult) {
			ApiResult result = (ApiResult) retVal;
			String returnCode = result.getCode();
			executeSuccess = returnCode.equals("0") ? true : false;
		}
		String codeVal = environment.getProperty(code);
		Log log = new Log();
		log.setRecordEvent(codeVal + "---->" + (executeSuccess ? "成功" : "失败"));
		Object[] params = joinPoint.getArgs();
		ObjectMapper mapper = new ObjectMapper();
		if (methodName.equals("memberLogin")) {
			if (params.length <= 0)
				return;
			Object username = params[0];
			log.setRecordUser(username.toString());
		} else {
			try {
				String userJson = redisHelper.get(token);
				MemberResource memberResource = mapper.readValue(userJson, MemberResource.class);
				String username = memberResource.getMember().getMemberName();
				log.setRecordUser(username);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String ip = getIP(request);
		log.setRecordIp(ip);
		try {
			boolean exists = false;
			for(Object param:params) {
				if(param instanceof MultipartFile) {
					exists = true;
					break;
				}
			}
			String paramsJson = exists?"":mapper.writeValueAsString(params);
			log.setRecordparams(paramsJson);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		this.log.saveLog(log);
	}

	// 获取请求IP
	public String getIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
