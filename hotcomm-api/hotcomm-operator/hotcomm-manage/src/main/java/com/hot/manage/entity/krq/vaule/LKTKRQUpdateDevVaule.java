package com.hot.manage.entity.krq.vaule;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTKRQUpdateDevVaule {
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
	private String ownId;
	private String mac;
	private String videoid;
	private int own_id;
}
