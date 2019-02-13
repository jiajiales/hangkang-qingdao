package com.hotcomm.prevention.config;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hotcomm.prevention.bean.mysql.common.entity.TOperationLog;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.common.OperationLogService;
import com.hotcomm.prevention.utils.ConverUtil;
import com.hotcomm.prevention.utils.TextUtils;

@Aspect
@Component
public class LogAspect {

	public static Logger operation_log = LoggerFactory.getLogger("operation_log");
	
	@Autowired
	private OperationLogService operationLogService;

	@Pointcut("execution(public * com.hotcomm.prevention.controller.*.*.*(..))")
	public void webLog() {
	};

	@Before("webLog()")
	public void before(JoinPoint joinPoint) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		// 拿到当前登陆用户的名称或账号
		String url = request.getRequestURL().toString();
		if (url.contains("login/getValidateCode")||url.contains("login/getUserInfo")) {
			return;
		}
		String id = request.getParameter("userid");
		if (TextUtils.isEmpty(id)) {
			throw new MyException("OPERATION00002","用户ID不能为空");
		}
		int userid = Integer.parseInt(id);
		TOperationLog logger = new TOperationLog();
		logger.setUrl(request.getRequestURL().toString());
		logger.setHttpmethod(request.getMethod());
		logger.setIp(request.getRemoteAddr());
		logger.setClassmethod(
				joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
		logger.setUserid(userid);
		//logger.setArgs(Arrays.toString(joinPoint.getArgs()));
		logger.setAddtime(ConverUtil.dateForString(new Date()));
		operationLogService.insert(logger);
		operation_log.info("Operater:" + userid + "," + "HTTP_METHOD : " + request.getMethod() + "," + "URL:"
				+ request.getRequestURL().toString() + "," + "IP:" + request.getRemoteAddr() + "," + "CLASS_METHOD : "
				+ joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + ","
				+ "ARGS : " + Arrays.toString(joinPoint.getArgs()));		
	}

	@AfterReturning("webLog()")
	public void afterRturn(JoinPoint joinPoint) {
	}

}
