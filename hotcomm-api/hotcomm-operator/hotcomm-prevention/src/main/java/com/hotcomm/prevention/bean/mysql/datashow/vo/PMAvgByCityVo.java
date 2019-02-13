package com.hotcomm.prevention.bean.mysql.datashow.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PMAvgByCityVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 区简称
	 */
	private String addvb;

	/**
	 * 区id
	 */
	private Integer areaid;

	/**
	 * 项目id
	 */
	private Integer groupid;

	/**
	 * 项目组名称
	 */
	private String groupname;

	/**
	 * 经度
	 */
	private Double x;

	/**
	 * 纬度
	 */
	private Double y;

	/**
	 * 天气
	 */
	private String weather;

	/**
	 * 气温
	 */
	private String temp;

	/**
	 * 相对湿度
	 */
	private String humidity;

	/**
	 * 空气质量
	 */
	private String quality;

	/**
	 * 区PM平均值
	 */
	private Double pm;

	/**
	 * 市PM平均值
	 */
	private Double pmall;

	/**
	 * 区噪音平均值
	 */
	private Double noiseval;

	/**
	 * 市噪音平均值
	 */
	private Double noisevalall;

	/**
	 * pm等级 1优 2 良 3 报警
	 */
	private Integer pmlevel;

	/**
	 * 噪音等级 1 优 2良 3报警
	 */
	private Integer noisevallevel;
}
