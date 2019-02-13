package com.hotcomm.data.bean.vo.devicegroup;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DeviceGroupVO {

	private Integer groupId;

	private String groupName;

	private Integer groupStatus;

	private Integer maxNums;

	private String remark;

}
