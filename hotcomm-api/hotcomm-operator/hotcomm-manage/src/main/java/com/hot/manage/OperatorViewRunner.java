package com.hot.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @Description: springboot 项目启动器
 * @author  http://www.hotcomm.net/
 * @date 2018年3月12日 上午10:04:57
 */
@SpringBootApplication(scanBasePackages= {"com.hot.manage"})
@MapperScan("com.hot.manage.db")
@ServletComponentScan
public class OperatorViewRunner {
	
	public static void main(String[] args) {
		 new SpringApplicationBuilder(OperatorViewRunner.class).run(args);  
	}
}
