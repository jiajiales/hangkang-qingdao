package com.hotcomm.data.bean.entity.sys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "hk_log")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor

public class Log {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "record_user")
	private String recordUser;

	@Column(name = "record_event")
	private String recordEvent;

	@Column(name = "record_time")
	private Date recordTime;

	@Column(name = "record_ip")
	private String recordIp;

	@Column(name = "record_params")
	private String recordparams;

}
