package com.hotcomm.data.bean.vo.sys;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class MemberVO {

	private Integer id;

	private String memberName;

	private Integer status;

	private String email;

	private String realName;

	private String telephone;

	private String customerIds;

	private Date createTime;

	private String createUser;

	private String roles;

	private Integer userType;

}
