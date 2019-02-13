package com.hotcomm.data.bean.entity.sys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "member_token")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class MemberToken {

	@Column(name = "member_id")
	private Integer memberId;

	@Column(name = "token")
	private String token;

	@Column(name = "expire_time")
	private Date expireTime;

	@Column(name = "session_id")
	private String sessionId;

	public MemberToken(Integer memberId, String token, Date expireTime) {
		super();
		this.memberId = memberId;
		this.token = token;
		this.expireTime = expireTime;
	}

}
