package com.hot.manage.service.system;

import java.util.List;

import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.PageParam;
import com.hot.manage.entity.system.TMessageLog;
import com.hot.manage.exception.MyException;

public interface TMessageLogService {

	Integer insertOne(TMessageLog message) throws MyException;

	void insertBath(List<TMessageLog> message) throws MyException;
	
	PageInfo<TMessageLog> selectByReceiverId(PageParam param) throws MyException;

	PageInfo<TMessageLog> selectByUserId(PageParam param) throws MyException;

	Integer update(TMessageLog message) throws MyException;
	
	List<TMessageLog> selectUnsent(TMessageLog message)throws MyException;
}
