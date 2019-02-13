package com.hotcomm.prevention.bean.mysql.datashow.sj.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DevStateCountVO {

	private Integer alarmcount;
	private Integer devcount;
	private Integer moduleid;
	private Integer repaircount;
	private Integer failurecount;
	private Integer othercount;
}
