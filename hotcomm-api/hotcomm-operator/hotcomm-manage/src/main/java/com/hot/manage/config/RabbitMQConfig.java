package com.hot.manage.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.gson.Gson;
import com.hot.manage.db.common.AppPush.AppPushMsgMapper;
import com.hot.manage.entity.common.AppPush.T_hk_apppush;
import com.hot.manage.entity.common.AppPush.T_hk_apppush_msg;
import com.hot.manage.entity.common.alarm.AlarmDev;
import com.hot.manage.entity.common.alarm.T_dev_alarm;
import com.hot.manage.entity.system.TMessageLog;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.SocketService;
import com.hot.manage.service.common.AppPush.AppPushService;
import com.hot.manage.service.common.dev.DeviceService;
import com.hot.manage.service.system.TMessageLogService;
import com.hot.manage.utils.ApiResult;
import com.hot.manage.utils.ConverUtil;
import com.hot.manage.utils.JSONUtil;
import com.hot.manage.utils.PushUtil;
import com.rabbitmq.client.Channel;

@Configuration
public class RabbitMQConfig {
	@Value("${spring.rabbitmq.host}")
	private String host;
	@Value("${spring.rabbitmq.port}")
	private Integer port;
	@Value("${spring.rabbitmq.username}")
	private String username;
	@Value("${spring.rabbitmq.password}")
	private String password;
	@Value("${spring.rabbitmq.virtual-host}")
	private String virtualhost;
	@Value("${spring.rabbitmq.queue}")
	private String queue;

	@Autowired
	SocketService socketService;
	@Autowired
	DeviceService deviceService;
	@Autowired
	TMessageLogService MessageLogService;
	@Autowired
	AppPushService appPushService;
	@Autowired
	AppPushMsgMapper appPushMsgMapper;

	@Bean
	public ConnectionFactory creatConnectionFactory() {
		CachingConnectionFactory factory = new CachingConnectionFactory();
		factory.setHost(host);
		factory.setVirtualHost(virtualhost);
		factory.setPort(port);
		factory.setUsername(username);
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
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			byte[] body = message.getBody();
			String ss = new String(body);
			System.out.println(ss);
			publishMessage(ss);
		}
	}

	/**
	 * 解析message信息，确定设备类型，查询指定表，确定责任人，发送信息(测试)
	 * 
	 * @param message
	 */
	public void publishMessage(String message) throws MyException {
		TMessageLog tMessageLog = new TMessageLog();
		Gson gson = new Gson();
		T_dev_alarm dev_alarm = gson.fromJson(message, T_dev_alarm.class);
		// 查询当前设备的责任人（安装位置等）
		AlarmDev dev = deviceService.selectDevForUser(dev_alarm.getDeviceid(), dev_alarm.getModuleid());
		dev.setAlarmtime(ConverUtil.timeForString(new Date()));
		String json = JSONUtil.toJson(dev);
		tMessageLog.setMessage(json);
		tMessageLog.setReceiverid(dev.getOwnid());
		tMessageLog.setSendtime(ConverUtil.timeForString(new Date()));
		tMessageLog.setUserid(0);
		// String s="{\"systate\":,\"message\":"+tMessageLog+"}";
		//推送指定用户
		//socketService.sendMessageToOne(tMessageLog);
		//广播
		socketService.sendMessageToAll(tMessageLog);
		if (dev.getOwnid() != null && !dev.getOwnid().equals(null) && !dev.getOwnid().equals("")) {
			String[] uid = dev.getOwnid().split(",");
			List<String> list = new ArrayList<>();
			for (String u : uid) {
				if (!list.contains(u)) {
					list.add(u);
				}
			}
			for (int i = 0; i < list.size(); i++) {
				// 查出用户regid
				T_hk_apppush t_hk_apppush = appPushService.selectRegid(Integer.valueOf(list.get(i)));
				// 向单个用户推送报警消息
				dev.setId(dev_alarm.getId());
				int code = PushUtil.sendAllsetNotification("报警消息", "设备：" + dev.getDevnum() + "发生报警，请及时查看处理",
						JSONUtil.toJson(ApiResult.resultInfo("0", "报警", dev)), t_hk_apppush.getRegid(), 86400);
				if (code == 201) {// 201推送失败
					// 推送失败的时候，把推送信息存进数据库，等下次登录的时候从数据库取出推送
					T_hk_apppush_msg t_hk_apppush_msg = new T_hk_apppush_msg();// 推送消息储存表
					t_hk_apppush_msg.setTitle("报警消息");
					t_hk_apppush_msg.setContent("设备：" + dev.getDevnum() + "发生报警，请及时查看处理");
					t_hk_apppush_msg.setMessage(JSONUtil.toJson(ApiResult.resultInfo("0", "报警", dev)));
					t_hk_apppush_msg.setRegids(t_hk_apppush.getRegid());
					t_hk_apppush_msg.setTimeToLive(String.valueOf(86400));
					t_hk_apppush_msg.setUserid(t_hk_apppush.getUserid());
					// 插入推送消息数据库
					appPushMsgMapper.insertSelective(t_hk_apppush_msg);
				}
			}
		}
	}
}
