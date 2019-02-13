package com.hotcomm.prevention.datasource;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

// 保证该AOP在@Transactional之前执行
@Order(-1)
@Aspect
@Component
public class DynamicDataSourceAspect {

	@Pointcut("execution(public * com.hotcomm.prevention.db.sqlserver.*.*(..))")
	public void pointCut() {
	}

	
	@Before("pointCut()")
	public void changeDataSource(JoinPoint point) throws Throwable {
		//定义的接口方法
        Method abstractMethod = ((MethodSignature) point.getSignature()).getMethod();
        if (abstractMethod.isAnnotationPresent(DBSource.class)) {
            String sourceName = abstractMethod.getAnnotation(DBSource.class).name();
            DynamicDataSource.setDataSource(sourceName);
            System.out.println(("动态切换数据源：--- " + sourceName));
        }

	}

	@After("pointCut()")
	public void after(JoinPoint point) {
		System.out.println("after");
		DynamicDataSource.clearDataSource();
	}
}
