package com.hot.manage.service.system;

import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.system.OperationLogPageParam;
import com.hot.manage.entity.system.TOperationLog;
import com.hot.manage.entity.system.TOperationLogVo;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.CommonCrudService;

public interface TOperationLogService extends CommonCrudService<TOperationLog, TOperationLog, Integer> {

	public PageInfo<TOperationLogVo> selectPageInfo(OperationLogPageParam param) throws MyException;

	PageInfo<TOperationLogVo> selectByUserId(OperationLogPageParam param) throws MyException;
	
	Integer insertObject(TOperationLog params) throws MyException;
}
