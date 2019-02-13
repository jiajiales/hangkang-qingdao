package com.hot.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Value;

import com.hot.parse.utils.ConvertHelp;
import com.hot.parse.utils.ThreadPool;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Test {
	private static String host = "39.108.239.37";
	private static Integer port = 5672;
	private static String username = "root";
	private static String password = "hotcomm123";
	private static String virtualhost = "/hotcomm_manager";
	private static String queue = "hotcomm.manager.data";

	public static void main(String[] args) throws IOException, TimeoutException {
		// long startTime = System.currentTimeMillis(); // 程序开始记录时间
		// // 创建3个线程的线程池
		// ThreadPool t = ThreadPool.getThreadPool(3);
		// for (int i = 0; i < 10; i++) {
		// t.execute(new Runnable[] { new Task(), new Task(), new Task() });
		// }
		// // t.execute(new Runnable[] { new Task(), new Task(), new Task() });
		// System.out.println(t);
		// t.destroy();// 所有线程都执行完成才destory
		// System.out.println(t);
		// long endTime = System.currentTimeMillis(); // 程序结束记录时间
		// long TotalTime = endTime - startTime; // 总消耗时间
		// System.out.println("耗时：" + TotalTime);
		/* 使用工厂类建立Connection和Channel，并且设置参数 */
		/*
		 * ConnectionFactory factory = new ConnectionFactory();
		 * factory.setHost(host);// MQ的IP factory.setVirtualHost(virtualhost);
		 * factory.setPort(port);// MQ端口 factory.setUsername(username);// MQ用户名
		 * factory.setPassword(password);// MQ密码 Connection connection =
		 * factory.newConnection(); Channel channel =
		 * connection.createChannel();
		 * 
		 * 创建消息队列，并且发送消息 // channel.queueDeclare(queue, false, false, false,
		 * null); String message = "消息2"; // channel.basicPublish("", queue,
		 * null, message.getBytes()); channel.basicPublish("", queue, null,
		 * message.getBytes()); System.out.println("生产了个'" + message + "'");
		 * 关闭连接 channel.close(); connection.close();
		 */

		StringBuffer json = new StringBuffer();
		URL url = new URL("http://112.74.51.248:8085/device/getDeviceByMac");
		// 创建连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestMethod("POST"); // 设置请求方式
		connection.setRequestProperty("Accept", "application/json");// 设置接收数据的格式
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 设置发送数据的格式
		connection.connect();
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		String content = "deviceCode=" + URLEncoder.encode("0000000024000003", "UTF-8");
		out.writeBytes(content);
		out.flush();
		out.close();
		int code = connection.getResponseCode();
		InputStream is = null;
		if (code == 200) { // 发送成功
			is = connection.getInputStream();
			// 输入流作参数传进InputStreamReader并用BufferedReader接受
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine = null;
			// 一直读到空，并设置流编码是UTF8
			while ((inputLine = in.readLine()) != null) {
				json.append(new String(inputLine.getBytes(), "UTF8"));
			}
			System.out.println(json);
			// 记得关闭连接
			in.close();
		} else {
			is = connection.getErrorStream();
		} // 读取响应
	}

	// 任务类
	static class Task implements Runnable {
		private static volatile int i = 1;

		@Override
		public void run() {// 执行任务
			System.out.println("任务 " + (i++) + " 完成");
		}
	}
}
