package com.hot.manage.entity.yg.item;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class LKTSignDeviceListAppVaule {
	//(value = "当前登录用户id")
	private Integer userid;
	
	//(value = "模块id")
	private Integer moduleid;
	
	//(value = "设备编号,设备地址关键字")
	private String macorcode;
}
