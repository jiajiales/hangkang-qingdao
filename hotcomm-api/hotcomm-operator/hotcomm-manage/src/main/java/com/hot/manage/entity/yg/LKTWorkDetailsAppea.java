package com.hot.manage.entity.yg;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTWorkDetailsAppea implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	//(value = "事件id/报警id")
	private Integer id;

	//(value = "添加时间")
	private String addtime;

	//(value = "事件类型;事件或报警")
	private String type;

}
