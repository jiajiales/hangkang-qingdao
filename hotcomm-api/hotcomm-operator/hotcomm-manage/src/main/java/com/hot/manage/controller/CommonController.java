package com.hot.manage.controller;

import com.hot.manage.exception.MyException;
import com.hot.manage.utils.ApiResult;

public interface CommonController<P, M, R> {

	public ApiResult insert(P p,R r) throws MyException;

	public ApiResult update(P p) throws MyException;

	public ApiResult delete(R r) throws MyException;

	public ApiResult select(R r,R b) throws MyException;
}
