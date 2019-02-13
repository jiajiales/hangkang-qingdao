package com.hotcomm.prevention.bean.mysql.manage.patrol;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SignPlaceOnid {
	private Integer id;
	private String address;
	private String lng;
	private String lat;
	private String devnum;
	private String lastsigntime;
	private String itemid;
	private String addtime;
	private String QRcodePicUrl;
	private String patID;
	private String patName;
	private String groupname;
}
