package com.hotcomm.data.bean.vo.queue;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class QueuePageVO {

	private Integer queueId;

	private String queueName;

	private Integer type;

	private String memberName;

	private Date holeTime;

	// private Long receiveDataNum;

	// private Long waitSendNum;

	private Integer queueStatus;

	private Date createTime;

	private String remark;

}
