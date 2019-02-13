package com.hot.manage.entity.common.event;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AppEventList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer eventid;
	private String state_name;
	private Integer state;
	private String addtime;
	private String devnum;
	private String code;
	private String lat;
	private String lng;
	private Integer moduleid;
	private String modulename;
}
