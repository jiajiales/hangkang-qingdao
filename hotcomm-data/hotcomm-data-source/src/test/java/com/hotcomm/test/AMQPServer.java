package com.hotcomm.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class AMQPServer {
	
	static String host;
	static Integer port;
	static String username;
	static String password;
	static String vhost;
	static String queue;
	
	
	public static void main(String[] args) {
		host = "119.29.184.139";
		port = 5672;
		username = "root";
		password = "hotcomm123";
		vhost = "/hotcomm";
		queue = "message.source.data";
		
		  ConnectionFactory factory = new ConnectionFactory();  
		  factory.setHost(host);  
          factory.setPort(port);  
          factory.setUsername(username);  
          factory.setPassword(password);  
          factory.setVirtualHost(vhost);  
          
			List<String> deviceCodes = new ArrayList<>();
			for (int i = 1000; i < 2000; i++) {
				String key = Integer.toBinaryString(i);
				key = "F0" + key;
				deviceCodes.add(key);
			}
          
          int len = 1;
          int threadLen= 1;
           final String [] ds =  deviceCodes.toArray(new String [deviceCodes.size()] );
          ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(200);  
          for(int k=0;k<threadLen;k++) {
        	  scheduledThreadPool.execute(new Runnable() {
				
				@Override
				public void run(){
					  try {
						  Connection connection = factory.newConnection();  
						for(int i=0;i<len;i++) {
						
							  Channel  channel = connection.createChannel();  
							  channel.queueDeclare(queue, true, false, false, null);  
							  String deviceCode = ds[new Random().nextInt(ds.length)];
							  String pushMessage = "{\"deviceCode\":\"${device_code}\",\"deviceId\":3550,\"sourceData\":\"{\\\"id\\\":\\\"302eb3fc-2115-443c-8b42-5b599721fd79\\\",\\\"macAddr\\\":\\\"0000000035000002\\\",\\\"data\\\":\\\"031e1affe9ffe103c5\\\",\\\"recv\\\":\\\"2018-01-23T16:07:34.000Z\\\",\\\"extra\\\":{\\\"commsysType\\\":\\\"lora\\\",\\\"rssi\\\":-72.0,\\\"snr\\\":24.5,\\\"frameCnt\\\":${frameCnt},\\\"gwid\\\":\\\"00001c497bb03023\\\",\\\"gwip\\\":\\\"192.168.1.105\\\",\\\"channel\\\":\\\"471100000\\\",\\\"sf\\\":12.0,\\\"fport\\\":6.0,\\\"devEUI\\\":\\\"FF1010\\\"},\\\"pub\\\":\\\"2018-01-23T16:07:35.107Z\\\"}\",\"source\":1,\"coreData\":\"031e1affe9ffe103c5\",\"parseType\":1}\r\n" + "";
							  pushMessage =  pushMessage.replace("${device_code}", deviceCode).replace("${frameCnt}", new Random().nextInt(1000000)+"");
							  channel.basicPublish("",queue, null, pushMessage.getBytes());  
						      System.out.println("Send Message is:"+pushMessage);       
						  }
					} catch (IOException e) {
						e.printStackTrace();
					} catch (TimeoutException e) {
						e.printStackTrace();
					}
				}
			});
          }
	}
	
	
}
