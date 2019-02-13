package com.hot.analysis.bean.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DevInfo {

	private Integer moduleid;
	private Integer day;
	private String code;
	private String devnum;
	private String addtime;
	private Integer alarmcount;
	private Integer failurecount;
	private Integer othercount;
	private Integer opencount;

}
