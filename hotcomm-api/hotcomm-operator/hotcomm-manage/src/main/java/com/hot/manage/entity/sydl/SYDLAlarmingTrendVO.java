package com.hot.manage.entity.sydl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SYDLAlarmingTrendVO {
	
	private Integer maxalarmcount;

	private String date;
	
	private Integer yearAgo;

}
