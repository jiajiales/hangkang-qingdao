package com.hotcomm.prevention.bean.mysql.common.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TUserVo {
	private Integer id;

	private String usernum;

	private String loginname;

	private String realname;

	private Integer fatherid;

	private String companyname;

	private String telephone;

	private String contacts;

	private Boolean isenable;

	private Boolean isdelete;

	private String addtime;

	private Integer adduserid;

	private String lastlogintime;

	private String lastloginip;

	private Integer createTime;

	private Integer updateTime;

	private Integer positionid;

	private String userpicpath;// 用户头像

	private Integer isAppUser;// 是否是app用户，0：不是；1：是

	private Integer isPcUser;// 是否是pc端用户；0：不是；1：是
}
