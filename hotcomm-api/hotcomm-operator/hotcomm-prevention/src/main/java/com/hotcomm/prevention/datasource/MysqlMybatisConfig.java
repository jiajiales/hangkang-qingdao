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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = { "com.hotcomm.prevention.db.mysql" }, sqlSessionFactoryRef = "mysqlSqlSessionFactory")
public class MysqlMybatisConfig {
	@Autowired
	Environment environment;

	@Bean("mysqlSqlSessionFactory")
	public SqlSessionFactory sqlSessionFactoryMysql(@Qualifier("mysqlDataSource") DataSource mysqlDataSource)
			throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(mysqlDataSource);
		// 分页插件配置
		/*PageInterceptor interceptor = new PageInterceptor();
		Properties properties = new Properties();
		properties.setProperty("helperDialect", environment.getProperty("pagehelper.mysql.helperDialect"));
		properties.setProperty("reasonable", environment.getProperty("pagehelper.mysql.reasonable"));
		properties.setProperty("supportMethodsArguments",
				environment.getProperty("pagehelper.mysql.supportMethodsArguments"));
		properties.setProperty("params", environment.getProperty("pagehelper.mysql.params"));
		// properties.setProperty("rowBoundsWithCount", "true");
		interceptor.setProperties(properties);
		factoryBean.setPlugins(new Interceptor[] { interceptor });*/
		// 添加XML目录
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		factoryBean.setTypeAliasesPackage("com.hotcomm.prevention.db.mysql");
		factoryBean.setMapperLocations(resolver.getResources("classpath:mappers/mysql/**/*.xml"));
		factoryBean.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));
		return factoryBean.getObject();
	}

	@Bean("mysqlDataSourceTransactionManager")
	public DataSourceTransactionManager DataSourceTransactionManager(
			@Qualifier("mysqlDataSource") DataSource mysqlDataSource) {
		return new DataSourceTransactionManager(mysqlDataSource);
	}

	@Bean("mysqlSqlSessionTemplate")
	public SqlSessionTemplate SqlSessionTemplateMysql(
			@Qualifier("mysqlSqlSessionFactory") SqlSessionFactory mysqlSqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(mysqlSqlSessionFactory);
	}
}
