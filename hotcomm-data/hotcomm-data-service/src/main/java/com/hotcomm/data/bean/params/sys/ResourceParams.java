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
public class ResourceParams implements ParamsValidate {

	private Integer id;

	@FieldValidate(limit = "2,200", type = ParamType.CUSTOM, pattern = "^[A-Za-z0-9-/]+$")
	private String path;

	private Integer type;

	@FieldValidate(limit = "2,50", type = ParamType.STRING)
	private String name;

	@FieldValidate(limit = "0,10", type = ParamType.NUMBER)
	private Integer weight;

	private Integer status;

	private Integer pid;

	@FieldValidate(limit = "2,100", type = ParamType.CUSTOM, pattern = "^[A-Za-z0-9-]+$")
	private String key;

}
