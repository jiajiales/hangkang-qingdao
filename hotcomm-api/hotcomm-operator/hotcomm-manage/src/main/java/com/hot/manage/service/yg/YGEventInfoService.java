package com.hot.manage.service.yg;

import java.util.List;

import com.hot.manage.entity.yg.vo.YGEventDeviceRele;
import com.hot.manage.entity.yg.vo.YGEventFinish;
import com.hot.manage.entity.yg.vo.YGEventHandling;
import com.hot.manage.entity.yg.vo.YGEventInfo;
import com.hot.manage.entity.yg.vo.YGEventInstructRele;
import com.hot.manage.exception.MyException;

public interface YGEventInfoService {

	/**
	 * 根据事件id查询事件内容
	 * 
	 * @param eventid
	 * @return
	 * @throws MyException
	 */
	YGEventInfo selectYGEventInfo(Integer eventid) throws MyException;

	/**
	 * 根据事件id查询关联的设备
	 * 
	 * @param eventid
	 * @return
	 * @throws MyException
	 */
	List<YGEventDeviceRele> selectEventDevice(Integer eventid) throws MyException;

	/**
	 * 根据事件id查询关联的工作指示
	 * 
	 * @param eventid
	 * @return
	 * @throws MyException
	 */
	List<YGEventInstructRele> selectEventInstruct(Integer eventid) throws MyException;

	/**
	 * 根据事件id查询事件处理详情(处理中)
	 * 
	 * @param eventid
	 * @return
	 * @throws MyException
	 */
	YGEventHandling selectEventHandling(Integer eventid) throws MyException;

	/**
	 * 根据事件id查询事件处理详情(完成)
	 * 
	 * @param eventid
	 * @return
	 * @throws MyException
	 */
	YGEventFinish selectEventFinish(Integer eventid) throws MyException;
}
