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

@Table(name = "hk_device_type")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DeviceType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer typeId;

	@Column(name = "type_name")
	private String typeName;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "create_user")
	private String createUser;

	@Column(name = "update_time")
	private Date updateTime;

	@Column(name = "code")
	private String code;

}
