package com.hotcomm.framework.web.result;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description: 
 * @author  wanpeng http://www.hotcomm.net/
 * @date 2018年3月16日 下午2:21:53
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SuccessApiResult extends ApiResult {
	
	private Object data;

	public SuccessApiResult(Object data) {
		this.code = "0";
		this.data = data;
	}
}
