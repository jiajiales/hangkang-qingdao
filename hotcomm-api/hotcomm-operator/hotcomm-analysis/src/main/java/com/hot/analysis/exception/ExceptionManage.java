package com.hot.analysis.exception;

import org.springframework.core.env.Environment;

public class ExceptionManage {
	
	Environment environment;
	
	public ExceptionManage() {}

	public ExceptionManage(Environment environment) {
		this.environment = environment;
	}
	
	public MyException creat(String state){
		return new MyException(state,environment.getProperty(state));	
	}

}
