package com.hotcomm.framework.web.result;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description: 
 * @author  wanpeng http://www.hotcomm.net/
 * @date 2018年3月16日 下午2:21:45
 */
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public abstract class ApiResult {

	protected String code;

	/**
	 * 成功的返回
	 * 
	 * @param data
	 *            数据
	 * @return 正常返回体
	 */
	public static ApiResult success(Object data) {
		return new SuccessApiResult(data);
	}
	
	public static ApiResult success() {
		return ApiResult.success("");
	}
	
	/**
	 * 错误返回
	 * 
	 * @param errorCode
	 *            错误码
	 * @param errorMessage
	 *            错误信息
	 * @return 错误返回体
	 */
	public static ApiResult error(String errorCode, String errorMessage) {
		return new ErrorApiResult(errorCode, errorMessage);
	}

}
