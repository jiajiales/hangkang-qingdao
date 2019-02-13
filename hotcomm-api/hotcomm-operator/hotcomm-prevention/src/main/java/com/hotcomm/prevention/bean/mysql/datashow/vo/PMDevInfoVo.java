package com.hotcomm.prevention.bean.mysql.datashow.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PMDevInfoVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 设备id
	 */
	private Integer id;
	
	/**
	 * 设备x
	 */
	private String x;
	
	/**
	 * 设备y
	 */
	private String y;
	
	/**
	 * 设备状态
	 */
	private Integer state;
	
	/**
	 * 设备责任人
	 */
	private String contacts;
	
	/**
	 * 设备mac地址
	 */
	private String mac;
	
	/**
	 * 设备编号
	 */
	private String devnum;
	
	/**
	 * 设备电量
	 */
	private Integer battery;
	
	/**
	 * 项目组名称
	 */
	private String groupname;
	
	/**
	 * 项目组id
	 */
	private Integer groupid;
}
