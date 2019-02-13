package com.hotcomm.prevention.bean.mysql.common.params;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ReciveLngLat {
	private Integer userid;
	private String lng;
	private String lat;
	private String recivetime;
	private Integer onlinestate;
	private String realname;
	private String telephone;
}
