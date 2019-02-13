package com.hotcomm.data.bean.entity.sys;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "sys_member")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Member implements Serializable {

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

	public Member(Integer id) {
		super();
		this.id = id;
	}

	public Member(String memberName) {
		super();
		this.memberName = memberName;
	}

	public Member(String memberName, String password) {
		super();
		this.memberName = memberName;
		this.password = password;
	}

	public Member(Integer id, String password) {
		super();
		this.id = id;
		this.password = password;
	}

}
