package com.hotcomm.data.bean.params.service.queue;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class QueueQueryParams {

	private List<Integer> memberIds;
	private List<Integer> moreStatus;

	public QueueQueryParams(List<Integer> memberIds) {
		super();
		this.memberIds = memberIds;
	}

	public QueueQueryParams(List<Integer> memberIds, List<Integer> moreStatus) {
		super();
		this.memberIds = memberIds;
		this.moreStatus = moreStatus;
	}

}
