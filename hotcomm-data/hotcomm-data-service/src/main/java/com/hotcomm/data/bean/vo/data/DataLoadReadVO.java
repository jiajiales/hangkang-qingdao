package com.hotcomm.data.bean.vo.data;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DataLoadReadVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer memberId;

	private Integer memberStatus;

	private String vhostCode;

	private String queueName;

	private Integer queueId;

	private Date queueHoldTime;

	private Integer queueStatus;

	private Long sendFilterInterval;

	private String vhostName;
	
	private Integer deviceTypeId;

}
