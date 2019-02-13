package com.hotcomm.prevention.bean.mysql.datashow.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PMMonitoringPoint implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 组id
	 */
	private Integer groupid;
	/**
	 * 组名称
	 */
	private String groupname;
	/**
	 * pm2.5值
	 */
	private Double pm25;
	/**
	 * 噪音值
	 */
	private Double noiseval;
	
	/**
	 * 环境质量
	 */
	private String level;
}
