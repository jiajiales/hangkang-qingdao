package com.hotcomm.data.bean.entity.service;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "hk_device_group")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DeviceGroup implements Serializable {

	private static final long serialVersionUID = -8237110099579617870L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "device_group_id")
	private Integer groupId;

	@Column(name = "group_name")
	private String groupName;

	@Column(name = "group_status")
	private Integer groupStatus;

	@Column(name = "device_max")
	private Integer maxNums;

	@Column(name = "remark")
	private String remark;

	@Column(name = "is_delete")
	private Integer isDelete;

}
