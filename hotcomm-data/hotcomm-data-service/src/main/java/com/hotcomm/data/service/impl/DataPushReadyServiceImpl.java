package com.hotcomm.data.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotcomm.data.bean.entity.service.DataPushReady;
import com.hotcomm.data.bean.enums.DeviceEnum.DeviceStatus;
import com.hotcomm.data.bean.enums.MemberEnum.MemberStatus;
import com.hotcomm.data.bean.enums.MemberVhostEnum.MemberVhostStatus;
import com.hotcomm.data.db.DataPushReadyMapper;
import com.hotcomm.data.service.DataPushReadyService;

@Service
public class DataPushReadyServiceImpl implements DataPushReadyService {

	@Resource
	private DataPushReadyMapper dataPushReadyMapper;
	
	
	@Override
	public void deviceDel(String deviceCode) {
		DataPushReady dataPushReady = new DataPushReady();
		dataPushReady.setDeviceCode(deviceCode);
		dataPushReadyMapper.delete(dataPushReady);
	}

	@Override
	public void deviceDisable(String deviceCode, DeviceStatus status) {
		DataPushReady dataPushReady = new DataPushReady();
		dataPushReady.setDeviceCode(deviceCode);
		dataPushReady.setDeviceStatus(status.getValue());
		dataPushReadyMapper.udpateDeviceStatus(dataPushReady);
	}

	@Override
	public void deviceTypeUpate(String deviceCode, Integer deviceTypeId) {
		DataPushReady dataPushReady = new DataPushReady();
		dataPushReady.setDeviceCode(deviceCode);
		dataPushReady.setDeviceTypeId(deviceTypeId);
		dataPushReadyMapper.updateDeviceType(dataPushReady);
	}

	@Override
	public void deviceGroupUpdate(String deviceCode, Integer deviceGroupId) {
		DataPushReady dataPushReady = new DataPushReady();
		dataPushReady.setDeviceCode(deviceCode);
		dataPushReadyMapper.delete(dataPushReady);
	}
	
	
	@Override
	public void batchDeviceGroupUpdate(Integer deviceGroupId, String[] deviceCode) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("deviceCodes", Arrays.asList(deviceCode));
		dataPushReadyMapper.delRecord(param);
	}

	@Override
	public void deviceUserUpdate(Integer customerId, MemberStatus memberStatus) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("memberId", customerId);
		dataPushReadyMapper.delRecord(param);
	}

	/**
	 * 虚拟机授权变更
	 * @param vhostCode
	 */
	@Override
	public void vhostAuthor(String vhostCode) {
		DataPushReady dataPushReady = new DataPushReady();
		dataPushReady.setVhostCode(vhostCode);
		dataPushReady.setVhostStatus(MemberVhostStatus.AUTHORIZE_PASS.getValue());
		dataPushReadyMapper.updateVhostStatus(dataPushReady);
	}

	/**
	 * 虚拟机信息变更
	 * @param dataPushReady
	 */
	@Override
	public void vhostInfoUpdate(DataPushReady dataPushReady) {
		dataPushReadyMapper.updateVhost(dataPushReady);
	}

	/**
	 * 队列名称变更
	 * @param queueId
	 * @param queueName
	 */
	@Override
	public void queueNameUpdate(Integer queueId, String queueName) {
		DataPushReady dataPushReady = new DataPushReady();
		dataPushReady.setQueueId(queueId);
		dataPushReady.setQueueName(queueName);
		dataPushReadyMapper.updateQueueName(dataPushReady);
	}

	/**
	 * 队列状态变更
	 * @param queueId
	 * @param queueName
	 */
	@Override
	public void queueStatusUpdate(Integer queueId, Integer queueStatus) {
		DataPushReady dataPushReady = new DataPushReady();
		dataPushReady.setQueueId(queueId);
		dataPushReady.setQueueStatus(queueStatus);
		dataPushReadyMapper.updateQueueStatus(dataPushReady);
	}

	/**
	 * 队列有效期限变更
	 * @param queueId
	 * @param queueHoleTime
	 */
	@Override
	public void queueHoleTimeUpdate(Integer queueId, Date queueHoleTime) {
		DataPushReady dataPushReady = new DataPushReady();
		dataPushReady.setQueueId(queueId);
		dataPushReady.setQueueHoleTime(queueHoleTime);
		dataPushReadyMapper.updateQueueHoleTime(dataPushReady);
	}

	/**
	 * 队列发送拦截基数变更
	 * @param queueId
	 * @param queueSendFilterNums
	 */
	@Override
	public void queueSendFilterNumsUpdate(Integer queueId, Long queueSendFilterNums) {
		DataPushReady dataPushReady = new DataPushReady();
		dataPushReady.setQueueId(queueId);
		dataPushReady.setQueueSendFilterNums(queueSendFilterNums);
		dataPushReadyMapper.updateQueueSendFilterNums(dataPushReady);
	}

	@Override
	public void deviceGroupDel(Integer groupId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("groupId",groupId);
		dataPushReadyMapper.delRecord(param);
	}

	@Override
	public void customerDel(Integer memberId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("memberId", memberId);
		dataPushReadyMapper.delRecord(param);
	}
	
	
}
