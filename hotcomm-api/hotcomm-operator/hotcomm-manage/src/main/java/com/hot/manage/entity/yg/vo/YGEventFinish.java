package com.hot.manage.entity.yg.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class YGEventFinish implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// (value = "事件处理状态，0：未处理；1：已派单；2：挂起；3：处理完毕")
	private Integer state;

	// (value = "问题描述")
	private String problemdesc;

	// (value = "相关照片")
	private String prictureUrl;

	// (value = "处理人")
	private String contacts;

	// (value = "联系电话")
	private String telephone1;

}
