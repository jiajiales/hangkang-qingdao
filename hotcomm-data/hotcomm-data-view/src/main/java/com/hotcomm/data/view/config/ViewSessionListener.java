package com.hotcomm.data.view.config;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import lombok.extern.slf4j.Slf4j;

@WebListener
@Slf4j
public class ViewSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		int timeout = 60*60*5;
		se.getSession().setMaxInactiveInterval(timeout);
		log.info("session过期时间{}秒",timeout);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		
	}

	



}
