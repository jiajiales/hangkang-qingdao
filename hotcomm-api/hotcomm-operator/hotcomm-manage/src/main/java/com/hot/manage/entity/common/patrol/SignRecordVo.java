package com.hot.manage.entity.common.patrol;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class SignRecordVo {
	
	private Integer id;//记录ID
	private Integer deviceid;//设备ID
	private String devnum;//设备编号
	private Integer groupid;//项目ID
	private String groupname;//项目名
	private Integer userid;//签到人员ID
	private String username;//签到人员名称
	private String addtime;//签到时间
	private Integer signstate;//签到状态，0：未成功；1：成功
	private Integer moduleid;//模块ID

}
