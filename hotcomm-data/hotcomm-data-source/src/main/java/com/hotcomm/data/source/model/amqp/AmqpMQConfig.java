package com.hotcomm.data.source.model.amqp;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.CacheMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import com.hotcomm.framework.utils.SpringUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.AMQImpl;
import com.rabbitmq.client.impl.AMQImpl.Basic.Ack;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class AmqpMQConfig  {
	
	@Autowired
	private Environment environment;
	

	@Bean(name = "receiveFactory")
	@Primary
	public ConnectionFactory receiveConnectionFactory() throws Exception {
		log.info("##############[ReceiveConnectionFactory init]#################");
		com.rabbitmq.client.ConnectionFactory factory = new com.rabbitmq.client.ConnectionFactory();
		factory.setUsername(this.environment.getProperty("spring.rabbitmq.receive.username"));
		factory.setPassword(this.environment.getProperty("spring.rabbitmq.receive.password"));
		factory.setHost(this.environment.getProperty("spring.rabbitmq.receive.host"));
		factory.setVirtualHost(this.environment.getProperty("spring.rabbitmq.receive.vhost"));
		String pv = this.environment.getProperty("spring.rabbitmq.receive.port");
		factory.setPort(pv==null||pv.trim().length()<=0?5672:Integer.valueOf(pv));
		factory.setAutomaticRecoveryEnabled(false);
		String profile = environment.getProperty("spring.profiles.active", String.class);
		if (profile.equals("prod")) {
			factory.useSslProtocol("tlsv1.2");
		}
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(factory);
		connectionFactory.setCacheMode(CacheMode.CHANNEL);
		connectionFactory.setChannelCacheSize(50);
		return connectionFactory;
	}
	
	@Bean(name = "pushFactory")
	public ConnectionFactory pushConnectionFactory() throws Exception {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setCacheMode(CacheMode.CHANNEL);
		connectionFactory.setChannelCacheSize(50);
		connectionFactory.setUsername(this.environment.getProperty("spring.rabbitmq.send.username"));
		connectionFactory.setPassword(this.environment.getProperty("spring.rabbitmq.send.password"));
		connectionFactory.setHost(this.environment.getProperty("spring.rabbitmq.send.host"));
		connectionFactory.setVirtualHost(this.environment.getProperty("spring.rabbitmq.send.vhost"));
		String pv = this.environment.getProperty("spring.rabbitmq.send.port");
		connectionFactory.setPort(pv==null||pv.trim().length()<=0?5672:Integer.valueOf(pv));
		connectionFactory.setPublisherConfirms(this.environment.getProperty("spring.rabbitmq.send.publisher-confirms",Boolean.class));
		return connectionFactory;
	}
	
	@Bean(name = "receiveListenerFactory")
	@Primary
	public SimpleMessageListenerContainer messageContainer(@Qualifier("receiveFactory") ConnectionFactory connectionFactory) {
		log.info("##############[ReceiveListenerFactory init]#################");
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		String queueName = this.environment.getProperty("spring.rabbitmq.receivd.queue");
		container.setQueues(new Queue[] { new Queue(queueName) });
		container.setExposeListenerChannel(true);
		container.setMaxConcurrentConsumers(120);
		container.setConcurrentConsumers(20);
		container.setPrefetchCount(500);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 设置确认模式手工确认
		container.setMessageListener(new ReceiveQueueListener(SpringUtil.getBean(AmqpMessageHandle.class)));
		return container;
	}
	
	@Bean
	public RabbitTemplate initTemplate(@Qualifier("pushFactory") ConnectionFactory factory) {
		RabbitTemplate template = new RabbitTemplate(factory);
		String queueName = this.environment.getProperty("spring.rabbitmq.send.queue");
		template.setRoutingKey(queueName);
		return template;
	}
	
	class ReceiveQueueListener implements ChannelAwareMessageListener {
		
		AmqpMessageHandle handle;
		
		public ReceiveQueueListener(AmqpMessageHandle handle) {
			super();
			this.handle = handle;
		}

		@Override
		public void onMessage(Message message, Channel channel) throws Exception {
			Ack ack = new AMQImpl.Basic.Ack(message.getMessageProperties().getDeliveryTag(),false);
			channel.asyncRpc(ack);
			handle.handleMessage(message.getBody());
		}
		
	}
	
	
}
