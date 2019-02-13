package com.hot.manage.service.yg;


import com.github.pagehelper.Page;
import com.hot.manage.entity.yg.vo.YGEventPartic;
import com.hot.manage.exception.MyException;

public interface YGEventParticService {

	/**
	 * 根据报警类型,上报人,上报时间,编号,地址,处理人搜索烟感事件
	 * 
	 * @param userid
	 * @param startTime
	 * @param endTime
	 * @param message
	 * @return
	 * @throws MyException
	 */
	Page<YGEventPartic> selectAllEventParticForTime(Integer userid, String startTime, String endTime, String message)
			throws MyException;

	/**
	 * 根据事件类型,事件状态搜索烟感事件. (eventstateid:关联t_alarm_state的id;-1,查全部;
	 * state:-1,查全部;0,未处理;1,已派单;2,挂起;3,处理完毕;)
	 * 
	 * @param userid
	 * @param eventstateid
	 * @param state
	 * @return
	 * @throws MyException
	 */
	Page<YGEventPartic> selectAllEventParticForState(Integer userid, Integer eventstateid, Integer state)
			throws MyException;
	/**
	 * 根据事件类型,上报时间,编号,地址,处理人,事件状态搜索烟感事件. (eventstateid:关联t_alarm_state的id;-1,查全部;
	 * state:-1,查全部;0,未处理;1,已派单;2,挂起;3,处理完毕;)
	 * 
	 * @param userid
	 * @param eventstateid
	 * @param state
	 * @param startTime
	 * @param endTime
	 * @param message
	 * @return
	 * @throws MyException
	 */
	Page<YGEventPartic> selectAllEventParticForMas(String startTime, String endTime, String message, Integer userid,
			Integer eventstateid, Integer state)
					throws MyException;;

	
}
