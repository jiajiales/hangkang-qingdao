package com.hotcomm.data.service.common;

import com.hotcomm.framework.web.exception.HKException;

public interface CommFunService<P, V, R> {

	public R addBean(P params) throws HKException;

	public void delBean(R id) throws HKException;

	public void updateBean(P params) throws HKException;

	public V getBean(R id) throws HKException;

}
