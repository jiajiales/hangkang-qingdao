package com.hotcomm.data.bean.params.sys;

import com.hotcomm.framework.annotation.FieldValidate;
import com.hotcomm.framework.annotation.ParamType;
import com.hotcomm.framework.comm.ParamsValidate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class RoleParams implements ParamsValidate {

	private Integer id;

	@FieldValidate(limit = "5,200", type = ParamType.STRING)
	private String desc;

	@FieldValidate(limit = "2,50", type = ParamType.STRING)
	private String roleName;

	private Integer status;

	private String resourceIds;

}
