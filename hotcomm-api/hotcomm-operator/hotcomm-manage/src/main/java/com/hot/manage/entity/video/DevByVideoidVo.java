package com.hot.manage.entity.video;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DevByVideoidVo {

	private Integer id;
	private String devnum;
	private String mac;
	private String code;
	private String addtime;
	private String lat;
	private String lng;
	private String x;
	private String y;
	private Integer own_id;
	private Integer devtype;

}
