package com.hotcomm.prevention.bean.mysql.manage.projectoverview;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//报警处理数据统计
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AlarmHandleStatistics implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String TheDate;
	private Integer unhandlecount;
	private Integer handlingcount;
	private Integer handlecount;
	private Integer num;
}
