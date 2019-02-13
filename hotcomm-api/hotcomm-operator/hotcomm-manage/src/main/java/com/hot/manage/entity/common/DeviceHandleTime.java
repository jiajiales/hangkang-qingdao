package com.hot.manage.entity.common;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DeviceHandleTime implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer alarmid;
	private String addtime;
	private Integer handlestate;
	private String name;
	private String handleTime;
	private String state_name;
	private String handlename;
	
	
	
}
