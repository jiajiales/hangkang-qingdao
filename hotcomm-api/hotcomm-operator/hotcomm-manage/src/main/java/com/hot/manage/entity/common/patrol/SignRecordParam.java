package com.hot.manage.entity.common.patrol;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class SignRecordParam extends PatrolParams {
	
	private Integer deviceid;//设备id
	private String groupname;//关联项目

}
