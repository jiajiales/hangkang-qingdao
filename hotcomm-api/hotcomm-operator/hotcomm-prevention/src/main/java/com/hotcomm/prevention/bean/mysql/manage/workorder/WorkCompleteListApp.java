package com.hotcomm.prevention.bean.mysql.manage.workorder;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class WorkCompleteListApp extends WorkUntreatedApp implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// (value = "处理方式")
	private Integer handleType;

	// (value = "处理耗时")
	private String ordertime;

}
