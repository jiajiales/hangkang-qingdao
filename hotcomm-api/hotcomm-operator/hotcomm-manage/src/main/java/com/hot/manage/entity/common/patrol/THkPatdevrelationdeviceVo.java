package com.hot.manage.entity.common.patrol;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class THkPatdevrelationdeviceVo {
	
	private Integer userid;//巡检人员ID
	private Integer deviceid;//绑定的终端设备ID

}
