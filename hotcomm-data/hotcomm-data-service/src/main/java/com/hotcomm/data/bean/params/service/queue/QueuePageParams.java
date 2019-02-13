package com.hotcomm.data.bean.params.service.queue;

import com.hotcomm.data.bean.params.PageParams;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class QueuePageParams extends PageParams {

	private Integer memberId;

	private String queueName;

	private Integer type;

	private String memberName;

	private Integer queueStatus;

	private String remark;

	private Integer loginMemberId;

	private Integer loginUserType;

}
