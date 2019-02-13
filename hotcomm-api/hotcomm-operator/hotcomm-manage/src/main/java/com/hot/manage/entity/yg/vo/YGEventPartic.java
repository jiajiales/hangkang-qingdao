package com.hot.manage.entity.yg.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class YGEventPartic implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// (value = "事件id")
	private Integer id;

	// (value = "事件描述")
	private String state_name;

	// (value = "紧急程度,5星表示，1-5;1,无明确要求时间;2,3天内处理;3,1天内处理;4,8个小时内处理;5,必须立即处理")
	private Integer level;

	// (value = "上报人")
	private String addusername;

	// (value = "上报人电话")
	private String addusertelephone;

	// (value = "上报时间")
	private String addtime;

	// (value = "设备编码")
	private String devnum;

	// (value = "设备地址")
	private String code;

	// (value = "事件处理状态,0：未处理；1：已派单；2：挂起；3：处理完毕")
	private Integer state;

	// (value = "处理人")
	private String assignname;

}
