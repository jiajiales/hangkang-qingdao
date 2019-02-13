package com.hotcomm.prevention.bean.mysql.manage.workorder;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class WorkDetails {

	// (value = "工单id")
	private Integer id;

	// (value = "工单编号")
	private String code;

	// (value = "工单状态")
	private Integer state;

	// (value = "工单标题")
	private String title;

	// (value = "发布时间")
	private String addtime;

	// (value = "要求处理时间-0-其他 ;1-立即 ;2-30分钟 ;3-1个小时; 4-2个小时' 5- 3个小时 ;6- 6个小时; 7-
	// 一天内; 8-两天内;9-三天内")
	private Integer complete_time;

	// (value = "工单描述")
	private String descrition;

	// (value = "责任人名字")
	private String assign;

	// (value = "责任人电话")
	private String assigntelephone;

	// (value = "派单人名字")
	private String adduserid;

	// (value = "派单人电话")
	private String adduseridtelephone;

	// (value = "工单关联的事件或者报警")
	private List<WorkDetailsAllevent> allevents;

	// (value = "关联的设备")
	private WorkDetailsDeviceAll devall;

	// (value = "事件或者报警关联的工作指示")
	private List<WorkDetailsInstructions> instructions;
}
