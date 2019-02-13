package com.hotcomm.prevention.bean.mysql.common.params;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UpdateStationParam {

	private String stcd;
	private Integer stationType;
	private String ppAlarmValue;
	private String zzAlarmValue;
	private String ppYJValue;
	private String zzYJValue;
	
}
