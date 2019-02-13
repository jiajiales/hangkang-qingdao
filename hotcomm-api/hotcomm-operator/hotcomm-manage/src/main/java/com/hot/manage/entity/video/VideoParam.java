package com.hot.manage.entity.video;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class VideoParam {

	private Integer id;
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
	private String devnum;
	private String mac;
	private Integer groupid;
	private String groupname;
	private String managerid;
	private String username;
	private Integer moduleid;
	private Integer itemPicId;
	private String site;
	private String picpath;
	private Integer deviceid;
	private Integer ownId;
	private Integer adduserid;
	private Integer devId;
	private Boolean isenable;
	private Boolean isdelete;

}
