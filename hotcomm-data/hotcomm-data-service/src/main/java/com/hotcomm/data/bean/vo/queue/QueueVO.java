package com.hotcomm.data.bean.vo.queue;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class QueueVO {

	private Integer queueId;

	private String queueName;

	private Integer type;

	private Date holeTime;

	private Integer queueStatus;

	private Long receiveDataNum;

	private Long sendDataNum;

	private Long waitSendNum;

	private Long sendFilterInterval;

	private String vhost;

	private String vhostAccount;

	private String vhostPassword;

	private Integer messages;

	private Integer consumers;

	private String remark;

}
