package com.hotcomm.prevention.bean.mysql.datashow.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class GroupDataVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 设备总数
	 */
	private Integer alldevcount;
	/**
	 * 设备正常运行数
	 */
	private Integer normaldevcount;
	/**
	 * 设备报警数
	 */
	private Integer alarmdevcount;
	/**
	 * 设备故障数
	 */
	private Integer faultdevcount;
}
