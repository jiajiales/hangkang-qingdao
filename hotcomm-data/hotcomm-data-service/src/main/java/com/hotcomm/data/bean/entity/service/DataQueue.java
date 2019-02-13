package com.hotcomm.data.bean.entity.service;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "hk_data_queue")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DataQueue {

	@Column(name = "ucode")
	private String ucode;

	@Column(name = "data_id")
	private Long dataId;

	@Column(name = "send_status")
	private Integer sendStatus;

	@Column(name = "queue_key")
	private String queueName;

	@Column(name = "member_id")
	private Integer memberId;

	@Column(name = "queue_id")
	private Integer queueId;

}
