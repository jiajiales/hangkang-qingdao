package com.hotcomm.prevention.bean.mysql.datashow.sj.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DevFailureRateVO {

	private Integer moduleID;
	private Integer date;
	private Integer devCount;
	private Integer repairCount;//当月维修过的设备数量
	private Integer currentMonthBrokenCount;//当月累积损坏的设备数量
	private Integer cumulativeBrokenCountAfterRepair;//当月进行维修后剩余的损坏设备数量

}
