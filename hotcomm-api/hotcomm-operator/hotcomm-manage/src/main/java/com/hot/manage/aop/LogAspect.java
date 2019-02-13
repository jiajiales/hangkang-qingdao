package com.hot.manage.aop;

import java.util.Arrays;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.hot.manage.entity.system.TOperationLog;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.system.TOperationLogService;
import com.hot.manage.utils.ConverUtil;
import ch.qos.logback.classic.Logger;

@Aspect
@Component
public class LogAspect {

	private Logger log = (Logger) LoggerFactory.getLogger(LogAspect.class);

	@Autowired
	private TOperationLogService operationLogService;

	@Pointcut("execution(public * com.hot.manage.controller.*.*.*(..))")
	public void webLog() {
	};

	@Before("webLog()")
	public void before(JoinPoint joinPoint) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		// 拿到当前登陆用户的名称或账号
		String url = request.getRequestURL().toString();
		if (url.contains("kaptcha/defaultKaptcha")||url.contains("login/getUserInfo")) {
			return;
		}
		String id = request.getParameter("userid");
		if (id==null) {
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
		logger.setArgs(Arrays.toString(joinPoint.getArgs()));
		logger.setAddtime(ConverUtil.timeForString(new Date()));
		operationLogService.insertObject(logger);
		log.info("Operater:" + userid + "," + "HTTP_METHOD : " + request.getMethod() + "," + "URL:"
				+ request.getRequestURL().toString() + "," + "IP:" + request.getRemoteAddr() + "," + "CLASS_METHOD : "
				+ joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + ","
				+ "ARGS : " + Arrays.toString(joinPoint.getArgs()));		
	}

	@AfterReturning("webLog()")
	public void afterRturn(JoinPoint joinPoint) {
	}

}
