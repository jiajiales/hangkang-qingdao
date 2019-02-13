package com.hot.manage.entity.yg.item;

import com.hot.manage.entity.PageParam;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class LKTSignHistorycondition  extends PageParam  {

	//(value = "设备签到id")
	private Integer id;
	
	//(value = "开始时间（签到时间）;开始时间,选填，不填默认搜索结束时间以前的所有参数")
	private String startTime;
	
	//(value = "结束时间（签到时间）;结束时间,选填，不填默认搜索开始时间至今的所有参数")
	private String endTime;
	
	//(value = "签到状态;选填，不填默认搜索全部 ；0：搜索签到失败的；1：搜索签到成功的")
	private String signstate;
	
	//(value = "签到设备编号关键字;选填，不填默认搜索全部 ")
	private String devnum;
	
}
