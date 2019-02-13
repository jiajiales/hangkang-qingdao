package com.hotcomm.data.bean.entity.service;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "hk_customer_member")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CustomerMember {

	@Column(name = "customer_id")
	private Integer customerId;

	@Column(name = "member_id")
	private Integer memberId;

}
