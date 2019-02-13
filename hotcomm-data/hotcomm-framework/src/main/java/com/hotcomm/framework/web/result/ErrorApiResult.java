package com.hotcomm.framework.web.result;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description: 
 * @author  wanpeng http://www.hotcomm.net/
 * @date 2018年3月16日 下午2:21:49
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ErrorApiResult extends ApiResult {

	private String msg;

	ErrorApiResult(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
