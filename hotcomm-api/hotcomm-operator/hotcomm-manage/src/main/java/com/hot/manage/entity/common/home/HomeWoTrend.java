package com.hot.manage.entity.common.home;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class HomeWoTrend {
	private String datetime;// 日期
	private Integer total;// 工单总数量
	private Integer unhandlewocount;// 未处理工单数量
	private Integer hangupwocount;// 挂起工单数量
	private Integer handlecount;// 已处理数量

}
