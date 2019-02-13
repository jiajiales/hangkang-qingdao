package com.hot.manage.entity.common.patrol;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class PatrolPersonVo {
	private Integer id;//巡检人ID
	private String usernum;//人员编号
	private String contacts;//人员名称
	private String addtime;//添加时间
	private Integer addresscount;//摇一摇数量
	private String devcount;//签到设备数量
	private String lastsigntime;//最后签到时间
	
}
