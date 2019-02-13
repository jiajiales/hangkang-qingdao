package com.hot.manage.entity.common.patrol;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class AppSigndDevVo {
	private Integer id;//终端设备ID
	private String devnum;//设备编号
	private String modulename;//设备类型或模块名称
	private Integer ownid;//责任人ID
	private String ownname;//责任人名称
	private Integer type;//设备类型，0：摇一摇设备；1：终端设备
	private String mac;
	private String lng;
	private String lat;
	/*private Integer clockid;//签到人ＩＤ
	private String clocknum;//签到人编号
	private String clockname;//签到人姓名
*/	
	

}
