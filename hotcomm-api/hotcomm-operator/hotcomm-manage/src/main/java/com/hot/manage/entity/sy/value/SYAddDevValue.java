package com.hot.manage.entity.sy.value;

import java.util.List;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SYAddDevValue {
	private Integer id;
	private String mac;
	private String code;
	private String addtime;
	private Integer userid;
	private String lng;
	private String lat;
	private Integer state;
	private String addrcode;
	private String lessalarmvalue;
	private String topalarmvalue;
	private double x;
	private double y;
	private String devnum;
	private Integer groupid;
	private Integer itempicid;
	private Integer moduleid;
	private Integer own_id;
	private List<Integer> videoId;

}
