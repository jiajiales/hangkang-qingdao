package com.hotcomm.prevention.bean.mysql.datashow.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class DevInformVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 设备地址
	 */
	private String code;
	/**
	 * 报警时间
	 */
	private String addtime;
	/**
	 * 报警状态
	 */
	private Integer handlestate;
}
