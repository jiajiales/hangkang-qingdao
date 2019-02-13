package com.hot.manage.entity.yg.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class YGEventInstructRele implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//(value = "指示人")
	private String contacts;
	
	//(value = "指示信息")
	private String content;

}
