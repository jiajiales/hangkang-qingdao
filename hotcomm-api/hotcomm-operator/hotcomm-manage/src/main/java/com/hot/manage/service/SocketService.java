package com.hot.manage.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hot.manage.entity.system.TMessageLog;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.common.LoginService;
import com.hot.manage.service.system.TMessageLogService;
import com.hot.manage.utils.ConverUtil;
import com.hot.manage.utils.JSONUtil;
import com.hot.manage.websocket.MyWebSocket;

@Service
public class SocketService {
	@Autowired
	private TMessageLogService messageLogService;
	@Autowired
	LoginService loginService;

	// 对指定用户推送消息
	public void sendMessageToOne(TMessageLog message) throws MyException {
		List<String> list = new ArrayList<>();
		String[] ss = message.getReceiverid().split(",");
		for (String s : ss) {
			if (!list.contains(s)) {
				list.add(s);
			}
		}	
		ConcurrentMap<String, MyWebSocket> websocketmap = MyWebSocket.webSocketmap;
		for (String s : list) {
			String queryLastToken = loginService.queryLastToken(Integer.parseInt(s));
			boolean bool = websocketmap.keySet().contains(queryLastToken+"_"+s);
			if (bool) {
				message.setReceiverid(s);
				message.setState(1);
				message.setReceivetime(ConverUtil.timeForString(new Date()));
				String sss="{\"systate\":0,\"message\":"+JSONUtil.toJson(message.getMessage())+"}";
				MyWebSocket.sendMessage(s,queryLastToken,sss);
				messageLogService.insertOne(message);
			} else {
				message.setReceiverid(s);
				messageLogService.insertOne(message);
			}
		}
	}

	// 广播信息
	public void sendMessageToAll(TMessageLog message) throws MyException {
		String sss="{\"systate\":0,\"message\":"+JSONUtil.toJson(message.getMessage())+"}";
		MyWebSocket.sendMessageToAll(sss);
		message.setState(1);
		messageLogService.insertOne(message);
	}
	
	/**
	 * 推送到登陆页面
	 * @throws MyException
	 */
	@SuppressWarnings("static-access")
	public void sendLogin(String token,Integer userid)throws MyException{
		ConcurrentMap<String, MyWebSocket> websocketmap = MyWebSocket.webSocketmap;
		//当前登陆用户id 查上一次的token,同websocketmap比较，如果包含，说明二次登陆，（推送消息到一次客户端）强制一次登陆下线
		String queryToken = loginService.queryToken(userid);
		MyWebSocket myWebSocket = websocketmap.get(queryToken+"_"+userid);
		if (myWebSocket!=null) {
			myWebSocket.sendMessage(String.valueOf(userid),queryToken,"{\"systate\":1,\"message\":\"你的账号已在其他地方登录\"}");
		}
	}

}
