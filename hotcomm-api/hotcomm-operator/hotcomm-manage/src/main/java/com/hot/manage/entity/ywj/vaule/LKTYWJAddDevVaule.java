package com.hot.manage.entity.ywj.vaule;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTYWJAddDevVaule {
	private Integer id;
	private String devnum;
	private String macAddr;
	private String code;
	private String lng;
	private String lat;
	private double x;
	private double y;
	private int maxalarmvalue;
	private int minalarmvalue;
	private String addtime;
	private Integer groupid;
	private Integer itempicid;
	private Integer userid;
	private Integer moduleid;
	private List<Integer> videoid;
	private Integer ownid;
}
