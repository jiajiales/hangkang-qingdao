package com.hotcomm.prevention.bean.mysql.datashow.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class AlarmIndexVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 当前正在处理的警情
	 */
	private Integer handlealarmcount;
	/**
	 * 正在处理的设备报警
	 */
	private Integer unhandlealarmcount;
	/**
	 * 处理平均用时
	 */
	private Double avetimecount;
}
