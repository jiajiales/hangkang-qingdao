package com.hot.manage.entity.common.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class LoginModel {
	private String username;
	private String password;
	private String code;
	private int logintype;
	private String codetoken;
}
