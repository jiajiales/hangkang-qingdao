package com.hot.manage.entity.yg;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTSelectUserApp implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	//(value = "用户编号")
	private String userNum;

	//(value = "用户姓名")
	private String contacts;
}
