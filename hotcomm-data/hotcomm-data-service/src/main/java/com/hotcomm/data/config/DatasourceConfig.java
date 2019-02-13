package com.hotcomm.data.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class DatasourceConfig  {

	@Autowired
	private Environment environment;
	
	@Bean
	public DataSource writeDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		try {
			dataSource.setUrl(this.environment.getProperty("spring.datasource.url"));
			dataSource.setUsername(this.environment.getProperty("spring.datasource.username"));
			dataSource.setPassword(this.environment.getProperty("spring.datasource.password"));
			dataSource.setInitialSize(Integer.parseInt(this.environment.getProperty("spring.datasource.initialSize")));
			dataSource.setMinIdle(Integer.parseInt(this.environment.getProperty("spring.datasource.minIdle")));
			dataSource.setMaxActive(Integer.parseInt(this.environment.getProperty("spring.datasource.maxActive")));
			dataSource.setMaxWait(Long.parseLong(this.environment.getProperty("spring.datasource.maxWait")));
			dataSource.setTimeBetweenEvictionRunsMillis(Integer.parseInt(this.environment.getProperty("spring.datasource.timeBetweenEvictionRunsMillis")));
			dataSource.setMinEvictableIdleTimeMillis(Integer.parseInt(this.environment.getProperty("spring.datasource.minEvictableIdleTimeMillis")));
			dataSource.setValidationQuery(this.environment.getProperty("spring.datasource.validationQuery"));
			dataSource.setTestWhileIdle(Boolean.parseBoolean(this.environment.getProperty("spring.datasource.testWhileIdle")));
			dataSource.setTestOnBorrow(Boolean.parseBoolean(this.environment.getProperty("spring.datasource.testOnBorrow")));
			dataSource.setTestOnReturn(Boolean.parseBoolean(this.environment.getProperty("spring.datasource.testOnReturn")));
			dataSource.setPoolPreparedStatements(Boolean.parseBoolean(this.environment.getProperty("poolPreparedStatements")));
			dataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(this.environment.getProperty("spring.datasource.maxPoolPreparedStatementPerConnectionSize")));
			dataSource.setConnectionProperties(this.environment.getProperty("spring.datasource.connectionProperties"));
			dataSource.setUseGlobalDataSourceStat(Boolean.parseBoolean(this.environment.getProperty("spring.datasource.useGlobalDataSourceStat")));
			dataSource.setFilters(this.environment.getProperty("spring.datasource.filters"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dataSource;
	}

}
