package com.hotcomm.data.bean.vo.devicegroup;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DeviceGroupListVO {

	private Integer devGroupId;

	private String devGroupName;

}
