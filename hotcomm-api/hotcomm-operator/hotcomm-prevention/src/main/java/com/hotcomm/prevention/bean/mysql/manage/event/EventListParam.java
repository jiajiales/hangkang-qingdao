package com.hotcomm.prevention.bean.mysql.manage.event;

import java.io.Serializable;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class EventListParam implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	// 处理状态
	private Integer handlestate;

	// 报警状态
	private Integer stateid;

	// 搜索内容(设备编号/设备地址)
	private String message;

	// 结束时间
	private String endTime;

	// 开始时间
	private String startTime;
}
