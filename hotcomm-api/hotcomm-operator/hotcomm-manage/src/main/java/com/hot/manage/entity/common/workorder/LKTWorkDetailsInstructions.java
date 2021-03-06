package com.hot.manage.entity.common.workorder;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTWorkDetailsInstructions  implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//(value = "指示人")
	private String name;
	
	//(value = "指示内容")
	private String content;
}
