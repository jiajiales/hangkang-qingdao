package com.hot.manage.service.yg;

import com.hot.manage.entity.yg.param.TAlarmDispose;
import com.hot.manage.entity.yg.param.YGEventParam;
import com.hot.manage.exception.MyException;

public interface YGAppService {

	/**
	 * 
	 * @param ygEventParam
	 * @return
	 * @throws MyException
	 */
	Integer insertEvent(YGEventParam ygEventParam)throws MyException;
	
	/**
	 * 报警设备处理
	 * @param talarmDispose 报警处理实体类对象
	 * @return
	 * @throws MyException
	 */
	Integer DevInfoDispose(TAlarmDispose talarmDispose)throws MyException;
}
