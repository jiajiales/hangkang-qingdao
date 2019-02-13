package com.hotcomm.prevention.bean.mysql.common;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DeviceInsertParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer insertId;
	private String mac;
	private String devnum;
	private String code;
	private double lat;
	private double lng;
	private String addtime;
	private String x;
	private String y;
	private Integer own_id;

	private Integer itempicid;

	private List<Integer> videoid;

	private Integer groupid;
	
	private List<String> pictureUrl;
	private Integer devtype;//设备类型
}
