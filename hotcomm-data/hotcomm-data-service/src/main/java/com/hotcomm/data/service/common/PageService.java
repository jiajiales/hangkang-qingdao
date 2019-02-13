package com.hotcomm.data.service.common;

import com.hotcomm.data.comm.PageInfo;
import com.hotcomm.framework.web.exception.HKException;

public interface PageService<P, V> {

	public PageInfo<V> queryPage(P params) throws HKException;

}
