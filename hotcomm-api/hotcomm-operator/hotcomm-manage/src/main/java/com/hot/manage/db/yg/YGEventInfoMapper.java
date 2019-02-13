package com.hot.manage.db.yg;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hot.manage.entity.yg.vo.YGEventDeviceRele;
import com.hot.manage.entity.yg.vo.YGEventFinish;
import com.hot.manage.entity.yg.vo.YGEventHandling;
import com.hot.manage.entity.yg.vo.YGEventInfo;
import com.hot.manage.entity.yg.vo.YGEventInstructRele;
import com.hot.manage.exception.MyException;

public interface YGEventInfoMapper {

	/**
	 * 事件内容
	 * 
	 * @param eventid
	 *            事件id
	 * @return
	 * @throws MyException
	 */
	YGEventInfo selectYGEventInfo(@Param("eventid") Integer eventid) throws MyException;

	/**
	 * 事件关联的设备
	 * 
	 * @param eventid
	 *            事件id
	 * @return
	 * @throws MyException
	 */
	List<YGEventDeviceRele> selectEventDevice(@Param("eventid") Integer eventid) throws MyException;

	/**
	 * 事件关联的工作指示
	 * 
	 * @param eventid
	 *            事件id
	 * @return
	 * @throws MyException
	 */
	List<YGEventInstructRele> selectEventInstruct(@Param("eventid") Integer eventid) throws MyException;

	/**
	 * 事件处理详情(处理中)
	 * 
	 * @param eventid
	 *            事件id
	 * @return
	 * @throws MyException
	 */
	YGEventHandling selectEventHandling(@Param("eventid") Integer eventid) throws MyException;

	/**
	 * 事件处理详情(完成)
	 * 
	 * @param eventid
	 *            事件id
	 * @return
	 * @throws MyException
	 */
	YGEventFinish selectEventFinish(@Param("eventid") Integer eventid) throws MyException;

	
}
