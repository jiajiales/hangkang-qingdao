package com.hot.manage.entity.jg.vaule;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTJgAddDevVaule {
	private Integer id;
	private String devnum;
	private String macAddr;
	private String coverName;
	private String code;
	private String lng;
	private String lat;
	private double x;
	private double y;
	private String addtime;
	private Integer purpose;
	private Integer own_id;
	private Integer loadbear;
	private Integer groupid;
	private Integer itempicid;
	private Integer userid;
	private Integer moduleid;
	private List<Integer> videoid;
}
