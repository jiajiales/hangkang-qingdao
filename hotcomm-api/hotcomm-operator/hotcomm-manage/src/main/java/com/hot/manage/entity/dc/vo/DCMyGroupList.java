package com.hot.manage.entity.dc.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
// 我的项目
public class DCMyGroupList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 设备组名
	private String groupname;

	// 正常设备数量
	private Integer normaldev;

	// 报警设备数量
	private Integer alarmdev;

	// 故障设备数量
	private Integer faultdev;
}
