package com.hot.manage.entity.yg;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTSelectHandle implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//(value = "用户id")
	private Integer id;

	//(value = "姓名")
	private String contacts;
	
}
