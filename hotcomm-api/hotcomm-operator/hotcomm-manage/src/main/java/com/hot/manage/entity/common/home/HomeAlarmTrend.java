package com.hot.manage.entity.common.home;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class HomeAlarmTrend {
	private Integer moduleid;
	private String modulename;
	private String addtime;// 时间
	private Integer alarmcount;// 报警数量

}
