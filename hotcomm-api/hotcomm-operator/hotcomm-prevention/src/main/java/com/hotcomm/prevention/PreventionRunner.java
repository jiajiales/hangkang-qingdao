package com.hotcomm.prevention;

import java.util.Date;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.hotcomm.prevention.service.data.DataService;
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class }, scanBasePackages = { "com.hotcomm.prevention" })
@MapperScan("com.hotcomm.prevention.db")
@EnableScheduling
public class PreventionRunner {
	
	@Autowired
	DataService dataService;
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(PreventionRunner.class).run(args);
	}
	
	@Scheduled(cron = "0/120 * * * * *")
    public void timer(){
		System.out.println("推送最新报警站点:"+new Date().toString());
		dataService.checkAlarmStationInTwoMinutes();
    }

}
