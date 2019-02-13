package com.hotcomm.data.utils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.hotcomm.framework.utils.SnowflakeIdWorker;

public class SerialNumberUtils {
	
	private final static String DEVICE_PREX = "d";
	
	private static SnowflakeIdWorker deviceWork = new SnowflakeIdWorker(0, 0);
	
	public static String buildDeviceSequeueNumber() {
		Lock lock = new ReentrantLock(); 
		String result = null;
		try {
			lock.lock();
			result= DEVICE_PREX.concat(Long.toHexString(deviceWork.nextId()));
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		for(int i=0;i<=1000;i++) {
			System.out.println(buildDeviceSequeueNumber());
		}
	}
	
}
