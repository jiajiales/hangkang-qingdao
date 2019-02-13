package com.hotcomm.data.view;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.hotcomm.data.view.config.ApplicationEnvironment;
import com.hotcomm.framework.utils.RedisHelper;

/**
 * @Description: springboot 项目启动器
 * @author http://www.hotcomm.net/
 * @date 2018年3月12日 上午10:04:57
 */

@SpringBootApplication(scanBasePackages = { "com.hotcomm" })
@ServletComponentScan(basePackages= {"com.hotcomm.data.view.config"})
@EnableScheduling
public class DataViewRunner   extends SpringBootServletInitializer  {
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DataViewRunner.class);
    }
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(DataViewRunner.class).run(args);
	}

	@Bean
	public RedisHelper initRedisBean(ApplicationEnvironment environment) {
		return new RedisHelper(environment.redisIndex);
	}
	
	
}
