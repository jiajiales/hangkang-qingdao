package com.hot.manage.entity.yg;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTWorkTobetreatedApp extends LKTWorkUntreated implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//(value = "处理方式")
	private String handleType;
	
	//(value = "处理耗时")
	private String ordertime;
	
}
