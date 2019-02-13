package com.hotcomm.prevention.bean.mysql.manage.event;

import java.io.Serializable;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class EventDeviceRele implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// (value = "设备类型")
	private String name;

	// (value = "设备编号")
	private String devnum;

	// (value = "责任人")
	private String contacts;

	// (value = "责任人电话")
	private String telephone;

	// (value = "设备所在地址")
	private String code;

	private String mac;
}
