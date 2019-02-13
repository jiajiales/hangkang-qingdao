package com.hotcomm.prevention.config;

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
import com.hotcomm.prevention.bean.mysql.common.entity.TStPptnR;
import com.hotcomm.prevention.bean.mysql.common.entity.TStRiverR;
import com.hotcomm.prevention.bean.mysql.common.entity.TStStbprpB;
import com.hotcomm.prevention.bean.mysql.manage.alarm.T_dev_alarm;
import com.hotcomm.prevention.bean.mysql.manage.appPush.T_hk_apppush;
import com.hotcomm.prevention.bean.mysql.manage.appPush.T_hk_apppush_msg;
import com.hotcomm.prevention.bean.mysql.manage.event.AlarmDev;
import com.hotcomm.prevention.bean.mysql.manage.message.TMessageLog;
import com.hotcomm.prevention.db.mysql.manage.appPush.AppPushMsgMapper;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.data.DataService;
import com.hotcomm.prevention.service.manage.appPush.AppPushService;
import com.hotcomm.prevention.service.manage.event.EventService;
import com.hotcomm.prevention.service.manage.message.TMessageLogService;
import com.hotcomm.prevention.service.manage.websocket.SocketService;
import com.hotcomm.prevention.utils.ApiResult;
import com.hotcomm.prevention.utils.ConverUtil;
import com.hotcomm.prevention.utils.JSONUtil;
import com.hotcomm.prevention.utils.PushUtil;
import com.rabbitmq.client.Channel;

@Configuration
public class RabbitMQConfig {

	/**
	 * 注入配置文件属性
	 */
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

	TStPptnR infoTStPptnR = new TStPptnR();

	@Autowired
	SocketService socketService;
	@Autowired
	EventService eventService;
	@Autowired
	TMessageLogService MessageLogService;
	@Autowired
	AppPushService appPushService;
	@Autowired
	AppPushMsgMapper appPushMsgMapper;
	@Autowired
	DataService dataService;

	/**
	 * 创建 ConnectionFactory
	 *
	 * @return
	 * @throws Exception
	 */

	@Bean
	public ConnectionFactory creatConnectionFactory() throws Exception {
		CachingConnectionFactory factory = new CachingConnectionFactory();
		factory.setHost(host);
		factory.setVirtualHost(virtualhost);
		factory.setPort(port);
		factory.setUsername(username);
		factory.setPassword(password);
		return factory;

	}

	// rabbitmq的模板配置
	@Bean
	public RabbitTemplate receiveRabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate firstRabbitTemplate = new RabbitTemplate(connectionFactory);
		return firstRabbitTemplate;
	}

	// 消费者
	@Bean
	public SimpleMessageListenerContainer messageContainer(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		String[] split = queue.split(",");
		container.setQueues(new Queue[] { new Queue(split[0]), new Queue(split[1]) }); // 设置监听的队列
		container.setExposeListenerChannel(true);
		container.setMaxConcurrentConsumers(5);
		container.setConcurrentConsumers(1);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 设置确认模式手工确认
		container.setMessageListener(new Receiver());
		return container;
	}

	class Receiver implements ChannelAwareMessageListener {
		@Override
		public void onMessage(Message message, Channel channel) throws Exception {
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 确认消息成功消费
			byte[] body = message.getBody();
			String ss = new String(body, "utf-8");
			System.out.println(ss);
			String[] split = queue.split(",");
			if (split[0].equals(message.getMessageProperties().getConsumerQueue())) {
				publishMessage(ss);
			} else if (split[1].equals(message.getMessageProperties().getConsumerQueue())) {
				new Thread(new Runnable() {
					public void run() {
						dataService.updateMySqlData(ss);
					}
				}).start();

			}
		}
	}

	/**
	 * 解析message信息，确定设备类型，查询指定表，确定责任人，发送信息
	 * 
	 * @param message
	 */
	public void publishMessage(String message) throws MyException {
		TMessageLog tMessageLog = new TMessageLog();
		Gson gson = new Gson();
		T_dev_alarm dev_alarm = gson.fromJson(message, T_dev_alarm.class);
		// 查询当前设备的责任人（安装位置等）
		AlarmDev dev = eventService.selectDevForUser(dev_alarm.getDeviceid(), dev_alarm.getModuleid());
		dev.setAlarmtime(ConverUtil.dateForString(new Date()));
		String json = JSONUtil.toJson(dev);
		tMessageLog.setMessage(json);
		tMessageLog.setReceiverid(dev.getOwnid());
		tMessageLog.setSendtime(ConverUtil.dateForString(new Date()));
		tMessageLog.setUserid(0);
		// String s="{\"systate\":,\"message\":"+tMessageLog+"}";
		// 推送指定用户
		// socketService.sendMessageToOne(tMessageLog);
		// 广播
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
				int code = 0;
				if (t_hk_apppush != null) {
					// 向单个用户推送报警消息
					dev.setId(dev_alarm.getId());
					code = PushUtil.sendAllsetNotification("报警消息", "设备：" + dev.getDevnum() + "发生报警，请及时查看处理",
							JSONUtil.toJson(ApiResult.resultInfo("0", "报警", dev)), t_hk_apppush.getRegid(), 86400);
				}
				if (code == 0 || code == 201) {// 201推送失败
					// 推送失败的时候，把推送信息存进数据库，等下次登录的时候从数据库取出推送
					T_hk_apppush_msg t_hk_apppush_msg = new T_hk_apppush_msg();// 推送消息储存表
					t_hk_apppush_msg.setTitle("报警消息");
					t_hk_apppush_msg.setContent("设备：" + dev.getDevnum() + "发生报警，请及时查看处理");
					t_hk_apppush_msg.setMessage(JSONUtil.toJson(ApiResult.resultInfo("0", "报警", dev)));
					t_hk_apppush_msg.setRegids("0");
					t_hk_apppush_msg.setTimeToLive(String.valueOf(86400));
					t_hk_apppush_msg.setUserid(Integer.valueOf(list.get(i)));
					// 插入推送消息数据库
					appPushMsgMapper.insertSelective(t_hk_apppush_msg);
				}
			}
		}
	}
}
