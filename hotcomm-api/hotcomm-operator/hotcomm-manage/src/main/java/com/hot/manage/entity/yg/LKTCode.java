package com.hot.manage.entity.yg;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LKTCode implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private Integer code;

}
