package com.hotcomm.data.bean.params.sys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CustomerMemberParams {

	private Integer memberId;

	private String customerIds;

	public CustomerMemberParams(Integer memberId, String customerIds) {
		super();
		this.memberId = memberId;
		this.customerIds = customerIds;
	}

}
