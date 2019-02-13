package com.hotcomm.data.bean.entity.service;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "hk_member_vhost")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class MemberVhost {

	@Column(name = "member_id")
	private Integer memberId;

	@Column(name = "vhost")
	private String vhost;

	@Column(name = "vhost_status")
	private Integer vhostStatus;

	@Column(name = "vhost_code")
	private String vhostCode;

	@Column(name = "vhost_account")
	private String vhostAccount;

	@Column(name = "vhost_password")
	private String vhostPassword;

	public MemberVhost(Integer memberId) {
		super();
		this.memberId = memberId;
	}

	public MemberVhost(String vhost) {
		super();
		this.vhost = vhost;
	}

}
