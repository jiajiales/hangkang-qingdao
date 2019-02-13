package com.hot.parse.entity.common;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DevMsg implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private Integer deviceid;

	private String mac;

	private Integer moduleid;

	private double height;

	private double alarmheight;

	private Integer plusminus;

	private Integer alarmvalue;

	private String lessalarmvalue;

	private String topalarmvalue;

	private Integer alarmset;

	private String lat;

	private String lng;

	private String code;
}
