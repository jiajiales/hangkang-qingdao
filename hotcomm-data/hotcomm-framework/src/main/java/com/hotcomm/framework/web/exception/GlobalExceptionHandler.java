package com.hotcomm.framework.web.exception;

import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hotcomm.framework.log.LogManager;
import com.hotcomm.framework.utils.SpringUtil;
import com.hotcomm.framework.web.result.ApiResult;

/**
 * @Description: 
 * @author wanpeng http://www.hotcomm.net/
 * @date 2018年3月16日 下午2:18:29
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ResponseBody
	@ExceptionHandler(Exception.class)
	public ApiResult serviceErrorHandler(Exception e) {
		LogManager manager = SpringUtil.getBean(LogManager.class);
		Logger log  = null;
		try {
			log = manager.getErrorLog();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (e instanceof HKException) { // 如果是自定义的异常，返回对应的错误信息
			String uuid = UUID.randomUUID().toString();
			HKException exception = (HKException) e;
			String errCode = exception.getCode();
			String errMsg = exception.getMsg();
			log.error("业务异常-"+uuid+":"+errCode+":"+errMsg);
			log.error("业务异常-"+uuid+"",e);
			return ApiResult.error(errCode, errMsg);
		} else { //返回系统异常
			String uuid = UUID.randomUUID().toString();
			log.error("SYS_EXCEPTION-"+uuid+":系统异常");
			log.error("SYS_EXCEPTION-"+uuid, e);
			return ApiResult.error("SYS_EXCEPTION", "系统异常");
		}
	}
}
