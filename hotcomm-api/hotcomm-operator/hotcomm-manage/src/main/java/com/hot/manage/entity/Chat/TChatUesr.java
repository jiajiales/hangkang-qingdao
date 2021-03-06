package com.hot.manage.entity.Chat;

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
@Table(name = "t_chat_user")
public class TChatUesr implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "userid")
	private String chatuserid;

	@Column(name = "username")
	private String username;

	@Column(name = "token")
	private String chattoken;

	@Column(name = "addtime")
	private String addtime;
	
	@Column(name = "isdelete")
	private Integer isdelete;
}
