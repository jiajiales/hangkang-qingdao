package com.hot.manage.entity.common.workorder.vaule;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTWorkListcondition implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// (value="模块ID")
	private Integer moduleid;

	// (value="用来查询此用户所关联的GroupID")
	private Integer userid;

	// (value="项目组ID;选填，不填查全部工单")
	private Integer groupid;

	// (value="开始时间,选填，不填默认搜索结束时间以前的所有参数，均不填则搜索全部数据")
	private String starttime;

	// (value="结束时间,选填，不填默认搜索开始时间至今的所有参数，均不填则搜索全部数据")
	private String endtime;

	// (value="工单名,选填，不填默认搜索全部;根据工单名进行模糊搜索")
	private String workname;

	// (value="工单状态；0：未处理；1：挂起；2：已处理")
	private Integer state;

	private Integer pageNum;

	private Integer pageSize;

}
