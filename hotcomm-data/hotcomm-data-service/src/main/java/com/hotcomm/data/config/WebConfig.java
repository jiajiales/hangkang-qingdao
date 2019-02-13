package com.hotcomm.data.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.hotcomm.data.comm.LogManangerHelper;
import com.hotcomm.data.web.interceptor.AuthorizeInterceptor;
import com.hotcomm.framework.log.LogManager;
import com.hotcomm.framework.utils.RedisHelper;
import com.hotcomm.framework.web.exception.ExceptionManager;
import com.hotcomm.framework.web.interceptor.ParamsInterceptor;
import com.hotcomm.framework.web.param.ParamCodeManager;

@Configuration
@PropertySource(value = { "code/exception.properties", "code/params.properties", "code/log-code.properties" }, encoding = "UTF-8")
public class WebConfig extends WebMvcConfigurationSupport {

	@Autowired
	private Environment environment;

	@Resource
	private LogManager logManager;

	@Resource
	private RedisHelper redisHelper;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthorizeInterceptor(createLogManager(), redisHelper));
		registry.addInterceptor(new ParamsInterceptor(createLogManager()));
	}

	@Bean
	public LogManager createLogManager() {
		return new LogManangerHelper();
	}

	@Bean(name = "exceptionManager")
	public ExceptionManager createExcMananger() {
		return new ExceptionManager(environment);
	}

	@Bean(name = "paramCodeManager")
	public ParamCodeManager createCodeManager() {
		return new ParamCodeManager(environment);
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	}

}
