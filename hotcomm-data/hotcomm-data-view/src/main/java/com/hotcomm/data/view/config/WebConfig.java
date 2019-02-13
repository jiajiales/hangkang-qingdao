package com.hotcomm.data.view.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.hotcomm.data.view.controller.LoginController;
import com.hotcomm.data.view.interceptor.AuthorizeInterceptor;
import com.hotcomm.data.view.socket.WebSocketTask;
import com.hotcomm.framework.utils.RedisHelper;
import com.hotcomm.framework.utils.SpringUtil;

@Component
public class WebConfig extends WebMvcConfigurationSupport {

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		RedisHelper redisHelper = SpringUtil.getBean(RedisHelper.class);
		registry.addInterceptor(new AuthorizeInterceptor(redisHelper)).excludePathPatterns("/static/**");
		registry.addInterceptor(new WebSocketTask(redisHelper)).excludePathPatterns("/static/**");
		registry.addInterceptor(new LoginController(redisHelper)).excludePathPatterns("/static/**");
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	}

}
