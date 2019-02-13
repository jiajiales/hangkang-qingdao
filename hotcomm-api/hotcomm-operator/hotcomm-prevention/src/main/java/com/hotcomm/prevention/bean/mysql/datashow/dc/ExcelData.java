package com.hotcomm.prevention.bean.mysql.datashow.dc;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ExcelData implements Serializable {

	private static final long serialVersionUID = 1L;

	// 表头
	private List<String> titles;

	// 数据
	private List<List<Object>> rows;

	// 页签名称
	private String name;

}
