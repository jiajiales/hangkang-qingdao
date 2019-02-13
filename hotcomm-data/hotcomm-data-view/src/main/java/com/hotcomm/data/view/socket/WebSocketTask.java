package com.hotcomm.data.view.socket;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.socket.WebSocketSession;

import com.hotcomm.framework.utils.RedisHelper;

@Component
public class WebSocketTask implements HandlerInterceptor {

	RedisHelper redisHelper;

	public WebSocketTask(RedisHelper redisHelper) {
		this.redisHelper = redisHelper;
	}

	@Scheduled(initialDelay = 1000 * 30, fixedRate = 1000 * 10)
	public void loopEchek() {
		Map<String, List<SocketSession>> sockets = WebSocketHandler.socketSessions;
		for(Iterator<Entry<String, List<SocketSession>>> iterator = sockets.entrySet().iterator();iterator.hasNext();) {
			Entry<String, List<SocketSession>> k = iterator.next();
			String sessionId = k.getKey();
			List<SocketSession> socketSessions = k.getValue();
			boolean deleteFlag = false;
			for(Iterator<SocketSession> m = socketSessions.iterator();m.hasNext();) {
				SocketSession socketSession = m.next();
				Integer keeepTime = socketSession.getKeepTime();
				if(keeepTime<30) {
					deleteFlag = true;
					break;
				}else {
					keeepTime-=10;
				}
				socketSession.setKeepTime(keeepTime);
			}
			if(deleteFlag) {
				for(SocketSession s:socketSessions) {
					WebSocketSession webSocketSession = s.getWebSocketSession();
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
				if(RequestContextHolder.getRequestAttributes()!=null) {
					// 获取到当前线程绑定的请求对象
					HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
					// 在redis上删除该token
					redisHelper.del(request.getSession().getAttribute("token").toString());
				}
				WebSocketHandler.removeKeyBySessionId(sessionId); // 销毁SOCKET线程对象
				iterator.remove();
				continue;
			}
			sockets.put(sessionId, socketSessions);
		}
	}
	
}
