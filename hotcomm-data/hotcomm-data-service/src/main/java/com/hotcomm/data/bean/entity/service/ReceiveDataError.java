package com.hotcomm.data.bean.entity.service;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "hk_receivce_data_err")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ReceiveDataError {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "receive_data")
	private String receiveData;

	@Column(name = "error_msg")
	private String errprMsg;

	@Column(name = "restart_load_nums")
	private Integer resLoadNums;

	@Column(name = "restart_send_nums")
	private Integer resSendNums;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "receive_queue_name")
	private String queue;

}
