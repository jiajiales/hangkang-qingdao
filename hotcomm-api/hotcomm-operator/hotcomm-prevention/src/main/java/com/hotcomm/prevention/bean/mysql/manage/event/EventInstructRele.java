package com.hotcomm.prevention.bean.mysql.manage.event;

import java.io.Serializable;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class EventInstructRele implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//(value = "指示人")
	private String contacts;
	
	//(value = "指示信息")
	private String content;

}
