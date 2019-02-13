package com.hot.manage.controller;

import com.hot.manage.exception.MyException;
import com.hot.manage.utils.ApiResult;

public interface PageController<P, M> {

	public ApiResult page(P p) throws MyException;

}
