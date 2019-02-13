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
public class CustomerParams implements ParamsValidate {

	@FieldValidate(limit = "1,5", type = ParamType.NUMBER)
	private Integer id;

	@FieldValidate(limit = "5,16", type = ParamType.STRING)
	private String password;

	@FieldValidate(limit = "5,30", type = ParamType.ENGLISH_NUMBER)
	private String memberName;

	@FieldValidate(limit = "1", type = ParamType.NUMBER)
	private Integer status;

	@FieldValidate(limit = "5,50", type = ParamType.EMAIL)
	private String email;

	@FieldValidate(limit = "2,30", type = ParamType.NAME)
	private String realName;

	@FieldValidate(limit = "11", type = ParamType.NUMBER)
	private String telephone;

	private String createUser;

	@FieldValidate(limit = "1", type = ParamType.NUMBER)
	private Integer userType;

	@FieldValidate(limit = "1", type = ParamType.NUMBER)
	private Integer queueType;

	@FieldValidate(limit = "1,50", type = ParamType.STRING)
	private String remark;

	private Integer isDelete;

	private String[] roles;

	// public CustomerParams(String password, String memberName, Integer userType) {
	// super();
	// this.password = password;
	// this.memberName = memberName;
	// this.userType = userType;
	// }

}
