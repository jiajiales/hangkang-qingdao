package com.hotcomm.test;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class DataLoaderTask extends ArrayList<String>  implements Runnable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -2009356860005299636L;
	
	public AtomicInteger loseIndex = new AtomicInteger(0);
	
	public abstract void handleData();
	
	public DataLoaderTask() {
		new Thread(this).start();;
	}
	
	@Override
	public void run() {
		Timer timer = new Timer();
		final DataLoaderTask task = this;
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				if(loseIndex.get()>=3) {
					log.info("监控数据存储 数据目前存有量{}",task.size());
					task.handleData();
				}
			}
		}, 1000*5, 1000*10);
		synchronized (this) {
			while(true) {
				try {
					Thread.sleep(1000*3);			
					int le = loseIndex.incrementAndGet();
					System.out.println("丢失次数"+le);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public Integer pushData(String data) {
		this.add(data);
		this.loseIndex = new AtomicInteger(0);
		return this.size();
	}
	
}
