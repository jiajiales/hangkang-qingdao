package com.hot.manage.entity.yg;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTDeviceAll implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//(value = "设备id")
	private Integer id;
	
	//(value = "设备编号")
	private String devnum;
	
	//(value = "设备mac地址")
	private String mac;
	
	//(value = "设备地址")
	private String code;
	
	//(value = "设备增加时间")
	private String addtime;
	
	//(value = "是否删除,1：删除，0：不删除")
	private Integer isdelete;
	
	//(value = "是否可用,1：可用；0：不可用")
	private Integer isenable;
	
	//(value = "添加人id")
	private Integer adduserid;
	
	//(value = "纬度")
	private String lat;
	
	//(value = "经度")
	private String lng;
	
	//(value = "当前设备状态,0：正常；1：报警，2：故障，3：其他")
	private Integer state;

	//(value = "电池电量")
	private Integer battery;
	
	//(value = "相对于地图的x方向位置")
	private double x;
	
	//(value = "相对于地图的y方向位置")
	private double y;
}
