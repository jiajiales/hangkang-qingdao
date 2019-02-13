package com.hotcomm.data.model.rabbitmq;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.hotcomm.framework.web.exception.HKException;
import com.rabbitmq.client.Channel;

@Component
public class RabbitListenerContainerFactory {

	@Autowired
	protected ApplicationContext applicationContext;

	public void createConnectionListenerFactory(String listenerFactoryBeanName, ConnectionFactory connfactory,
			String listenerQueue) throws HKException {
		ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
		if (beanFactory.containsSingleton(listenerFactoryBeanName)) {
			beanFactory.destroySingleton(listenerFactoryBeanName);
		}
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connfactory);
		container.setQueues(new Queue[] { new Queue(listenerQueue) });
		container.setExposeListenerChannel(true);
		container.setMaxConcurrentConsumers(5);
		container.setConcurrentConsumers(2);
		container.setAcknowledgeMode(AcknowledgeMode.AUTO); // 设置确认模式手工确认
		container.setMessageListener(new HangKangConsumer());
		container.setStopConsumerMinInterval(50000);
		container.start();
		beanFactory.registerSingleton(listenerFactoryBeanName, container);
	}

	public void startQueueListener(String listenerFactoryBeanName) {
		ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
		if (beanFactory.containsSingleton(listenerFactoryBeanName)) {
			SimpleMessageListenerContainer container = (SimpleMessageListenerContainer) beanFactory.getSingleton(listenerFactoryBeanName);
			container.start();
		}
	}

	public void stopQueueListener(String listenerFactoryBeanName) {
		ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
		if (beanFactory.containsSingleton(listenerFactoryBeanName)) {
			SimpleMessageListenerContainer container = (SimpleMessageListenerContainer) beanFactory.getSingleton(listenerFactoryBeanName);
			container.stop();
		}
	}

	class HangKangConsumer implements ChannelAwareMessageListener {
		@Override
		public void onMessage(Message message, Channel channel) throws Exception {
			try {
				byte[] body = message.getBody();
				String msg = new String(body);
				System.out.println(msg);
				// channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
