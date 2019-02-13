package com.hot.analysis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Description: springboot 项目启动器
 * @author  http://www.hotcomm.net/
 * @date 2018年3月12日 上午10:04:57
 */
@SpringBootApplication(scanBasePackages= {"com.hot.analysis"})
@MapperScan("com.hot.analysis.db")
@ServletComponentScan
public class AnalysisViewRunner extends WebMvcConfigurerAdapter {
	
	public static void main(String[] args) {
		 new SpringApplicationBuilder(AnalysisViewRunner.class).run(args);  
	}
}
