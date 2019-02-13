package com.hotcomm.data.model.rabbitmq;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.CacheMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.hotcomm.data.config.ApplicationEnvironment;
import com.hotcomm.data.model.rabbitmq.receive.ReceiveMessageHandler;
import com.hotcomm.data.service.ReceiveDataErrorService;
import com.hotcomm.framework.utils.SpringUtil;
import com.rabbitmq.client.Channel;

@Configuration
public class RabbitReceiveConfig {

	public static AtomicInteger atomicInteger = new AtomicInteger(0);

	@Autowired
	private ReceiveMessageHandler handler;
	
	final ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(200);  
	
	@Bean(name = "receiveConnectionFactory")
	@Primary
	public ConnectionFactory receiveConnectionFactory(ApplicationEnvironment environment) throws Exception {
		com.rabbitmq.client.ConnectionFactory factory = new com.rabbitmq.client.ConnectionFactory();
		factory.setUsername(environment.getAmqpRName());
		factory.setPassword(environment.getAmqpRPwd());
		factory.setHost(environment.getAmqpRHost());
		factory.setVirtualHost(environment.getAmqpRVhost());
		factory.setPort(environment.getAmqpRPort());
		factory.setAutomaticRecoveryEnabled(false);
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(factory);
		connectionFactory.setCacheMode(CacheMode.CHANNEL);
		connectionFactory.setChannelCacheSize(250);
		return connectionFactory;
	}

	@Bean(name = "receiveMessageListenerFactory")
	@Primary
	public SimpleMessageListenerContainer messageContainer(@Qualifier("receiveConnectionFactory") ConnectionFactory connectionFactory, ApplicationEnvironment environment) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setQueues(new Queue[] { new Queue(environment.getAmqpRQueue()) });
		container.setExposeListenerChannel(true);
		container.setMaxConcurrentConsumers(20);
		container.setConcurrentConsumers(20);
		container.setPrefetchCount(500);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 设置确认模式手工确认
		container.setMessageListener(new HangKangConsumer(handler));
		container.setAutoStartup(false);
		return container;
	}

	class HangKangConsumer implements ChannelAwareMessageListener {

		ReceiveMessageHandler handler;

		public HangKangConsumer(ReceiveMessageHandler handler) {
			super();
			this.handler = handler;
		}

		@Override
		public void onMessage(Message message, Channel channel) throws Exception {
		
			String msg = new String(message.getBody());
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);// 确认消息成功消费
		
			scheduledThreadPool.execute(new Runnable() {
				
				@Override
				public void run() {
					try {
						handler.handleMessage(msg);
					} catch (Exception e) {
						e.printStackTrace();
						// channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //
						// 消息接收成功,但是数据处理失败
						ReceiveDataErrorService errorService = SpringUtil.getBean(ReceiveDataErrorService.class);
						errorService.addErrorData(new String(message.getBody()), e,
								message.getMessageProperties().getConsumerQueue());
					}
				}
			});
			
		}
	}

}
