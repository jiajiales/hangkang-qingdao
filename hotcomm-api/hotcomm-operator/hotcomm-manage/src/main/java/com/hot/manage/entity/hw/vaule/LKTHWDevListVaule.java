package com.hot.manage.entity.hw.vaule;

import com.hot.manage.entity.PageParam;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTHWDevListVaule extends PageParam {
	private String Starttime;
	private String Endtime;
	private String context;
	private Integer Batterytype;
	private Integer Moduleid;
	private Integer userid;
	private Integer groupid;
}
