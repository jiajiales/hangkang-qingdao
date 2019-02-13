package com.hot.manage.entity.common.workorder;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTWorkDevList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//(value = "设备id")
	private Integer id;

	//(value = "设备类型")
	private String device;

	//(value = "设备mac")
	private String mac;

	//(value = "设备devnum")
	private String devnum;
	
	//(value = "设备地址")
	private String code;

	//(value = "纬度")
	private String lat;

	//(value = "经度")
	private String lng;

	//(vaule = "关联的报警或事件")
	private List<LKTWorkAllAlarm> son;
}
