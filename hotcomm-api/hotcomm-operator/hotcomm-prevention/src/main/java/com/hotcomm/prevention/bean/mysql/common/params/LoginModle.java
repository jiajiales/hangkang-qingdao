package com.hotcomm.prevention.bean.mysql.common.params;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LoginModle {
	private String username;
	private String password;
	// 验证码
	private String code;
	// 登陆类型-1：PC端登陆；2：APP登陆
	private Integer logintype;
	// uuid:获取缓存中验证码的key
	private String uuid;
}
