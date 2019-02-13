package com.hotcomm.prevention.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration 
public class DataSourceConfig {
	@Autowired
	Environment environment;
	
	@Bean("mysqlDataSource")
	public DataSource dataSourcemysql(){
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(environment.getProperty("spring.datasource.mysql.driver-class-name"));
		dataSource.setUrl(environment.getProperty("spring.datasource.mysql.url"));
		dataSource.setUsername(environment.getProperty("spring.datasource.mysql.username"));
		dataSource.setPassword(environment.getProperty("spring.datasource.mysql.password"));
		return dataSource;
	}
	
	@Bean("sqlserverDataSource")
	public DataSource dataSourceSqlServer() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(environment.getProperty("spring.datasource.sqlserver.driver-class-name"));
		dataSource.setUrl(environment.getProperty("spring.datasource.sqlserver.url"));
		dataSource.setUsername(environment.getProperty("spring.datasource.sqlserver.username"));
		dataSource.setPassword(environment.getProperty("spring.datasource.sqlserver.password"));
		return dataSource;
	}
	
	@Bean("dynamicDataSource")
	public DataSource dataSource(@Qualifier("mysqlDataSource") DataSource mysqlDataSource,
			@Qualifier("sqlserverDataSource") DataSource sqlserverDataSource) {
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		// 默认数据源
		dynamicDataSource.setDefaultTargetDataSource(mysqlDataSource);
		// 配置多数据源
		Map<Object, Object> dsMap = new HashMap<Object, Object>();
		dsMap.put("mysqlDataSource", mysqlDataSource);
		dsMap.put("sqlserverDataSource", sqlserverDataSource);
		dynamicDataSource.setTargetDataSources(dsMap);
		return dynamicDataSource;
	}

}
