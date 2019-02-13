package com.hot.manage.utils.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import static org.junit.Assert.assertEquals;

import io.rong.RongCloud;
import io.rong.messages.BaseMessage;
import io.rong.models.message.BroadcastMessage;
import io.rong.models.message.SystemMessage;
import io.rong.models.message.TemplateMessage;
import io.rong.models.response.ResponseResult;
import io.rong.util.GsonUtil;

public class ChatSystemUtil {


	public ResponseResult testSendSystem(String[] targetIds,String content,Integer type,Object obj) throws Exception {
		String objecttype = null;
		switch (type) {
		case 1:
			objecttype = "RC:TxtMsg";
			break;
		case 2:
			objecttype = "RC:ImgMsg";
			break;
		case 3:
			objecttype = "RC:VcMsg";
			break;
		}
		RongCloud rongCloud = RongCloudUtil.getRongCloud();
		SystemMessage systemMessage = new SystemMessage()
					.setSenderId("usetId")
					.setTargetId(targetIds)
					.setObjectName(objecttype)
					.setContent((BaseMessage) obj)
					.setIsPersisted(0)			//是否在融云服务器存储, 0: 不存储, 1: 存储, 默认: 1 
					.setIsCounted(0)			// 	在各端是否计数, 0: 不计数, 1: 计数, 默认: 1
					.setContentAvailable(0);
		ResponseResult result = rongCloud.message.system.send(systemMessage);
		System.out.println("publishSystem:  " + result.toString());
		assertEquals("200",result.getCode().toString());
		return result;
	}

	/**
	 * 发送系统模板消息方法（一个用户向一个或多个用户发送系统消息，单条消息最大 128k， 会话类型为 SYSTEM.每秒钟最多发送 100
	 * 条消息，每次最多同时向 100 人发送，如： 一次发送 100 人时，示为 100 条消息。）
	 */
	public void testSendSystemTemplate() throws Exception {
		RongCloud rongCloud = RongCloudUtil.getRongCloud();
		Reader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(ChatSystemUtil.class.getClassLoader()
					.getResourceAsStream("jsonsource/message/TemplateMessage.json")));
			TemplateMessage template = (TemplateMessage) GsonUtil.fromJson(reader, TemplateMessage.class);
			ResponseResult result = rongCloud.message.system.sendTemplate(template);
			System.out.println("sendSystemTemplate:  " + result.toString());
			assertEquals("200", result.getCode().toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				reader.close();
			}
		}
	}

	/**
	 * 广播消息测试
	 */
	public ResponseResult testSendBroadcast(String content, Integer type, Object obj) throws Exception {
		String objecttype = null;
		switch (type) {
		case 1:
			objecttype = "RC:TxtMsg";
			break;
		case 2:
			objecttype = "RC:ImgMsg";
			break;
		case 3:
			objecttype = "RC:VcMsg";
			break;
		}
		RongCloud rongCloud = RongCloudUtil.getRongCloud();
		BroadcastMessage message = new BroadcastMessage().setSenderId("Hji8yh76").setObjectName(objecttype)
				.setContent((BaseMessage) obj);
		ResponseResult result = rongCloud.message.system.broadcast(message);
		System.out.println("send broadcast:  " + result.toString());

		assertEquals("200", result.getCode().toString());
		return result;
	}
}
