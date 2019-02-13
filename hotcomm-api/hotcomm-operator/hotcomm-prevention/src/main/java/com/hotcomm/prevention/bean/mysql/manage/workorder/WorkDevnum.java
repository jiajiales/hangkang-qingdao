package com.hotcomm.prevention.bean.mysql.manage.workorder;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class WorkDevnum implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private Integer devnum;
}
