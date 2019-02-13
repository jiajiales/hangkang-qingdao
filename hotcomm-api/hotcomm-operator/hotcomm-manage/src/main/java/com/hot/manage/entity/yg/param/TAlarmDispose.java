package com.hot.manage.entity.yg.param;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TAlarmDispose implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// (value = "[必填]用户ID")
	private Integer id;

	// (value = "[必填]报警记录ID")
	private Integer ygalarmid;

	// (value = "[必填]报警状态")
	private Integer alarmstateid;

	// (value = "[必填]是否需要派工")
	private Integer isdispatch;

	// (value = "[可不填]问题备注")
	private String remark;

	// (value = "[可不填]图片路径")
	private List<String> pictureUrl;
}
