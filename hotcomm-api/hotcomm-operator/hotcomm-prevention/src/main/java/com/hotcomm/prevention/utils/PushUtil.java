package com.hotcomm.prevention.utils;

import java.util.HashMap;
import java.util.Map;
import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class PushUtil {

	private static final String MasterSecret = "e0be4e90703a3f937b480587";
	private static final String Appkey = "817396a407ca1389360f3fe7";

	/**
	 * 给所有平台的所有用户发通知//如果regids信息不为空则是推送单个用户
	 */
	public static int sendAllsetNotification(String title, String content, String message, String regids,
			long timeToLive) {
		ClientConfig clientConfig = ClientConfig.getInstance();
		clientConfig.setTimeToLive(timeToLive);// 极光推送设置离线时间
		JPushClient jpushClient = new JPushClient(MasterSecret, Appkey, null, clientConfig);
		// JPushClient jpushClient = new JPushClient(masterSecret,
		// appKey);//第一个参数是masterSecret 第二个是appKey
		Map<String, String> extras = new HashMap<String, String>();
		// 添加附加信息
		extras.put("message", message);
		PushPayload payload = buildPushObject_all_alias_alert(title, content, extras, regids);
		try {
			PushResult result = jpushClient.sendPush(payload);
			System.out.println(result);
		} catch (APIConnectionException e) {
			System.out.println(e);
		} catch (APIRequestException e) {
			System.out.println(e);
			System.out.println("Error response from JPush server. Should review and fix it. " + e);
			System.out.println("HTTP Status: " + e.getStatus());
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println("Error Message: " + e.getErrorMessage());
			System.out.println("Msg ID: " + e.getMsgId());
			// 201推送失败
			return 201;
		}
		// 200推送成功
		return 200;
	}

	/**
	 * 发送通知
	 * 
	 * @param message
	 * @param extras
	 * @return
	 * @author WangMeng
	 * @date 2017年1月13日
	 */
	private static PushPayload buildPushObject_all_alias_alert(String title, String content, Map<String, String> extras,
			String regids) {
		if (!TextUtils.isEmpty(regids)) {
			return PushPayload.newBuilder().setPlatform(Platform.all())
					// 设置平台
					.setAudience(Audience.registrationId(regids))
					// 按什么发送 tag alia
					.setNotification(Notification.newBuilder().setAlert(content)
							.addPlatformNotification(
									AndroidNotification.newBuilder().setTitle(title).addExtras(extras).build())
							.addPlatformNotification(IosNotification.newBuilder().addExtras(extras).build()).build())
					// 发送消息
					.setOptions(Options.newBuilder().setApnsProduction(false).build()).build();
		}
		return PushPayload.newBuilder().setPlatform(Platform.all())
				// 设置平台
				.setAudience(Audience.all())
				// 按什么发送 tag alia
				.setNotification(Notification.newBuilder().setAlert(content)
						.addPlatformNotification(
								AndroidNotification.newBuilder().setTitle(title).addExtras(extras).build())
						.addPlatformNotification(IosNotification.newBuilder().incrBadge(1).addExtras(extras).build())
						.build())
				// 发送消息
				.setOptions(Options.newBuilder().setApnsProduction(false).build()).build();
		// 设置ios平台环境 True 表示推送生产环境，False 表示要推送开发环境 默认是开发
	}
}
