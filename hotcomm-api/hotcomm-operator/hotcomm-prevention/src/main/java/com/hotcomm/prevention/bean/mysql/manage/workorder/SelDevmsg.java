package com.hotcomm.prevention.bean.mysql.manage.workorder;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SelDevmsg implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// 新增的旧设备id
	private Integer id;

	private Integer moduleid;

	private Integer groupid;

	// (value = "时间")
	private String addtime;

	// (value = "设备编号")
	private String devnum;

	// (value = "安装人")
	private Integer adduserid;

	// (value = "责任人")
	private Integer own_id;

	private String lat;

	private String lng;

	private String code;

	private String newdevnum;

}
