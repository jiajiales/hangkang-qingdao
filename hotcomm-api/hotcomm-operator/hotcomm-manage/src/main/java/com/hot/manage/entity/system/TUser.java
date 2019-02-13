package com.hot.manage.entity.system;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Table(name="t_user")
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class TUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	
	@Column(name="userNum")
	private String usernum;
	
	@Column(name="loginname")
    private String loginname;
	
	@Column(name="password")
    private String password;
	
	@Column(name="realname")
    private String realname;
	
	@Column(name="fatherid")
    private Integer fatherid;
	
	@Column(name="companyname")
    private String companyname;
	
	@Column(name="telephone")
    private String telephone;
	
	@Column(name="contacts")
    private String contacts;
	
	@Column(name="isenable")
    private Boolean isenable;
	
	@Column(name="isdelete")
    private Boolean isdelete;
	
	@Column(name="addtime")
    private String addtime;
	
	@Column(name="adduserid")
    private Integer adduserid;
	
	@Column(name="lastlogintime")
    private String lastlogintime;
	
	@Column(name="lastloginIP")
    private String lastloginip;
	
	@Column(name="create_time")
    private String createTime;
	
	@Column(name="update_time")
    private String updateTime;

	@Column(name="status")
    private Boolean status;
	
	@Column(name="positionid")
    private Integer positionid;
	
	@Column(name="userpicPath")
	private String userpicpath;
	
	@Column(name="isAppUser")
	private Integer isAppUser;
	
	@Column(name="isPcUser")
	private Integer isPcUser;

}