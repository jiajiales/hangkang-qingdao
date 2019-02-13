package com.hot.manage.entity.common.patrol;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ShakeSignVo {
	private Integer id;//摇一摇设备ID
	private String devnum;//设备编号
	private Integer groupid;//项目组ID
	private String groupname;//关联项目
	private String code;// 放置位置
	private String lng;//经度
	private String lat;//维度
	private String userid;//签到人员
	private String signname;//签到名称
}
