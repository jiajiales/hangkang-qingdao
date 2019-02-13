package com.hotcomm.prevention.bean.mysql.datashow.sj;

import java.io.Serializable;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DevState implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;


	// (value = "设备类型")
	private Integer moduleID;// 设备类型
	// (value = "日期")
	private Integer yearStr;// 日期
	// (value = "报警总数")
	private Integer allAlarmCount;// 报警总数
	// (value = "水浸报警")
	private Integer alarmCount;// 水浸报警
	// (value = "设备损坏")
	private Integer failureCount;// 设备损坏
	// (value = "其他原因")
	private Integer otherCount;// 其他原因
}
