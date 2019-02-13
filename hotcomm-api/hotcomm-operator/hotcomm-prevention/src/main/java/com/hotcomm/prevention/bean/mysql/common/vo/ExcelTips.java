package com.hotcomm.prevention.bean.mysql.common.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ExcelTips implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer succesefull;

	private Integer failed;
	
	private List<String> mac;

}
