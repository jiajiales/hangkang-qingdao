package com.hot.manage.entity.common.workorder;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTWorkDevnum implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private Integer devnum;
}
