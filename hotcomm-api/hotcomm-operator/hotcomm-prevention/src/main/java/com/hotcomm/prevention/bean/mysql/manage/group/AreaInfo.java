package com.hotcomm.prevention.bean.mysql.manage.group;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AreaInfo {
	
	private Integer areaid;
	private String  areaname;
	
	
}
