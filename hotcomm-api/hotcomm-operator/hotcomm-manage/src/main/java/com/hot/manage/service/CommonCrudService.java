package com.hot.manage.service;

import com.hot.manage.exception.MyException;

public interface CommonCrudService<P, V, R> {

	public R insertObject(P params,R r) throws MyException;
	
	public R updateObject(P params)throws MyException;
	
	public R delObject(R id) throws MyException;
	
	public V selectObject(R id) throws MyException;
	
}
