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
public class MemberVhostParams implements ParamsValidate {

	@FieldValidate(limit = "1,5", type = ParamType.NUMBER)
	private Integer memberId;

	@FieldValidate(limit = "2,35", type = ParamType.CUSTOM, pattern = "^[A-Za-z0-9./_]+$")
	private String vhost;

	private Integer vhostStatus;

	private String vhostCode;

	@FieldValidate(limit = "2,30", type = ParamType.CUSTOM, pattern = "^[A-Za-z0-9./_]+$")
	private String vhostAccount;

	@FieldValidate(limit = "5,30", type = ParamType.ENGLISH_NUMBER)
	private String vhostPassword;

}
