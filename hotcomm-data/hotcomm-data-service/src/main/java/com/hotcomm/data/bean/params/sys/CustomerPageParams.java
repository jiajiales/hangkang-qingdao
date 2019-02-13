package com.hotcomm.data.bean.params.sys;

import com.hotcomm.data.bean.params.PageParams;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CustomerPageParams extends PageParams {

	private Integer id;

	private String password;

	private String memberName;

	private Integer status;

	private String email;

	private String realName;

	private String telephone;

	private String createUser;

	private Integer vhostStatus;

	private String[] roles;

	private Integer loginMemberId;

	private Integer loginUserType;

}
