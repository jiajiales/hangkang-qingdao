package com.hotcomm.data.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hotcomm.data.service.QueueService;

@Component
public class RabbitMQTask {
	
	@Autowired
	QueueService queueService;

	// 定时处理过期队列
	@Scheduled(initialDelay = 1000 * 30, fixedRate = 1000 * 60 * 5)
	public void execuhandleExpiereQueue() {
		this.queueService.handleExpireQueue();
	}

	@Scheduled(initialDelay = 1000 * 30, fixedRate = 1000 * 60)
	public void executeLoadWaitSendCodeKyes() {
		this.queueService.loopQueueSendFilterInterval();
	}

}
