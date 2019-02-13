package com.hotcomm.prevention.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hotcomm.prevention.utils.ApiResult;

import ch.qos.logback.classic.Logger;

@RestControllerAdvice
public class AllExceptionHandler {

	private Logger log = (Logger) LoggerFactory.getLogger(AllExceptionHandler.class);

	@ResponseBody
	@ExceptionHandler(Exception.class)
	public ApiResult exceptionHandler(Exception e) {
		if (e instanceof MyException) {
			log.error(((MyException) e).getMsg());
			MyException exception = (MyException) e;
			String state = exception.getSystate();
			String msg = exception.getMsg();
			return ApiResult.resultInfo(state, msg,null);
		} else {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			e.printStackTrace(new PrintStream(outputStream));
			String ss = outputStream.toString();
			log.error(ss);
			return ApiResult.resultInfo("SYSTEM00001", "系统异常",null);
		}
	}
}
