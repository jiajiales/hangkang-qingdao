package com.hotcomm.prevention.bean.mysql.manage.projectoverview;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SXDLAlarmingTrendVO {

	private Integer maxalarmcount;

	private String date;

	private Integer yearAgo;

}