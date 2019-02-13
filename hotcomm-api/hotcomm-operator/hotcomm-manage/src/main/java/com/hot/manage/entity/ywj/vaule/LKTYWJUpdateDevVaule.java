package com.hot.manage.entity.ywj.vaule;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTYWJUpdateDevVaule {
	private Integer ownid;
	private Integer devid;
	private Integer moduleid;
	private String devnum;
	private String code;
	private String lat;
	private String lng;
	private double x;
	private double y;
	private int maxalarmvalue;
	private int minalarmvalue;
	private Integer groupid;
	private Integer itempicid;
	private Integer userid;
	private String videos;//多个摄像头
	private String mac;
}
