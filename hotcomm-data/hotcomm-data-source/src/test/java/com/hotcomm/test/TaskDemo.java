package com.hotcomm.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskDemo {
	
	public static void main(String[] args) throws InterruptedException {
		DataLoaderTask task = new DataLoaderTask() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1586317322953844150L;

			@Override
			public void handleData() {
				for(String s:this) {
					log.info("数据存储内容---->{}",s);
				}
			}
		};
		
		Thread.sleep(1000*7);
		
		task.pushData("FFF00001010101");
		
	}
	
}
