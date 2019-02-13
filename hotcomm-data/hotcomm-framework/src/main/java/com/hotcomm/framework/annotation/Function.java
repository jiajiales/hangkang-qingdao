package com.hotcomm.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 
 * @author  wanpeng http://www.hotcomm.net/
 * @date 2018年3月16日 下午4:24:38
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Function {
	
	String key() default "";
	
}
