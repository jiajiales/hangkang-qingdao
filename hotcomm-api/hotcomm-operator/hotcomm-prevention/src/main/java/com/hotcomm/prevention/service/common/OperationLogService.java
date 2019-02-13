package com.hotcomm.prevention.service.common;

import com.hotcomm.prevention.bean.mysql.common.entity.TOperationLog;
import com.hotcomm.prevention.bean.mysql.common.params.OperationLogPageParam;
import com.hotcomm.prevention.bean.mysql.common.vo.TOperationLogVo;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.exception.MyException;

public interface OperationLogService {

	void insert(TOperationLog log) throws MyException;
	
	PageInfo<TOperationLogVo> selectPageInfo(OperationLogPageParam param)throws MyException;

}
