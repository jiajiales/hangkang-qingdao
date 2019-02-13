package com.hot.manage.utils.chat;

import io.rong.RongCloud;

public class RongCloudUtil {
	
	public static final String appKey = "qd46yzrfqiz8f1";
	/**
	 * 此处替换成您的appSecret
	 * */
	public static final String appSecret = "mFMmN6qPUY";
	/**
	 * 自定义api地址
	 * */
	//private static final String api = "http://api.cn.ronghub.com";
	
	RongCloud rongCloud;

	public RongCloudUtil(RongCloud rongCloud) {
		super();
		this.rongCloud = rongCloud;
	}
	
	public static RongCloud getRongCloud(){
		RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
		return rongCloud;
	}
}
