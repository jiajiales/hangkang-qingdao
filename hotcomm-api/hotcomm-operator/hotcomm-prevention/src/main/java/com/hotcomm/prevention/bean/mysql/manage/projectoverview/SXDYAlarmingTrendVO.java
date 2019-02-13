package com.hotcomm.prevention.bean.mysql.manage.projectoverview;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SXDYAlarmingTrendVO {
	
	private Integer maxalarmcount;

	private Integer minalarmcount;

	private String date;
	
	private Integer yearAgo;

}