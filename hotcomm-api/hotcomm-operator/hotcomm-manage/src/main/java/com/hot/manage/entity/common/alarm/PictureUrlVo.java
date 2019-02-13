package com.hot.manage.entity.common.alarm;

import java.io.Serializable;

import lombok.Data;

@Data
public class PictureUrlVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 报警/事件id
	private Integer id;
	// 图片路径
	private String pictureUrl;
}
