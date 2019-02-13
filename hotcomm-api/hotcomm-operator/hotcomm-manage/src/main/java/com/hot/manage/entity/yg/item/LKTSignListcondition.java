package com.hot.manage.entity.yg.item;

import com.hot.manage.entity.PageParam;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTSignListcondition extends PageParam {

	//(value = "模块id")
	private Integer moduleid;

	//(value = "开始时间（签到时间）,不填默认搜索结束时间以前的所有参数，均不填则搜索全部数据")
	private String startTime;

	//(value = "结束时间（签到时间）,不填默认搜索开始时间至今的所有参数，均不填则搜索全部数据")
	private String endTime;

	//(value = "签到设备编号关键字或放置位置关键字,不填默认搜索全部")
	private String devnumoraddress;

}
