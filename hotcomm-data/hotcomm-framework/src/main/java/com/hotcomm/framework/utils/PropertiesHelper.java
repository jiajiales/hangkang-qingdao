package com.hotcomm.framework.utils;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration  
public class PropertiesHelper  implements EnvironmentAware {
	
	public static String devModel;
	
	@Override
	public void setEnvironment(Environment environment) {
		String devModel = environment.getProperty("dev_model", String.class);
		PropertiesHelper.devModel=devModel;
	}
	
}
