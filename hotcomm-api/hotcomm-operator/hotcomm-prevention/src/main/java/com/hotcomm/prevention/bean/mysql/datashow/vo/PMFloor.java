package com.hotcomm.prevention.bean.mysql.datashow.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PMFloor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 楼层id
	 */
	private Integer id;
	
	/**
	 * 楼层图片
	 */
	private String picpath;
	
	/**
	 * 楼层数
	 */
	private String site;
}
