package com.hotcomm.data.model.rabbitmq;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.CacheMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.hotcomm.data.bean.entity.service.MemberVhost;
import com.hotcomm.data.config.ApplicationEnvironment;
import com.hotcomm.data.service.CustomerService;
import com.hotcomm.data.utils.HttpApiResult;
import com.hotcomm.data.utils.LogUtil;
import com.hotcomm.framework.web.exception.ExceptionManager;
import com.hotcomm.framework.web.exception.HKException;

@Component
public class RabbitVhostManager {

	@Autowired
	private CustomerService customerService;

	@Autowired
	@Qualifier("exceptionManager")
	private ExceptionManager exceptionManager;

	@Autowired
	private Environment environment;

	@Autowired
	private RabbitRestTool tools;

	@Autowired
	protected ApplicationContext applicationContext;
	
	@Autowired
	private ApplicationEnvironment applicationEnvironment;
	
	public List<MemberVhost> getSafeVhosts(){
		return customerService.listAuthors();
	}
	
	public MemberVhost getVhost(Integer memberId) {
		for (MemberVhost vhost : getSafeVhosts()) {
			if (vhost.getMemberId() == memberId)
				return vhost;
		}
		return null;
	}
	
	public void deleteVhostObject(String vhostName,String vhostAccount) {
		Logger log = LogUtil.SERVICE_LOG;
		HttpApiResult result = this.tools.deleteVhost(vhostName);
		if(result.getStatusCode()>299)
			throw new HKException("-1", "删除虚拟机地址失败,需要手动删除");
		log.info("删除虚拟机地址{}成功",vhostName);
		result = this.tools.deleteRabbitAccount(vhostAccount);
		if(result.getStatusCode()>299)
			throw new HKException("-1", "删除虚拟机账号失败,需要手动删除");
		log.info("删除虚拟机账号{}成功",vhostAccount);
	}
	
	public void validateExists(String vhostName, String vhostAccount, String vhostPwd) throws HKException {
		Logger log = LogUtil.SERVICE_LOG;
		boolean vhostNotExists = tools.existsVhost(vhostName);
		log.info("验证线上虚拟机地址{}{}", vhostName, vhostNotExists ? "已存在" : "不存在");
		if (vhostNotExists)
			throw new HKException("-1", String.format("虚拟机地址%s在地址池存在冲突,请重新输入新地址", vhostName));
		boolean userNotExists = tools.existsUser(vhostAccount);
		log.info("验证线上虚拟机账号{}{}", vhostName, vhostNotExists ? "已存在" : "不存在");
		if(userNotExists) 
			throw new HKException("-1", String.format("虚拟机账号%s在用户池存在冲突,请重新输入新账号", vhostName));
	}
		
	public void createRabbitVhostMessage(String vhostName, String vhostAccount, String vhostPwd)throws HKException  {
		Logger log = LogUtil.SERVICE_LOG;
		HttpApiResult result = tools.addRabbitVhost(vhostName);
		if(result.getStatusCode()>299)
			throw new HKException("-1", "获取虚拟机队列信息失败");
		log.info("创建虚拟机地址{}成功",vhostName);
		
		result = tools.addRabbitAccount(vhostAccount, vhostPwd);
		if(result.getStatusCode()>299)
			throw new HKException("-1", "获取虚拟机队列信息失败");
		log.info("创建虚拟机账号{}成功",vhostAccount);
		
		result = tools.vhostPermissionsToUser(vhostName, tools.adminName,"admin");
		if(result.getStatusCode()>299)
			throw new HKException("-1", "获取虚拟机队列信息失败");
		result = tools.vhostPermissionsToUser(vhostName, vhostAccount,"None");
		if(result.getStatusCode()>299)
			throw new HKException("-1", "获取虚拟机队列信息失败");
		log.info("分配账号{}虚拟机地址{}权限成功",vhostAccount,vhostName);
		
	}
	
	
	public ConnectionFactory createVhostSpringAmqpFactory(String vhostName, String factoryCode) {
		try {
			ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
			DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
			if (beanFactory.containsSingleton(factoryCode)) {
				beanFactory.destroySingleton(factoryCode);
			}
			RabbitConnectionFactoryBean sendFactoryBean = new RabbitConnectionFactoryBean();
			sendFactoryBean.setHost(applicationEnvironment.getAmqpSHost());
			sendFactoryBean.setPort(applicationEnvironment.getAmqpSPort());
			sendFactoryBean.setUsername(applicationEnvironment.getAmqpSName());
			sendFactoryBean.setPassword(applicationEnvironment.getAmqpSPwd());
			sendFactoryBean.setAutomaticRecoveryEnabled(false);
			sendFactoryBean.afterPropertiesSet();
			CachingConnectionFactory connectionFactory = new CachingConnectionFactory(sendFactoryBean.getObject());
			connectionFactory.setAddresses(applicationEnvironment.getAmqpSAddress());
			connectionFactory.setPublisherConfirms(applicationEnvironment.getAmqpSConfirm());
			connectionFactory.setCacheMode(CacheMode.CONNECTION);
			connectionFactory.setVirtualHost(vhostName);
			beanFactory.registerSingleton(factoryCode, connectionFactory);
			return connectionFactory;
		} catch (Exception e) {
			e.printStackTrace();
			throw new HKException("VHOST0009",
					environment.getProperty("VHOST0009") + ",vhost:" + vhostName + ",factoryCode=" + factoryCode);
		}
	}

	public void removeVhostFactorySpringBean(String factoryCode) {
		ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
		if (beanFactory.containsSingleton(factoryCode)) {
			beanFactory.destroySingleton(factoryCode);
		}
	}

}
