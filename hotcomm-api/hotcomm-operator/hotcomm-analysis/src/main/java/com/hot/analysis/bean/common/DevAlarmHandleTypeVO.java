package com.hot.analysis.bean.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DevAlarmHandleTypeVO {

	private Integer alarmcount;
	private Integer allalarmcount;
	private Integer failurecount;
	private Integer moduleid;
	private Integer othercount;

}
