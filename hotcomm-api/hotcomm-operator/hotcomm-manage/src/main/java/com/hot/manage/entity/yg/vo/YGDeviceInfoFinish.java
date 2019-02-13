package com.hot.manage.entity.yg.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class YGDeviceInfoFinish {

	// (value = "是否需要派工;0,已处理,不需要;1,需要派工")
	private Integer isdispatch;

	// (value = "处理人")
	private String contacts;

	// (value = "处理状态，0：未处理；1：处理中；2：已处理")
	private Integer handlestate;

	// (value = "结束时间")
	private String endtime;
}
