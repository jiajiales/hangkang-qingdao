package com.hotcomm.data.model.rabbitmq;

import java.util.List;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotcomm.data.model.rabbitmq.common.QueueCustomer;
import com.hotcomm.data.service.QueueService;
import com.hotcomm.framework.utils.SpringUtil;
import com.hotcomm.framework.web.exception.HKException;

@Component
public class RabbitQueueManager {

	@Autowired
	private RabbitRestTool tools;

	@Autowired
	private QueueService queueService;

	
	public List<com.hotcomm.data.bean.entity.service.Queue> getSafeQueues(List<Integer> memberIds) {
		return this.queueService.getActiveQueues(memberIds);
	}
	
	public void createQueueBingVhost(String factoryCode, String queueName) {
		ConnectionFactory factory = (ConnectionFactory) SpringUtil.getBean(factoryCode);
		AmqpAdmin admin = new RabbitAdmin(factory);
		admin.declareQueue(new Queue(queueName));
	}

	public void updateQueueBingVhost(String factoryCode, String oldQueueName, String newQueueName) throws HKException {
		removeQueueBingVhost(factoryCode, oldQueueName);
		createQueueBingVhost(factoryCode, newQueueName);
	}

	public void removeQueueBingVhost(String factoryCode, String queueName) throws HKException {
		ConnectionFactory factory = (ConnectionFactory) SpringUtil.getBean(factoryCode);
		String vhost = factory.getVirtualHost();
		QueueCustomer customer = tools.getQueueMsg(vhost, queueName);
		Integer messages = customer.getMessages();
		if (messages > 0)
			throw new HKException("-1", "当前队列中尚有数据未发送完,此刻不允许删除");
		AmqpAdmin admin = new RabbitAdmin(factory);
		admin.deleteQueue(queueName, false, true);
	}

}
