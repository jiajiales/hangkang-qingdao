package com.hot.manage.entity.sxdl;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SXDLSelectOnId implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer itempicid;

	private String picpath;

	private String site;

	private String devnum;

	private String lat;

	private String lng;

	private double x;

	private double y;

	private Integer groupid;

	private String groupname;

	private String lastalarmtime;

	private String lastValue;

	private String addrcode;

	private String lessalarmvalue;

	private String topalarmvalue;

	private Integer alarmset;

	private String mac;

	private String code;
	
	private Integer own_id;
	
	private String videos;
}
