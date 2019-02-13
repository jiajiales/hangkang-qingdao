package com.hotcomm.data.view.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurationSupport implements WebSocketConfigurer {

	@Autowired
	WebSocketHandler webSocketHandler;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(webSocketHandler, "/ws/server").addInterceptors(new WebSocketHandlerInterceptor());
		registry.addHandler(webSocketHandler, "/sockjs/server").addInterceptors(new WebSocketHandlerInterceptor()).withSockJS();
	}

}