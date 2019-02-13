package com.hot.manage.entity.dc.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class DCItemPicVo implements Serializable {

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
	// 图片添加时间
	private String addtime;
	// 组id
	private Integer itemid;
}
