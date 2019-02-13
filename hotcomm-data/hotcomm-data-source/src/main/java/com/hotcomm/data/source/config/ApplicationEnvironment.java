package com.hotcomm.data.source.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEnvironment {
	
	
	@Value("${spring.redis.database}")
	public Integer redisIndex;
	
}
