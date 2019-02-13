package com.hotcomm.data.bean.entity.service;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "hk_device")
@Data
@NoArgsConstructor
public class Device implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "device_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer deviceId;

	@Column(name = "device_code")
	private String code;

	@Column(name = "device_type_id")
	private Integer type;

	@Column(name = "descript")
	private String desc;

	@Column(name = "device_group_id")
	private Integer groupId;

	@Column(name = "receive_num")
	private Integer receiveNum;

	@Column(name = "is_delete")
	private Integer isDelete;

	@Column(name = "device_status")
	private Integer status;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "create_user")
	private String createUser;

	@Column(name = "update_time")
	private Date updateTime;

	@Column(name = "device_protocol")
	private Integer protocol;

	@Column(name = "iot_tech")
	private Integer iotTech;

	@Column(name = "lora_data")
	private String lora;

	public Device(String code) {
		super();
		this.code = code;
	}

	@Override
	public boolean equals(Object obj) {
		Device s = (Device) obj;
		return code.equals(s.code);
	}

	@Override
	public int hashCode() {
		String in = code;
		return in.hashCode();
	}

	public Device(Integer deviceId, String code) {
		super();
		this.deviceId = deviceId;
		this.code = code;
	}

}
