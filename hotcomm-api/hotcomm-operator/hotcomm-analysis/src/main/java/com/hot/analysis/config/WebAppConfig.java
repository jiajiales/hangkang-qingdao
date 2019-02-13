package com.hot.analysis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

	@Value("${image.location.path}")
	private String resourceDir;
	
	// 静态资源路径从定向
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/image/**").addResourceLocations("file:" + resourceDir);
		super.addResourceHandlers(registry);
	}

}
