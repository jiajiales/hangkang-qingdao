package com.hot.manage.entity.yg;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTSignGroupList implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//(value = "项目组id")
	private Integer id;
	
	//(value = "项目组名")
	private String groupname;
	
}
