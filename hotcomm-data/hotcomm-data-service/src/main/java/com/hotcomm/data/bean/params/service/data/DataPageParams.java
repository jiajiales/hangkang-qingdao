package com.hotcomm.data.bean.params.service.data;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.hotcomm.data.bean.params.PageParams;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DataPageParams extends PageParams {

	private Integer memberId;

	private Integer memberType;

	private String deviceCode;

	private String queueName;

	private Integer sendStatus;

	private Integer dataSource;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endTime;

	private Long longStartTime;

	private Long longEndTime;

	private Integer startIndex;

	private Integer endIndex;

}
