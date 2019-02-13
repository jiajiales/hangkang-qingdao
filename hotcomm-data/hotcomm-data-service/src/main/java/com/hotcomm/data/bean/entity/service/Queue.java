package com.hotcomm.data.bean.entity.service;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "hk_queue")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Queue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer queueId;

	@Column(name = "queue_name")
	private String queueName;

	@Column(name = "type")
	private Integer type;

	@Column(name = "member_id")
	private Integer memberId;

	@Column(name = "hole_time")
	private Date holeTime;

	@Column(name = "queue_status")
	private Integer queueStatus;

	@Column(name = "receive_data_num")
	private Long receiveDataNum;

	@Column(name = "send_filter_interval")
	private Long sendFilterInterval;

	@Column(name = "send_data_num")
	private Long sendDataNum;

	@Column(name = "wait_send_num")
	private Long waitSendNum;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "update_time")
	private Date updateTime;

	@Column(name = "create_user")
	private String createUser;

	@Column(name = "remark")
	private String remark;

	@Column(name = "is_delete")
	private Integer isDelete;

	public Queue(Integer memberId) {
		super();
		this.memberId = memberId;
	}

	public Queue(Integer queueId, String queueName, Integer type) {
		super();
		this.queueId = queueId;
		this.queueName = queueName;
		this.type = type;
	}

	public Queue(String queueName) {
		super();
		this.queueName = queueName;
	}

}
