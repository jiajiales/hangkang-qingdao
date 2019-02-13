package com.hotcomm.data.bean.entity.sys;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Integer id;

	@Column(name = "password")
	private String password;

	@Column(name = "member_name")
	private String memberName;

	@Column(name = "status")
	private Integer status;

	@Column(name = "email")
	private String email;

	@Column(name = "real_name")
	private String realName;

	@Column(name = "telephone")
	private String telephone;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "update_time")
	private Date updateTime;

	@Column(name = "create_user")
	private String createUser;

	@Column(name = "user_type")
	private Integer userType;

	@Column(name = "remark")
	private String remark;

	@Column(name = "is_delete")
	private Integer isDelete;

	@Column(name = "vhost")
	private String vhost;

	@Column(name = "vhost_account")
	private String vhostAccount;

	@Column(name = "vhost_password")
	private String vhostPassword;

	@Column(name = "vhost_status")
	private Integer vhostStatus;

}
