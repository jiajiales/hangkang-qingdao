package com.hotcomm.data.view.socket;

import org.springframework.web.socket.WebSocketSession;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class SocketSession {
	
	private String sessionId;
	private Integer keepTime;
	private WebSocketSession  webSocketSession;
	public SocketSession(String sessionId, Integer keepTime, WebSocketSession webSocketSession) {
		super();
		this.sessionId = sessionId;
		this.keepTime = keepTime;
		this.webSocketSession = webSocketSession;
	}
	public SocketSession(String sessionId, Integer keepTime) {
		super();
		this.sessionId = sessionId;
		this.keepTime = keepTime;
	}
	
	
}
