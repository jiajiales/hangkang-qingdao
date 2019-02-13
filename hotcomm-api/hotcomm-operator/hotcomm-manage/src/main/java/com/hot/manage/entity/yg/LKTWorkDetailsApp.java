package com.hot.manage.entity.yg;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTWorkDetailsApp extends LKTWorkUntreated implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// (value = "派单人id")
	private Integer pdid;

	// (value = "工单标题")
	private String title;

	// (value = "工单描述")
	private String descrition;

	// (value = "设备编号")
	private Integer devid;

	// (value = "图片URL;用,隔开")
	private String img;

	// (value = "音频URL;用,隔开")
	private String audio;

	// (value = "视频URL;用,隔开")
	private String video;

	// (value = "关联事件集合")
	private List<LKTWorkDetailsAppea> son;

}
