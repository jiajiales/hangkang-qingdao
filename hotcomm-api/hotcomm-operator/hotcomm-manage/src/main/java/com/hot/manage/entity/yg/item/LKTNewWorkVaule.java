package com.hot.manage.entity.yg.item;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class LKTNewWorkVaule {

	//(value = "工单标题")
	private String title;
	
	//(value = "工单描述")
	private String descrition;
	
	//(value = "要求处理时间-0-其他 ;1-立即 ;2-30分钟 ;3-1个小时; 4-2个小时' 5- 3个小时 ;6- 6个小时; 7- 一天内; 8-两天内;9-三天内")
	private Integer complete_time;
	
	//(value = "处理人id")
	private Integer assignid;
	
	//(value = "创建工单的用户ID")
	private Integer adduserid;
	
	//(value = "需要关联的报警记录id;0：不需要关联；关联多个的时候id用逗号隔开如：1,2,3")
	private String alarm_id;
	
	//(value = "需要关联的事件上报记录id;0：不需要关联；关联多个的时候id用逗号隔开如：1,2,3")
	private String event_id;
	
	//(value = "需要关联的设备id;0：不需要关联；关联多个的时候id用逗号隔开如：1,2,3")
	private String dev_id;
	
}
