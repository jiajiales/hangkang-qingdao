package com.hotcomm.data;

import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.hotcomm.data.config.ApplicationEnvironment;
import com.hotcomm.framework.utils.RedisHelper;

/**
 * @Description: springboot 项目启动器
 * @author http://www.hotcomm.net/
 * @date 2018年3月12日 上午10:04:57
 */
@SpringBootApplication(scanBasePackages = { "com.hotcomm" })
@MapperScan(basePackages = { "com.hotcomm.data.db" })
@EnableScheduling
public class DataRunner {

	public static void main(String[] args) {
		new SpringApplicationBuilder(DataRunner.class).run(args);
	}

	@Bean
	public RedisHelper initRedisBean(ApplicationEnvironment environment) {
		return new RedisHelper(environment.getRedisIndex());
	}
}
