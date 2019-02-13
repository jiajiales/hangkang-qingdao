package com.hot.manage.entity.hw.vaule;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTHWUpdateDevVaule {
	private Integer devid;
	private Integer moduleid;
	private String devnum;
	private String lat;
	private String lng;
	private double x;
	private double y;
	private Integer groupid;
	private Integer itempicid;
	private Integer userid;
	private String code;
	private String ownId;
	private String videoid;
}
