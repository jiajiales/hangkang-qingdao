package com.hotcomm.data.db;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hotcomm.data.bean.entity.service.Queue;
import com.hotcomm.data.bean.params.service.queue.QueuePageParams;
import com.hotcomm.data.bean.params.service.queue.QueueParams;
import com.hotcomm.data.bean.params.service.queue.QueueQueryParams;
import com.hotcomm.data.bean.vo.queue.QueuePageVO;
import com.hotcomm.data.bean.vo.queue.QueueVO;

import tk.mybatis.mapper.common.Mapper;

public interface QueueMapper extends Mapper<Queue> {

	Page<QueuePageVO> queryPage(@Param("pageParams") QueuePageParams pageParams);

	QueueVO getQueueParamsByQueueId(@Param("queueId") Integer queueId);

	Integer getWaitSendNumByQueueId(@Param("queueId") Integer queueId);

	List<Queue> queryQueues(@Param("params") QueueQueryParams params);

	void updateQueueDataParams(@Param("queueName") String queueName);

	List<Queue> listActive(@Param("memberIds") List<Integer> memberIds, @Param("type") Integer queueType);

	void updateQueueWaitSendNums(@Param("queueName") String queueName);

	Long getWaitSendNums(@Param("memberId") Integer memberId);

	void updateQueueName(Queue queue);

	QueueVO getQueueView(@Param("params") QueueParams params);

	void stopQueue(Integer memberId);

	List<Queue> getExpireQueue();

	List<Map<String, Object>> getRuningQueue();

	void delQueueByCustomerId(@Param("customerId") Integer customerId);

}
