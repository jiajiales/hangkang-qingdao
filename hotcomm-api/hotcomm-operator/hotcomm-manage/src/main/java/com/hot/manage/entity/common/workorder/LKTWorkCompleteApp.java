package com.hot.manage.entity.common.workorder;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTWorkCompleteApp extends LKTWorkDetailedApp implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// (value = "故障描述 ")
	private String problemdesc;
	
	// (value = "处理描述 ")
	private String remark;

	// (value = "处理图片资源路径;多个的时候用中文分号；隔开-如:资源1；资源2；资源3 ")
	private String pictureurl;

	// (value = "处理方式，1：维修，2：挂起，3：更换")
	private Integer handleType;
	
}
