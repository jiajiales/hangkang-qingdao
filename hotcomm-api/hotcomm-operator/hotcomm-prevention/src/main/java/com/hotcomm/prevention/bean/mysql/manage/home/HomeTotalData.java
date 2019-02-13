package com.hotcomm.prevention.bean.mysql.manage.home;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class HomeTotalData {
	private Integer devTotal;// 总设备数
	private Integer devAddCount;// 新增设备数
	private Integer alarmTotal;// 报警总数
	private Integer alarmAddCount;// 报警新增数量
	private Integer woTotal;// 工单总数
	private Integer woAddCount;// 新增工单数量
	private Integer checkPointTotal;// 巡检点总数
	private Integer checkCount;// 巡检次数

}
