package com.hotcomm.prevention.bean.mysql.manage.event;

import java.io.Serializable;
import java.util.List;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class EventParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// (value = "[必填]添加人id")
	private Integer adduserid;

	// (value = "[必填]事件描述")
	private Integer describe;

	// (value =
	// "[必填]紧急程度,5星表示，1-5;1,无明确要求时间;2,3天内处理;3,1天内处理;4,8个小时内处理;5,必须立即处理")
	private Integer level;

	// (value = "[必填]设备id")
	private Integer devid;

	// (value = "[必填]设备编号")
	private String devnum;

	// (value = "图片资源路径")
	private List<String> pictureUrl;

	// (value = "语音资源路径")
	private List<String> voiceUrl;

	// (value = "详细说明")
	private String instructions;

	// (value = "[必填]是否需要派工;0,已处理,不需要;1,需要派工")
	private Integer isdispatch;

}
