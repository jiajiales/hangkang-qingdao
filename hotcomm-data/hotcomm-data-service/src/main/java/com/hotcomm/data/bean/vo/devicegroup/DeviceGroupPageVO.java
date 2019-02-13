package com.hotcomm.data.bean.vo.devicegroup;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DeviceGroupPageVO {

	private Integer devGroupId;

	private String devGroupName;

	private Integer groupStatus;

	private Integer devNum;

	private Integer devMax;

	private String memberIds;

	private String memberNames;

	private String remark;

}
