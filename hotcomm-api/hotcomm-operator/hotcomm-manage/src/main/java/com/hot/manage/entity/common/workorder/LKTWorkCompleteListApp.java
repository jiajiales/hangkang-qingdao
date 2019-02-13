package com.hot.manage.entity.common.workorder;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTWorkCompleteListApp extends LKTWorkUntreatedApp implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// (value = "处理方式")
	private Integer handleType;

	// (value = "处理耗时")
	private String ordertime;

}
