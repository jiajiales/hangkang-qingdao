package com.hot.manage.entity.yg.param;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class YGEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// (value = "[不用填]事件id")
	protected Integer id;

	// (value = "[不用填]事件编号")
	private String code;

	// (value = "事件处理状态,0未处理，1已派单 2挂起 3处理完毕")
	private Integer state;

	// (value = "添加时间")
	private String addtime;

	// (value = "添加时间")
	private YGEventParam ygeventParam;
}
