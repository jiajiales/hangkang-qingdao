package com.hotcomm.data.service;

import java.util.List;

import com.hotcomm.data.bean.entity.service.MemberVhost;
import com.hotcomm.data.bean.entity.service.Queue;
import com.hotcomm.data.bean.enums.QueueEnum.QueueType;
import com.hotcomm.data.bean.params.service.queue.QueuePageParams;
import com.hotcomm.data.bean.params.service.queue.QueueParams;
import com.hotcomm.data.bean.vo.queue.QueuePageVO;
import com.hotcomm.data.bean.vo.queue.QueueVO;
import com.hotcomm.data.model.rabbitmq.common.QueueCustomer;
import com.hotcomm.data.service.common.CommFunService;
import com.hotcomm.data.service.common.PageService;
import com.hotcomm.framework.web.exception.HKException;

public interface QueueService extends CommFunService<QueueParams, QueueVO, Integer>, PageService<QueuePageParams, QueuePageVO> {

	void queueRun(Integer queueId) throws HKException;

	void queuePause(Integer queueId) throws HKException;

	void queueRename(QueueParams params) throws HKException;

	void updateHoleTime(QueueParams params) throws HKException;

	void updateFilternums(QueueParams params) throws HKException;

	List<Queue> getVhostInnerQueue(List<MemberVhost> memberVhosts) throws HKException;

	List<Queue> getActiveQueues(List<Integer> memberIds);

	QueueCustomer getQueueRabbitView(String queueName) throws HKException;

	QueueVO getQueueView(QueueParams params) throws HKException;

	void saveQueue(String queueName, QueueType type, Integer memberId) throws HKException;

	Long queryQueueWatiSendNums(Integer memberId) throws HKException;

	String getQueueName(Integer memberId, QueueType type) throws HKException;

	void updateQueueName(Integer memberId, QueueType type, String queueName) throws HKException;

	void stopQueue(Integer memberId) throws HKException;
	
	/**
	 * 检查队列过期 处理
	 */
	void handleExpireQueue();
	
	void loopQueueSendFilterInterval();
}
