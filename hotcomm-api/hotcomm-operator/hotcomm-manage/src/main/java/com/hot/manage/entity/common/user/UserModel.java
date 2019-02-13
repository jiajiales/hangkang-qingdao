package com.hot.manage.entity.common.user;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class UserModel {
	private int id;
	private String username;
	private String token;
	private List<PowerModel> powerList;
	private Integer roleid;
}
