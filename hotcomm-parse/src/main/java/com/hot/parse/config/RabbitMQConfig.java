package com.hot.parse.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.gson.Gson;
import com.hot.parse.distribution.Analysis;
import com.hot.parse.entity.common.ReceiveModel;
import com.hot.parse.utils.MyTask;
import com.hot.parse.utils.ThreadPool;
import com.rabbitmq.client.Channel;

@Configuration
public class RabbitMQConfig {

	// ip地址
	@Value("${spring.rabbitmq.host}")
	private String host;
	// 端口
	@Value("${spring.rabbitmq.port}")
	private Integer port;
	// 用户名
	@Value("${spring.rabbitmq.username}")
	private String username;
	// 密码
	@Value("${spring.rabbitmq.password}")
	private String password;
	// 虚拟主机
	@Value("${spring.rabbitmq.virtual-host}")
	private String virtualhost;
	// 队列名称
	@Value("${spring.rabbitmq.queue}")
	private String queue;
	// rabbitmq处理线程数
	@Value("${spring.rabbitmq.worker_num}")
	private int worker_num;

	private ThreadPool handerdc;

	private Gson gson;

	private ReceiveModel model;

	private Logger logger;

	/**
	 * 设置队列连接信息
	 * 
	 * @return
	 */
	@Bean
	public ConnectionFactory creatConnectionFactory() {
		// 创建worker_num个线程的线程池
		this.handerdc = ThreadPool.getThreadPool(worker_num);
		CachingConnectionFactory factory = new CachingConnectionFactory();
		// 设置队列地址
		factory.setHost(host);
		// 设置虚拟主机名
		factory.setVirtualHost(virtualhost);
		// 设置端口号
		factory.setPort(port);
		// 用户名
		factory.setUsername(username);
		// 密码
		factory.setPassword(password);
		return factory;
	}

	@Bean
	public RabbitTemplate receiveRabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate firstRabbitTemplate = new RabbitTemplate(connectionFactory);
		return firstRabbitTemplate;
	}

	@Bean
	public SimpleMessageListenerContainer messageContainer(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setQueues(new Queue[] { new Queue(queue) });
		container.setExposeListenerChannel(true);
		container.setMaxConcurrentConsumers(5);
		container.setConcurrentConsumers(1);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		container.setMessageListener(new Receiver());
		return container;
	}

	class Receiver implements ChannelAwareMessageListener {
		@Override
		public void onMessage(Message message, Channel channel) throws Exception {
			// 返回消息确认
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			// 取出队列消息内容
			byte[] body = message.getBody();
			String ss = new String(body);
			publishMessage(ss);
		}
	}

	public RabbitMQConfig() {
		this.gson = new Gson();
		this.model = new ReceiveModel();
		this.logger = LoggerFactory.getLogger(RabbitMQConfig.class);
	}

	/**
	 * 使用線程池執行解析任務
	 * 
	 * @param message
	 */
	public void publishMessage(String message) {
		logger.error("rabbitmq数据：{}", message);
		model = gson.fromJson(message, ReceiveModel.class);
		// 分发数据进入模块判断
		int moduleid = Analysis.GetBelongFun(model);
		// 把任务分发给线程池处理
		handerdc.execute(new Runnable[] { new MyTask(moduleid, model) });
		logger.info("线程信息：{}", handerdc);
	}
}
