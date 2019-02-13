package com.hotcomm.prevention.service.manage.message;

import java.util.List;

import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.bean.mysql.manage.PageParam;
import com.hotcomm.prevention.bean.mysql.manage.message.TMessageLog;
import com.hotcomm.prevention.exception.MyException;

public interface TMessageLogService {

	Integer insertOne(TMessageLog message) throws MyException;

	void insertBath(List<TMessageLog> message) throws MyException;
	
	PageInfo<TMessageLog> selectByReceiverId(PageParam param) throws MyException;

	PageInfo<TMessageLog> selectByUserId(PageParam param) throws MyException;

	Integer update(TMessageLog message) throws MyException;
	
	List<TMessageLog> selectUnsent(TMessageLog message)throws MyException;
}
