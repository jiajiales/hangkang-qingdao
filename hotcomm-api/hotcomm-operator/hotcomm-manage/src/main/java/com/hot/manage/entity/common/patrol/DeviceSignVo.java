package com.hot.manage.entity.common.patrol;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class DeviceSignVo {
	private Integer id;//终端设备ID
	private String devnum;//设备编号
	private Integer groupid;//项目组ID
	private String groupname;//项目组名称
	private String code;//设备安装位置
	private String lng;//经度
	private String lat;//维度
	private String userid;//签到人ID
	private String username;//签到人名称
	private String lastsigntime;//最后签到时间
	private Integer moduleid;
}
