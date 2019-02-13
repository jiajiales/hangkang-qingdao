package com.hotcomm.data.service;

import java.util.Date;

import com.hotcomm.data.bean.entity.service.DataPushReady;
import com.hotcomm.data.bean.enums.DeviceEnum.DeviceStatus;
import com.hotcomm.data.bean.enums.MemberEnum.MemberStatus;

public interface DataPushReadyService {
	
	/**
	 * 设备删除
	 * @param deviceCode
	 */
	public void deviceDel(String deviceCode);
	
	/**
	 * 设备状态变更
	 * @param deviceCode
	 */
	public void deviceDisable(String deviceCode, DeviceStatus status);

	/**
	 * 设备类型变更
	 * @param deviceCode
	 * @param deviceTypeId
	 */
	public void deviceTypeUpate(String deviceCode,Integer deviceTypeId);

	/**
	 * 设备组变更
	 * @param deviceCode
	 * @param deviceGroupId
	 */
	public void deviceGroupUpdate(String deviceCode,Integer deviceGroupId);
	
	/**
	 *  批量变更设备组
	 * @param deviceGroupId
	 * @param deviceCode
	 */
	public void batchDeviceGroupUpdate(Integer deviceGroupId,String [] deviceCode);
	
	/**
	 * 设备组删除
	 * @param groupId
	 */
	public void deviceGroupDel(Integer groupId);
	
	/**
	 * 用户删除
	 * @param memberId
	 */
	public void customerDel(Integer memberId);
	
	/**
	 * 虚拟机授权变更
	 * @param vhostCode
	 */
	public void vhostAuthor(String vhostCode);

	/**
	 * 设备关联用户状态变更
	 * @param deviceCode
	 * @param customerId
	 * @param memberStatus
	 */
	public void deviceUserUpdate(Integer customerId, MemberStatus memberStatus);

	/**
	 * 虚拟机信息变更
	 * @param dataPushReady
	 */
	public void vhostInfoUpdate(DataPushReady dataPushReady);

	/**
	 * 队列名称变更
	 * @param queueId
	 * @param newQueueName
	 */
	public void queueNameUpdate(Integer queueId, String newQueueName);

	/**
	 * 队列状态变更
	 * @param queueId
	 * @param queueStatus
	 */
	public void queueStatusUpdate(Integer queueId, Integer queueStatus);

	/**
	 * 队列有效期变更
	 * @param queueId
	 * @param holeTime
	 */
	public void queueHoleTimeUpdate(Integer queueId, Date queueHoleTime);

	/**
	 * 队列发送拦截基数变更
	 * @param queueId
	 * @param queueSendFilterNums
	 */
	public void queueSendFilterNumsUpdate(Integer queueId, Long queueSendFilterNums);

}
