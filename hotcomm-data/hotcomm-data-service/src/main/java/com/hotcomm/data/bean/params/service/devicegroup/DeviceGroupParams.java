package com.hotcomm.data.bean.params.service.devicegroup;

import com.hotcomm.framework.annotation.FieldValidate;
import com.hotcomm.framework.annotation.ParamType;
import com.hotcomm.framework.comm.ParamsValidate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DeviceGroupParams implements ParamsValidate {

	@FieldValidate(limit = "1,5", type = ParamType.NUMBER)
	private Integer groupId;

	@FieldValidate(limit = "2,30", type = ParamType.STRING)
	private String groupName;

	@FieldValidate(limit = "1,200", type = ParamType.STRING)
	private String remark;

	@FieldValidate(limit = "1", type = ParamType.NUMBER)
	private Integer groupStatus;

	@FieldValidate(limit = "1,5", type = ParamType.NUMBER)
	private Integer maxNums;

}
