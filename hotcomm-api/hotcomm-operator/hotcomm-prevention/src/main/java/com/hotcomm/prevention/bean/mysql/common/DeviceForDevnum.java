package com.hotcomm.prevention.bean.mysql.common;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DeviceForDevnum implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer moduleid;
	private String name;
	private String devnum;
	private String devtype;
	private String mac;
}
