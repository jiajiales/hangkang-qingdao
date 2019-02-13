package com.hotcomm.prevention.config;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.hotcomm.prevention.bean.TStPptnR;
import com.hotcomm.prevention.bean.TStRiverR;
import com.hotcomm.prevention.bean.TStStbprpB;
import com.hotcomm.prevention.utils.JSONUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Configuration
public class RabbitMQSendMsg implements EnvironmentAware {
	// ip地址
	private static String host;
	// 端口
	private static Integer port;
	// 用户名
	private static String username;
	// 密码
	private static String password;
	// 虚拟主机
	private static String virtualhost;
	// 队列名称
	private static String queue;

	public static void sendMsg(List<TStStbprpB> tStStbprpBSqlServer, List<TStPptnR> tStPptnR, List<TStRiverR> tStRiverR)
			throws IOException, TimeoutException {
		// 推送队列
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);// MQ的IP
		factory.setVirtualHost(virtualhost);
		factory.setPort(port);// MQ端口
		factory.setUsername(username);// MQ用户名
		factory.setPassword(password);// MQ密码
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		byte[] tStStbprpBSqlServerInfo = JSONUtil.toJson(tStStbprpBSqlServer).getBytes("utf-8");
		byte[] tStPptnRInfo = JSONUtil.toJson(tStPptnR).getBytes("utf-8");
		byte[] tStRiverRInfo = JSONUtil.toJson(tStRiverR).getBytes("utf-8");
		channel.basicPublish("", queue, null, tStStbprpBSqlServerInfo);
		channel.basicPublish("", queue, null, tStPptnRInfo);
		channel.basicPublish("", queue, null, tStRiverRInfo);
		channel.close();
		connection.close();
	}

	@SuppressWarnings("static-access")
	@Override
	public void setEnvironment(Environment environment) {
		String host = environment.getProperty("spring.rabbitmq.send.host", String.class);
		String port = environment.getProperty("spring.rabbitmq.send.port", String.class);
		String username = environment.getProperty("spring.rabbitmq.send.username", String.class);
		String password = environment.getProperty("spring.rabbitmq.send.password", String.class);
		String virtualhost = environment.getProperty("spring.rabbitmq.send.virtual-host", String.class);
		String queue = environment.getProperty("spring.rabbitmq.send.queue", String.class);
		this.host = host;
		this.port = Integer.valueOf(port);
		this.username = username;
		this.password = password;
		this.virtualhost = virtualhost;
		this.queue = queue;
	}
}
