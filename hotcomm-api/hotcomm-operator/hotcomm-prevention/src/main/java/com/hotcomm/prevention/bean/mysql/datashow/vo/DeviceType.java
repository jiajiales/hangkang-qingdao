package com.hotcomm.prevention.bean.mysql.datashow.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DeviceType {

	private Integer id;
	private String typename;
	private Integer moduleid;
	private Integer isdelete;
}
