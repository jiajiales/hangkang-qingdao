package com.hot.manage.entity.yg;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTMyproject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// (value="项目组ID")
	private Integer groupid;

	// (value="项目组名")
	private String groupname;

	private String groupcode;

	private Integer coordinate;

	private String addtime;

	private Integer adduserid;

	private Integer managerid;

	private String contacts;

	private Integer cityid;

	private String telephone;

	private String imgpath;

	private String itemnum;

	// (value="设备总数")
	private Integer totalequipment;

	// (value="设备报警数")
	private Integer alarmequipment;

	// (value="设备故障数")
	private Integer faultequipment;

	// (value="项目组纬度")
	private String lat;

	// (value="项目组经度")
	private String lng;

	// (value="项目组工单数")
	private Integer worknum;

}
