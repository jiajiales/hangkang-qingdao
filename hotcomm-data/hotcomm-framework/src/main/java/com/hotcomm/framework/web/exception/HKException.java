package com.hotcomm.framework.web.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description: 
 * @author  wanpeng http://www.hotcomm.net/
 * @date 2018年3月16日 下午2:18:21
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class HKException extends RuntimeException {

	private static final long serialVersionUID = 8961415773151982244L;
	
	private String code;

	private String msg;
	
	public HKException(String code, String msg) {
		super(code);
		this.code = code;
		this.msg = msg;
	}

}
