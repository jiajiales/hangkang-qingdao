package com.hotcomm.data.source;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import com.hotcomm.data.source.config.ApplicationEnvironment;
import com.hotcomm.framework.utils.RedisHelper;

@SpringBootApplication(scanBasePackages = { "com.hotcomm" })
public class SourceApplicationRunner {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SourceApplicationRunner.class).web(WebApplicationType.NONE).run(args);
	}
	
	@Bean
	public RedisHelper initRedisBean(ApplicationEnvironment environment) {
		return new RedisHelper(environment.redisIndex);
	}
}
