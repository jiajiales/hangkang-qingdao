package com.hotcomm.data.view.socket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.hotcomm.data.view.bean.Constant;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

	public static final Map<String, List<SocketSession>> socketSessions = new ConcurrentHashMap<>();

	public static void removeKeyBySessionId(String sessionId) {
		if (socketSessions.containsKey(sessionId)) {
			for(SocketSession m:socketSessions.get(sessionId)) {
				WebSocketSession webSocketSession = m.getWebSocketSession();
				try {
					String message = "sessionKill";
					if (webSocketSession.isOpen()) {
						webSocketSession.sendMessage(new SocketMessage("message", message).toTextMessage());
						webSocketSession.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			log.info("用户{}退出,同时将SOCKET线程对象销毁",sessionId);
			socketSessions.remove(sessionId);
		}
	}
	
	public static void updateSessionKeeps(String sessionId) {
		if (socketSessions.containsKey(sessionId)) {
			 List<SocketSession> sockets = socketSessions.get(sessionId);
			for(SocketSession m:sockets) {
				m.setKeepTime(Constant.KEEP_TIME);
			}
			log.info("用户{}活动,保持心跳,将生命周期延长",sessionId);
		}
			
	}

/*	static {
		Timer t = new Timer();
		final int evel = 5;

		t.schedule(new TimerTask() {
			@Override
			public void run() {
				for (String key : socketSessions.keySet()) {
					List<SocketSession> sockets  = socketSessions.get(key);
					for(Iterator<SocketSession> iterator = sockets.iterator();iterator.hasNext();) {
						SocketSession socketSession = iterator.next();
						Integer keepTimes = socketSession.getKeepTime();
						if (keepTimes > 30) {
							keepTimes -= evel;
							socketSession.setKeepTime(keepTimes);
							socketSessions.put(key, socketSession);
						} else {
							log.info("即将被销毁SessionID:{}",key);
							WebSocketSession webSocketSession = socketSession.getWebSocketSession();
							String message = "sessionKill";
							try {
								if (webSocketSession.isOpen()) {
									webSocketSession.sendMessage(new SocketMessage("message", message).toTextMessage());
									webSocketSession.close();
								}
								socketSessions.remove(key);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}

				}
			}
		}, 2000, 1000 * evel);
	}*/

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
 		String sessionId = (String) session.getAttributes().get("MEMBER_SESSIONID");
		Integer sessionIndex = (Integer) session.getAttributes().get("SESSION_KEEP_INDEX");
		SocketSession socketSession = new SocketSession(sessionId, sessionIndex, session);
		if(socketSessions.containsKey(sessionId)) {
			List<SocketSession> sockets =  socketSessions.get(sessionId);
			sockets.add(socketSession);
			socketSessions.put(sessionId, sockets);
		}else {
			List<SocketSession> sockets = new ArrayList<>();
			sockets.add(socketSession);
			socketSessions.put(sessionId, sockets);
		}
		log.info("用户{}登入,同时将SOCKET线程对象创建和保存",sessionId);
	}

}
