package com.hot.manage.entity.yg.item;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class LKTSelectGroupWorkconditionSon{
	//(value="模块ID")
	private Integer moduleid;
	
	//(value="用来查询此用户所关联的GroupID")
	private Integer userid;
	
	//(value="项目组ID;选填，不填查全部工单")
	private Integer groupid;
	
	//(value="开始时间,选填，不填默认搜索结束时间以前的所有参数，均不填则搜索全部数据")
	private String starttime;
	
	//(value="结束时间,选填，不填默认搜索开始时间至今的所有参数，均不填则搜索全部数据")
	private String endtime;
	
	//(value="工单名,选填，不填默认搜索全部;根据工单名进行模糊搜索")
	private String workname;
	
}
