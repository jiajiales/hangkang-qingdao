package com.hotcomm.prevention.bean.mysql.datashow.video;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DeviceRelationVideoVo {

	private Integer id;
	private String videoNum;
	private String factory;
	private String code;
	private String lng;
	private String lat;
	private String x;
	private String y;
	private String addtime;
	private Integer relationDevCount;
	private Integer state;
	private String relationDev;
	private String videoPath;
	private Integer distance;

}
