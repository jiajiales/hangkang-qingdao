package com.hotcomm.prevention.datasource;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

@Configuration
@MapperScan(basePackages = {
		"com.hotcomm.prevention.db.sqlserver" }, sqlSessionFactoryRef = "sqlserverSqlSessionFactory")
public class SqlserverMybatisConfig {

	@Autowired
	Environment environment;

	@Bean("sqlserverSqlSessionFactory")
	public SqlSessionFactory SqlSessionFactory(@Qualifier("sqlserverDataSource") DataSource sqlserverDataSource)
			throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(sqlserverDataSource);
		// 分页插件配置
//		PageInterceptor interceptor = new PageInterceptor();
//		Properties properties = new Properties();
//		properties.setProperty("helperDialect", environment.getProperty("pagehelper.sqlserver.helperDialect"));
//		properties.setProperty("reasonable", environment.getProperty("pagehelper.sqlserver.reasonable"));
//		properties.setProperty("supportMethodsArguments",
//				environment.getProperty("pagehelper.sqlserver.supportMethodsArguments"));
//		properties.setProperty("params", environment.getProperty("pagehelper.sqlserver.params"));
//		// properties.setProperty("rowBoundsWithCount", "true");
//		interceptor.setProperties(properties);
//		factoryBean.setPlugins(new Interceptor[] { interceptor });
		// 添加xml目录
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		factoryBean.setTypeAliasesPackage("com.hotcomm.prevention.db.sqlserver");
		factoryBean.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));
		factoryBean.setMapperLocations(resolver.getResources("classpath:mappers/sqlserver/**/*.xml"));
		return factoryBean.getObject();
	}

	@Bean("sqlserverSqlSessionTemplate")
	public SqlSessionTemplate SqlSessionTemplate(
			@Qualifier("sqlserverSqlSessionFactory") SqlSessionFactory sqlserverSqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlserverSqlSessionFactory);
	}

}
