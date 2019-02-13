package com.hotcomm.data.bean.params.sys;

import com.hotcomm.data.bean.params.PageParams;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class MemberPageParams extends PageParams{

	private Integer id;

	private String password;

	private String memberName;

	private Integer status;

	private Integer userType;

	private String email;

	private String realName;

	private String telephone;

	private String createUser;

	private String[] roles;

	private Integer startIndex;

	private Integer endIndex;

}
