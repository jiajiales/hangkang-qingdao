package com.hot.manage.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class MyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String systate;// 操作状态;
	private String msg;

	public MyException(String systate, String msg) {
		this.systate = systate;
		this.msg = msg;
	}

}
