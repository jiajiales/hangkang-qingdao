package com.hotcomm.data.model.rabbitmq.send;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.hotcomm.data.bean.entity.service.MemberVhost;
import com.hotcomm.data.bean.entity.service.Queue;
import com.hotcomm.data.bean.enums.QueueEnum;
import com.hotcomm.data.model.rabbitmq.RabbitQueueManager;
import com.hotcomm.data.model.rabbitmq.RabbitVhostManager;

/**
 * RabbitMQ 启动切入
 * @author Administrator
 *
 */
@Component
public class RabbitMQRunner implements ApplicationRunner {
	
	@Autowired
	private RabbitVhostManager rabbitVhostManager;
	
	@Autowired
	private RabbitQueueManager queueManager;
	
	@Autowired
	SimpleMessageListenerContainer receiveMessageListenerFactory;
	
	/**
	 * 初始化所有 正常合法 VhostBean 工厂对象
	 * 初始化所有正常合法队列 
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		List<MemberVhost> vhosts = rabbitVhostManager.getSafeVhosts();
		List<Integer> memberIds = new ArrayList<>(vhosts.size());
		for (MemberVhost memberVhost : vhosts) {
			String vhostCode = memberVhost.getVhostCode();
			String vhost = memberVhost.getVhost();
			rabbitVhostManager.createVhostSpringAmqpFactory(vhost, vhostCode);
			memberIds.add(memberVhost.getMemberId());
		}
		
		List<Queue> activeQueues = queueManager.getSafeQueues(memberIds);
		List<Queue> downQueues = new ArrayList<>();
		for (Queue queue : activeQueues) {
			Integer memberId = queue.getMemberId();
			MemberVhost vhost = rabbitVhostManager.getVhost(memberId);
			String vhostCode = vhost.getVhostCode();
			String queueName = queue.getQueueName();
			queueManager.createQueueBingVhost(vhostCode, queueName);
			if (queue.getType() == QueueEnum.QueueType.DOWNSTREAM.getValue()) {
				downQueues.add(queue);
			}
		}
		receiveMessageListenerFactory.start();
		
	}
	
}
