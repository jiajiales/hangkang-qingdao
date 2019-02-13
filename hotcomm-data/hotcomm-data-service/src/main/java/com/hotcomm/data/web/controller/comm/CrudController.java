package com.hotcomm.data.web.controller.comm;

import com.hotcomm.framework.web.exception.HKException;
import com.hotcomm.framework.web.result.ApiResult;

public interface CrudController<P, R, V> {

	public ApiResult get(R id) throws HKException;

	public ApiResult update(P params) throws HKException;

	public ApiResult del(R id) throws HKException;

	public ApiResult add(P params) throws HKException;

}
