package com.hot.manage.service.yg;

import com.hot.manage.entity.yg.vo.YGDeviceInfoDispose;
import com.hot.manage.entity.yg.vo.YGDeviceInfoFinish;
import com.hot.manage.entity.yg.vo.YG_DeviceInfo;
import com.hot.manage.exception.MyException;

public interface YGDeviceInfoService {

	/**
	 * 设备详情
	 * 
	 * @param ygalarmid
	 * @return
	 * @throws MyException
	 */
	YG_DeviceInfo selectYGDeviceInfo(Integer ygalarmid) throws MyException;

	/**
	 * 设备处理中详情
	 * 
	 * @param ygalarmid
	 * @return
	 * @throws MyException
	 */
	YGDeviceInfoDispose selectDevInfoDispose(Integer ygalarmid) throws MyException;

	/**
	 * 设备处理完成详情
	 * 
	 * @param ygalarmid
	 * @return
	 * @throws MyException
	 */
	YGDeviceInfoFinish selectDevInfoFinish(Integer ygalarmid) throws MyException;
	
	
}
