package com.hotcomm.data.db;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hotcomm.data.bean.entity.service.DataQueue;
import com.hotcomm.data.bean.vo.data.DataQueueSendVO;

public interface DataQueueMapper extends tk.mybatis.mapper.common.Mapper<DataQueue> {

	//List<DataQueueSendVO> queryWaitSendDatas(@Param("queueName") String queueName, @Param("limit") Integer limit);

	void updateDataQueueSendStatus(@Param("ucode") String ucode, @Param("sendStatus") Integer sendStatus);
	
	List<DataQueueSendVO> queryWaitSendDatas(@Param("queueId") Integer queueId, @Param("limit") Integer limit);
}
