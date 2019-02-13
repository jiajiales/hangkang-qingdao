package com.hot.manage.entity.yg.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class YGEventHandling implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// (value = "事件处理状态，0：未处理；1：已派单；2：挂起；3：处理完毕'")
	private Integer state;

	// (value = "工单号")
	private String code;

	// (value = "分配人")
	private String contacts;

	// (value = "分配人电话")
	private String telephone1;

	// (value = "分配时间")
	private String addtime;

	// (value = "要求处理时间,0-其他 ;1-立即 ;2-30分钟 ;3-1个小时; 4-2个小时'' 5- 3个小时 ;6- 6个小时;
	// 7- 一天内; 8-两天内;9-三天内")
	private Integer complete_time;

}
