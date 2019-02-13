package com.hot.manage.entity.common.patrol;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class PatrolParams {
	private Integer pageSize;
	private Integer pageNum;
	private String starttime;
	private String endtime;
	private String context;//关键字：人员编号或人员名称
	private Integer userid;
	private Integer moduleid;
	

}
