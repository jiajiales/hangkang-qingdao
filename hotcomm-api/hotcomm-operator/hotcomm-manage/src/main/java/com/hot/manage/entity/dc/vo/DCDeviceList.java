package com.hot.manage.entity.dc.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor

public class DCDeviceList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 设备id
	private Integer id;
	// mac地址
	private String mac;
	// 设备编号
	private String devnum;
	// 项目id
	private Integer groupid;
	// 关联项目名称
	private String groupname;
	// 设备地址
	private String code;
	// 责任人id
	private Integer own_id;
	// 责任人
	private String contacts;
	// 添加时间
	private String addtime;
	// 经度
	private String lat;
	// 纬度
	private String lng;
	// 电池总量
	private Integer Battery;
	
	private Integer  count;

	private Integer picid;// 图片id
	private String x;// 设备x
	private String y;// 设备y
	private String picpath;// 图片路径
	private String site;// 图片所在楼层
	private String videos;//设备关联的摄像头

}
