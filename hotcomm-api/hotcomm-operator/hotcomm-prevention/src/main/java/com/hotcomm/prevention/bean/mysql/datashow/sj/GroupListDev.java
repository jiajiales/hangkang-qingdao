package com.hotcomm.prevention.bean.mysql.datashow.sj;

import java.io.Serializable;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GroupListDev implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private double x;
	private double y;
	private Integer state;
	private String site;
	private String picpath;
	private String lat;
	private String lng;
	private String alarmstateName;
	private String contacts;
	private String code;
	private String devnum;

}
