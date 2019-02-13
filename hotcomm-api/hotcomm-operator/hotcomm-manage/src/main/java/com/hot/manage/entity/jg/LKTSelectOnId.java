package com.hot.manage.entity.jg;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTSelectOnId implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer own_id;
	private String contacts;
	private Integer itempicid;
	private String picpath;
	private String site;
	private String devnum;
	private String mac;
	private String code;
	private String lat;
	private String lng;
	private Integer purpose;
	private Integer loadbear;
	private double x;
	private double y;
	private Integer groupid;
	private String groupname;
	private String videoPath;
	private String videos;
}
