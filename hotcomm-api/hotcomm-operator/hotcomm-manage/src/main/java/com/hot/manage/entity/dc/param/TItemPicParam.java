package com.hot.manage.entity.dc.param;

import java.io.Serializable;

import lombok.Data;

@Data
public class TItemPicParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 图片id
	private Integer id;
	// 图片编码
	private String picnum;
	// 图片路径
	private String picpath;
	// 图片楼层
	private String site;
	
}
