package com.hot.manage.utils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description: TODO
 * @author wanpeng http://www.hotcomm.net/
 * @date 2018年3月16日 下午2:21:45
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ApiResult {

	private String systate;// 操作状态
	private String msg;
	private Object data;

	public ApiResult(String systate, String msg, Object data) {
		this.systate = systate;
		this.msg = msg;
		this.data = data;
	}

	public static ApiResult resultInfo(String systate, String msg, Object data) {	
		return new ApiResult(systate, msg, data);
	}
}
