package com.hot.manage.entity.video;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class VideoByModuleid {

	private Integer id;
	private String devnum;
	private String mac;
	private String code;
	private String addtime;
	private String lat;
	private String lng;
	private String x;
	private String y;
	private Integer ownId;
	private Integer adduserid;
	private String videoPath;
	private String factory;

}
