package com.hot.manage.entity.common.user;

import java.sql.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserTokenModel {
	private int id;
	private int userid;
	private String token;
	private Date logintime;
	private int logintype;
	private String ip;
	private int state;
}
