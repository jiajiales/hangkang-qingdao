package com.hotcomm.data.bean.entity.service;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "hk_device_push_message")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DataPushReady {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="device_code")
	private String deviceCode;
	
	@Column(name="device_status")
	private Integer deviceStatus;
	
	@Column(name="device_typeId")
	private Integer deviceTypeId;
	
	@Column(name="device_typeCode")
	private String deviceTypeCode;
	
	@Column(name="device_groupId")
	private Integer deviceGroupId;
	
	@Column(name="member_id")
	private Integer memberId;
	
	@Column(name="member_status")
	private Integer memberStatus;
	
	@Column(name="vhost_name")
	private String vhostName;
		
	@Column(name="vhost_code")
	private String vhostCode;
	
	@Column(name= "vhost_status")
	private Integer vhostStatus;
	
	@Column(name="queue_name")
	private String queueName;
	
	@Column(name="queue_status")
	private Integer queueStatus;
	
	@Column(name="queue_id")
	private Integer queueId;
	
	@Column(name="queue_holeTime")
	private Date queueHoleTime;
	
	@Column(name="queue_sendFilterNums")
	private Long queueSendFilterNums;

	public DataPushReady(String deviceCode) {
		super();
		this.deviceCode = deviceCode;
	}
	
}
