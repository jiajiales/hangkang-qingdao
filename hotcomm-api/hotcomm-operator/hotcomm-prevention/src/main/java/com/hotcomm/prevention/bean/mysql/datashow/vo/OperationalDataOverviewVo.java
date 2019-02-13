package com.hotcomm.prevention.bean.mysql.datashow.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class OperationalDataOverviewVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 警情数
	 */
	private Integer alertcount;
	/**
	 * 火灾数
	 */
	private Integer firecount;
	/**
	 * 火灾隐患
	 */
	private Integer firehazardcount;
	/**
	 * 执法数量
	 */
	private Integer executecount;
	/**
	 * 报警处理平均用时  m/time
	 */
	private Double avetimecount;
}
