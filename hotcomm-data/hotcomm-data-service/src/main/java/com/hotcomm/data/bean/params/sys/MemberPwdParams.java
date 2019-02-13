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
public class MemberPwdParams implements ParamsValidate {

	@FieldValidate(limit = "1,5", type = ParamType.NUMBER)
	private Integer id;

	@FieldValidate(limit = "5,16", type = ParamType.STRING)
	private String oldPassword;

	@FieldValidate(limit = "5,16", type = ParamType.STRING)
	private String newPassword;

	@FieldValidate(limit = "5,16", type = ParamType.STRING)
	private String newPassword2;

}
