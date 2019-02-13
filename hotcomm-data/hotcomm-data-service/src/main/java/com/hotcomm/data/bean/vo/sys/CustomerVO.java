package com.hotcomm.data.bean.vo.sys;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CustomerVO {

	private Integer id;

	private String memberName;

	private Integer status;

	private String email;

	private String realName;

	private String telephone;

	private Date createTime;

	private String createUser;

	private String roles;

	private Integer userType;

	private String vhost;

	private String vhostAccount;

	private String vhostPassword;

	private Integer vhostStatus;

}
