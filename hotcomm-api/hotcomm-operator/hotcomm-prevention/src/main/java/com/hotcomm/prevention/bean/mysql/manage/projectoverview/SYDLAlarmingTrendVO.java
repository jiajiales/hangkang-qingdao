package com.hotcomm.prevention.bean.mysql.manage.projectoverview;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor

//需要返回的参数
public class SYDLAlarmingTrendVO {
	
	private Integer maxalarmcount;

	private String date;
	
	private Integer yearAgo;

}
