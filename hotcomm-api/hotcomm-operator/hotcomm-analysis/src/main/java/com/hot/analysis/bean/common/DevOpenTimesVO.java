package com.hot.analysis.bean.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DevOpenTimesVO {

	private Integer moduleid;
	private String dateTime;
	private Integer openCount;

}
