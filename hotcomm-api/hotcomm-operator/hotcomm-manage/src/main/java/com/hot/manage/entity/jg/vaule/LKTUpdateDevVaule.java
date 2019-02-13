package com.hot.manage.entity.jg.vaule;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTUpdateDevVaule {
	private Integer devid;
	private Integer moduleid;
	private String devnum;
	private String lat;
	private String lng;
	private double x;
	private double y;
	private Integer groupid;
	private Integer userid;
	private Integer itempicid;
	private Integer purpose;
	private Integer loadbear;
	private String code;
	private Integer own_id;
	private List<Integer> videoid;
}
