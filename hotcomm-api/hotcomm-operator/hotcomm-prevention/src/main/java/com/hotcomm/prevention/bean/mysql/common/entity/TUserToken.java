package com.hotcomm.prevention.bean.mysql.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Table(name="t_user_token")
public class TUserToken implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="userid")
	private Integer userid;
	@Column(name="token")
	private String token;
	@Column(name="logintime")
	private String logintime;
	@Column(name="logintype")
	private Integer logintype;
	@Column(name="ip")
	private String ip;
	@Column(name="state")
	private Integer state;

}