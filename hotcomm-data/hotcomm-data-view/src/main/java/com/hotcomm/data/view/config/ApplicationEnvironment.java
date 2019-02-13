package com.hotcomm.data.view.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEnvironment {
	
	
	@Value("${spring.redis.database}")
	public Integer redisIndex;
	
	@Value("${PROXY_PORT}")
	public String PROXY_PORT;
	
	@Value("${PROXY_HOST}")
	public String PROXY_HOST;
	
}
