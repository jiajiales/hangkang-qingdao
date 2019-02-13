package com.hot.analysis.bean.ywj;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTYWJselectFlipNum implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// (value = "设备类型")
	private Integer moduleid;

	// (value = "查询类型")
	private Integer querytype;

	// (value = "设备编码")
	private Integer devid;

	// (value = "数量")
	private Integer count;

	// (value = "设备地址/名称")
	private String devname;

	// (value = "维度")
	private String lat;

	// (value = "经度")
	private String lng;

}
