package com.hotcomm.framework.web.exception;

import org.springframework.core.env.Environment;

/**
 * @Description: 
 * @author  wanpeng http://www.hotcomm.net/
 * @date 2018年3月16日 下午2:18:35
 */
public class ExceptionManager {

	Environment environment;

	public ExceptionManager() {}
	
	public ExceptionManager(Environment environment) {
		this.environment = environment;
	}
	
	public HKException create(String code) {
		return new HKException(code, environment.getProperty(code));
	}

}
