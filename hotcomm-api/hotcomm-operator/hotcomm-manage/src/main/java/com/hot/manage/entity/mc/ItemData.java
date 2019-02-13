package com.hot.manage.entity.mc;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class ItemData {
	
	private Integer id;
	private String groupname;
	private String x;
	private String y;
	private String itemnum;
	private Integer devtotal;
	private Integer alarmCount;
	private Integer faultCount;

}
