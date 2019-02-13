package com.hot.manage.entity.yg.item;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTSignDeviceListUserVaule {

	//(value = "设备签到id")
	private Integer id;

	//(value = "巡检人员id;如果要分配给多个签到人员，id请用，隔开；如：1,2,3")
	private String patrolsid;
}
