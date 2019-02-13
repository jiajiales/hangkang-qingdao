package com.hot.manage.entity.sxdy.value;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SXDYAddDevVaule {
	private Integer id;
	private String devnum;
	private String macAddr;
	private String code;
	private String lng;
	private String lat;
	private double x;
	private double y;
	private String addrcode;
	private String addtime;
	private Integer groupid;
	private Integer itempicid;
	private Integer userid;
	private Integer moduleid;
	private Integer own_id;
	private String videoid;
	private Integer lessalarmvalue;
	private Integer topalarmvalue;
}
