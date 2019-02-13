package com.hot.manage.service;

import com.hot.manage.entity.PageInfo;
import com.hot.manage.exception.MyException;

public interface PageService<P,V> {
	
	public PageInfo<V> selectPageInfo(P v)throws MyException;

}
