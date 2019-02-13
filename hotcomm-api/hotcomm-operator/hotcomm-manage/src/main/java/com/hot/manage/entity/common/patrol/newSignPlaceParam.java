package com.hot.manage.entity.common.patrol;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class newSignPlaceParam {
	private String address;
	private String lng;
	private String lat;
	private String devnum;
	private String itemid;
	private String ADDTIME;
	private String QRcodePicUrl;
	private Integer insertId;
	private String patUser;
}
