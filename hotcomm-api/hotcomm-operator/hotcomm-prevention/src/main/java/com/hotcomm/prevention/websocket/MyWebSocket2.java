package com.hotcomm.prevention.websocket;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.hotcomm.prevention.bean.mysql.manage.message.TMessageLog;
import com.hotcomm.prevention.service.common.TUserTokenService;
import com.hotcomm.prevention.service.manage.message.TMessageLogService;
import com.hotcomm.prevention.utils.ConverUtil;
import com.hotcomm.prevention.utils.JSONUtil;
import com.hotcomm.prevention.utils.SpringUtil;

@ServerEndpoint(value = "/websocket2/{userid}/{token}")
@Component
public class MyWebSocket2 {

	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;
	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;
	// 用来存放客户端对应的MyWebSocket对象
	public final static ConcurrentMap<String, MyWebSocket2> webSocketmap = new ConcurrentHashMap<String, MyWebSocket2>();

	TMessageLogService messageLogService;

	TUserTokenService userTokenService;

	public TMessageLogService getLogService() {
		if (messageLogService == null) {
			return SpringUtil.getApplicationContext().getBean(TMessageLogService.class);
		} else {
			return messageLogService;
		}
	}

	public TUserTokenService getLoginService() {
		if (messageLogService == null) {
			return SpringUtil.getApplicationContext().getBean(TUserTokenService.class);
		} else {
			return userTokenService;
		}
	}

	/**
	 * 连接建立成功调用的方法
	 */
	@OnOpen
	public void onOpen(@PathParam("userid") String userid, @PathParam("token") String token, Session session) {
		this.session = session;
		addOnlineCount(userid, token);
		webSocketmap.put(token + "_" + userid, this);
		getInfo(userid, token);
		System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
		System.out.println(webSocketmap);
	}

	private void getInfo(String userid, String token) {
		TMessageLogService messageLogService = getLogService();
		TMessageLog message = new TMessageLog();
		message.setReceiverid(userid);
		List<TMessageLog> list = messageLogService.selectUnsent(message);
		if (list.size() != 0 || list != null) {
			for (TMessageLog tMessageLog : list) {
				sendMessage(token+"_"+userid, tMessageLog.getMessage().toString());
				tMessageLog.setState(1);
				tMessageLog.setReceivetime(ConverUtil.dateForString(new Date()));
				messageLogService.update(tMessageLog);
			}
		}
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose(@PathParam("userid") String userid, @PathParam("token") String token) {
		webSocketmap.remove(token + "_" + userid, webSocketmap.get(token + "_" + userid));// 从map中删除
		subOnlineCount(); // 在线数减1
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 链接错误
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		error.printStackTrace();
	}

	/**
	 * 收到客户端消息后调用的方法
	 * 
	 * @param message
	 *            客户端发送过来的消息 指定接收对象，接收信息
	 * 
	 */
	@OnMessage
	public void onMessage(String message) {
		TMessageLogService messageLogService = getLogService();
		RequestMsg resmsg = JSONUtil.toBean(message, RequestMsg.class);
		TMessageLog tMessageLog = new TMessageLog();
		BeanUtils.copyProperties(resmsg, tMessageLog);
		String receiver = resmsg.getReceiveid();
		String[] ss = receiver.split(",");
		for (String s : ss) {
			Set<String> set = webSocketmap.keySet();
			for (String string : set) {
				if (string.contains("_" + s)) {
					sendMessage(string, resmsg.getMessage());
					tMessageLog.setSendtime(ConverUtil.dateForString(new Date()));
					tMessageLog.setReceiverid(s);
					tMessageLog.setReceivetime(ConverUtil.dateForString(new Date()));
					tMessageLog.setState(1);
				} else {
					tMessageLog.setSendtime(ConverUtil.dateForString(new Date()));
					tMessageLog.setReceiverid(s);
				}
			}
			messageLogService.insertOne(tMessageLog);
		}
		System.out.println("来自客户端的消息:" + resmsg.getMessage());
	}

	// 对指定用户发送信息
	public static void sendMessage(String key, String message) {
		MyWebSocket2 socket = webSocketmap.get(key);
		try {
			socket.session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 群发
	public static void sendMessageToAll(String message) {
		Collection<MyWebSocket2> values = webSocketmap.values();
		for (MyWebSocket2 myWebSocket : values) {
			try {
				myWebSocket.session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount(String userid, String token) {
		if (!webSocketmap.containsKey(token + "_" + userid)) {
			MyWebSocket2.onlineCount++;
		}
	}

	public static synchronized void subOnlineCount() {
		MyWebSocket2.onlineCount--;
	}
}
