package com.hot.manage.entity.common.alarm;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AlarmStateParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// (value = "[必填]类型ID")
	private Integer id;

	// (value = "[必填]状态名称")
	private String state_name;

	// (value = "模块ID")
	private Integer module_id;

	// (value = "[必填]紧急状态程度，1-5")
	private Integer level;

	// (value = "添加时间")
	private String addtime;

	// (value = "[必填]类型，1：报警；2：故障; 3：其他")
	private Integer type;
}
