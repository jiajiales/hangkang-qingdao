package com.hotcomm.data.bean.params.service.device;

import com.hotcomm.framework.annotation.FieldValidate;
import com.hotcomm.framework.annotation.ParamType;
import com.hotcomm.framework.comm.ParamsValidate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DeviceBatchParams implements ParamsValidate {

	@FieldValidate(limit = "1,5", type = ParamType.NUMBER)
	private Integer deviceGroupId;

	@FieldValidate(limit = "1,10000", type = ParamType.CUSTOM, pattern="^[0-9,]+$")
	private String deviceIds;

}
