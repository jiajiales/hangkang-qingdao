package com.hotcomm.prevention.bean.mysql.datashow.sj.vo;

import java.io.Serializable;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DevStateVO implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// (value = "模块ID")
	private Integer moduleID;// 模块ID

	// (value = "年")
	private Integer yaer;// 年

	// (value = "月")
	private Integer mth;// 月

	// (value = "水浸设备总数量")
	private Integer add_devcount;// 水浸设备总数量

	// (value = "水浸报警数量")
	private Integer alarmcount;// 报警数量

	// (value = "故障损坏数量")
	private Integer falseCount;// 故障损坏
}
