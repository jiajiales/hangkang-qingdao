package com.hot.manage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.hot.manage.exception.ExceptionManage;
import com.hot.manage.interceptor.PermissionInterceptor;
import com.hot.manage.interceptor.TokenInterceptor;

@Configuration
@PropertySource(value = { "exception.properties" }, encoding = "UTF-8")
public class WebAppConfig extends WebMvcConfigurerAdapter {

	@Value("${image.location.path}")
	private String resourceDir;
	@Value("${apk.location.path}")
	private String apkResourceDir;
	@Autowired
	private RedisHelper redisHelper;
	@Autowired
	private Environment environment;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 权限注解拦截
		registry.addInterceptor(new TokenInterceptor(redisHelper, createManage())).addPathPatterns("/**")
				.excludePathPatterns("/kaptcha/defaultKaptcha", "/login/getUserInfo");
		registry.addInterceptor(new PermissionInterceptor(redisHelper, createManage())).addPathPatterns("/**")
				.excludePathPatterns("/kaptcha/defaultKaptcha", "/login/getUserInfo");
	}

	// 静态资源路径从定向
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/image/**").addResourceLocations("file:" + resourceDir);
		registry.addResourceHandler("/apk/**").addResourceLocations("file:" + apkResourceDir);
		super.addResourceHandlers(registry);
	}

	@Bean
	public ExceptionManage createManage() {
		return new ExceptionManage(environment);
	}

}
