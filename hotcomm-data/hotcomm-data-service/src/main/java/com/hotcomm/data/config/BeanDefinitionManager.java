package com.hotcomm.data.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BeanDefinitionManager {

	@Autowired
	protected ApplicationContext applicationContext;

	public void createApplicationBean(String beanName, Class<?> beanClazz, Map<String, Object> beanProperties) {
		ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(beanClazz);
		for (String key : beanProperties.keySet()) {
			beanDefinitionBuilder.addPropertyValue(key, beanProperties.get(key));
		}
		if (!beanFactory.isBeanNameInUse(beanName)) {
			beanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
		}
	}

	public void removeApplicaitonBean(String beanName) {
		ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
		if (beanFactory.isBeanNameInUse(beanName)) {
			beanFactory.removeBeanDefinition(beanName);
		}
	}

}
