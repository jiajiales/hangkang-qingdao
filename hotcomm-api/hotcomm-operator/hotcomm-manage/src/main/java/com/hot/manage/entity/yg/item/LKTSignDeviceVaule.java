package com.hot.manage.entity.yg.item;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class LKTSignDeviceVaule {
	//(value = "不用填写")
	private Integer id;

	//(value = "设备编号")
	private String devnum;
	
	//(value = "关联项目组id")
	private Integer itemid;
	
	//(value = "签到设备放置位置")
	private String address;
	
	//(value = "经度")
	private String lon;
	
	//(value = "纬度")
	private String lat;
	
	//(value = "巡检人员id;如果要分配给多个签到人员，id请用，隔开；如：1,2,3")
	private String patrolsid;
	
}
