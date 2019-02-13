package com.hot.manage.entity.sxdl.value;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SXDLUpdateDevVaule {
	private Integer devid;
	private Integer moduleid;
	private String devnum;
	private String code;
	private String lat;
	private String lng;
	private double x;
	private double y;
	private Integer groupid;
	private Integer itempicid;
	private Integer userid;
	private Integer ownId;
	private String mac;
	private String videoid;
	private Integer lessalarmvalue;
	private Integer topalarmvalue;
}
