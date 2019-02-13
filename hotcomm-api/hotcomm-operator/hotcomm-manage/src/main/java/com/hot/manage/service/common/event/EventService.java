package com.hot.manage.service.common.event;

import java.util.List;

import com.github.pagehelper.Page;
import com.hot.manage.entity.common.event.AppEventInfo;
import com.hot.manage.entity.common.event.AppEventList;
import com.hot.manage.entity.common.event.EventDeviceRele;
import com.hot.manage.entity.common.event.EventFinishVo;
import com.hot.manage.entity.common.event.EventHandling;
import com.hot.manage.entity.common.event.EventInfoVo;
import com.hot.manage.entity.common.event.EventInstructRele;
import com.hot.manage.entity.common.event.EventListParam;
import com.hot.manage.entity.common.event.EventListVo;
import com.hot.manage.entity.common.event.EventParam;
import com.hot.manage.entity.common.event.T_event_state;
import com.hot.manage.entity.common.event.T_module;
import com.hot.manage.exception.MyException;

public interface EventService {

	/**
	 * 事件工作指示
	 * 
	 * @param eventid
	 *            事件id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	List<EventInstructRele> selectWorkInstruction(Integer eventid, Integer moduleid) throws MyException;

	/**
	 * 事件关联设备
	 * 
	 * @param eventid
	 *            事件id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	List<EventDeviceRele> selectEventDeviceRe(Integer eventid, Integer moduleid) throws MyException;

	/**
	 * 事件详情
	 * 
	 * @param eventid
	 *            事件id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	EventInfoVo selectEventInfo(Integer eventid, Integer moduleid) throws MyException;

	/**
	 * 事件处理中详情
	 * 
	 * @param eventid
	 *            事件id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	EventHandling selectEventHandling(Integer eventid, Integer moduleid) throws MyException;

	/**
	 * 事件处理完成详情
	 * 
	 * @param eventid
	 *            事件id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	EventFinishVo selectEventFinish(Integer eventid, Integer moduleid) throws MyException;

	/**
	 * 根据提交筛选列表
	 * 
	 * @param eventListParam
	 *            实体类
	 * @param userid
	 *            用户id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	Page<EventListVo> selectEventList(EventListParam eventListParam, Integer userid, Integer moduleid)
			throws MyException;

	/**
	 * 事件地图展示
	 * @param userid
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	List<EventListVo> selectEventListMaps(Integer userid ,Integer moduleid)throws MyException;
	/**
	 * 查询所有事件状态
	 * 
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	List<T_event_state> selectAllEventState(Integer moduleid) throws MyException;

	/**
	 * 查询单个事件状态
	 * 
	 * @param stateid
	 *            事件状态id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	T_event_state selectEventStateByOne(Integer stateid, Integer moduleid) throws MyException;

	/**
	 * 根据id修改事件状态
	 * 
	 * @param eventAlarm
	 *            实体类
	 * @return
	 * @throws MyException
	 */
	Integer updateEventStateByOne(T_event_state eventAlarm) throws MyException;

	/**
	 * 根据id删除状态
	 * 
	 * @param stateid
	 *            事件状态id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	Integer deleteEventStateByOne(Integer stateid, Integer moduleid) throws MyException;

	/**
	 * 新增事件状态
	 * 
	 * @param eventAlarm
	 *            实体类
	 * @return
	 * @throws MyException
	 */
	Integer insertEventStateOne(T_event_state eventAlarm) throws MyException;

	/**
	 * APP端事件上报
	 * 
	 * @param eventParam
	 *            实体类
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	Integer insertOneEvent(Integer userid, EventParam eventParam, Integer moduleid) throws MyException;

	/**
	 * APP端事件详情
	 * 
	 * @param moduleid
	 *            模块id
	 * @param eventid
	 *            事件id
	 * @return
	 * @throws MyException
	 */
	AppEventInfo selectAPPEventInfo(Integer moduleid, Integer eventid) throws MyException;

	/**
	 * 关闭事件
	 * 
	 * @param eventid
	 *            事件id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	Integer closeEvent(Integer eventid, Integer moduleid) throws MyException;

	/**
	 * 查询模块信息
	 * 
	 * @return
	 * @throws MyException
	 */
	List<T_module> selectAPPModule() throws MyException;

	/**
	 * APP上报记录
	 * 
	 * @param moduleid
	 *            模块id
	 * @param timeout
	 *            时间选择: 不填:全部; 1:1个月前 2:3个月前
	 * @param message
	 *            根据设备编号或者地址 筛选
	 * @return
	 * @throws MyException
	 */
	List<AppEventList> selectAppEventList(Integer moduleid, Integer timeout, String message) throws MyException;
	
	/**
	 * 事件总数
	 * @param userid
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	Integer selectEventCount(Integer userid, Integer moduleid) throws MyException;
}
