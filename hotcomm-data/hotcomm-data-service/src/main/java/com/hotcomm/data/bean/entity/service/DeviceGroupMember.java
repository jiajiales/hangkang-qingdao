package com.hotcomm.data.bean.entity.service;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "hk_device_group_member")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DeviceGroupMember implements Serializable {

	private static final long serialVersionUID = -8237110099579617870L;

	@Column(name = "device_group_id")
	private Integer deviceGroupId;

	@Column(name = "member_id")
	private Integer memberId;

}
