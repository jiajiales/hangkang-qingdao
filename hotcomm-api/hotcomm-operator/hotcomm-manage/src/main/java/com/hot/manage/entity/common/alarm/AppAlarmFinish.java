package com.hot.manage.entity.common.alarm;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AppAlarmFinish implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 状态名称
	private String state_name;

	// 处理详情
	private String problemdesc;

	// 处理完成时间
	private String endTime;

	// 图片路径
	private String pictureUrl;

	// 工单id
	private Integer id;
}
