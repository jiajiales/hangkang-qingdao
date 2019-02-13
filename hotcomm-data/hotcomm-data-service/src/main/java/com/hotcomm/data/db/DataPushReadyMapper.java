package com.hotcomm.data.db;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hotcomm.data.bean.entity.service.DataPushReady;

import tk.mybatis.mapper.common.Mapper;

public interface DataPushReadyMapper extends Mapper<DataPushReady> {
	
	public void udpateDeviceStatus(DataPushReady dataPushReady);
	
	public void updateDeviceType(DataPushReady dataPushReady);
	
	public void udpateDeviceGroup(DataPushReady dataPushReady);
	
	public void batchUpdateDeviceGroup(@Param("deviceGroupId")Integer deviceGroupId,@Param("deviceCodes")List<String> deviceCodes);
	
	public void updateVhostStatus(DataPushReady dataPushReady);
	
	public void updateVhost(DataPushReady dataPushReady);
	
	public void updateQueueName(DataPushReady dataPushReady);
	
	public void updateQueueStatus(DataPushReady dataPushReady);
	
	public void updateQueueHoleTime(DataPushReady dataPushReady);
	
	public void updateQueueSendFilterNums(DataPushReady dataPushReady);
	
	public void delRecord(Map<String, Object> param);
	
}
