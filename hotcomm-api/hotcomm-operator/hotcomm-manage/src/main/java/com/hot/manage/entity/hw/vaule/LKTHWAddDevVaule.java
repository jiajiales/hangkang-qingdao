package com.hot.manage.entity.hw.vaule;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTHWAddDevVaule {
	private Integer id;
	private String mac;
	private String code;
	private String lng;
	private String lat;
	private Integer state;
	private String addtime;
	private Integer userid;
	private double x;
	private double y;
	private String devnum;
	private Integer groupid;
	private Integer itempicid;
	private Integer moduleid;
	private Integer own_id;
	private List<Integer> videoId;
}
