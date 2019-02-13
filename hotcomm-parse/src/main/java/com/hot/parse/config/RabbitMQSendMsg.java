package com.hot.parse.config;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.hot.parse.entity.common.DevAlarm;
import com.hot.parse.entity.common.DevMsg;
import com.hot.parse.entity.common.SendAlarmMq;
import com.hot.parse.utils.ConvertHelp;
import com.hot.parse.utils.JSONUtil;
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

	private Logger logger = LoggerFactory.getLogger(RabbitMQSendMsg.class);

	public static void sendMsg(DevMsg devMsg, DevAlarm devAlarm, int moduleid, String Recv)
			throws IOException, TimeoutException {
		Logger logger = LoggerFactory.getLogger(RabbitMQSendMsg.class);
		SendAlarmMq sendAlarmMq = new SendAlarmMq();
		sendAlarmMq.setAddtime(ConvertHelp.getOnTime());
		sendAlarmMq.setDeviceid(devMsg.getDeviceid());
		sendAlarmMq.setAlarmstateid(devAlarm.getAlarmstateid());
		sendAlarmMq.setMac(devMsg.getMac());
		sendAlarmMq.setModuleid(moduleid);
		sendAlarmMq.setId(devAlarm.getId());
		if (!TextUtils.isEmpty(Recv)) {
			sendAlarmMq.setRecvtime(ConvertHelp.UTCStringtODefaultString(Recv));
		} else {
			sendAlarmMq.setRecvtime(ConvertHelp.getOnTime());
		}
		// 推送队列
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);// MQ的IP
		factory.setVirtualHost(virtualhost);
		factory.setPort(port);// MQ端口
		factory.setUsername(username);// MQ用户名
		factory.setPassword(password);// MQ密码
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		/* 发送消息 */
		String message = JSONUtil.toJson(sendAlarmMq);
		logger.error("推送报警队列{}", message);
		channel.basicPublish("", queue, null, message.getBytes());
		/* 关闭连接 */
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
