package com.hotcomm.data.web.controller.comm;

import com.hotcomm.data.comm.PageInfo;
import com.hotcomm.framework.web.exception.HKException;

public interface PageController<P, V> {

	public PageInfo<V> page(P params) throws HKException;

}
