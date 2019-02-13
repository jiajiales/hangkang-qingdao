package com.hot.manage.db.yg;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hot.manage.entity.yg.param.YGEvent;
import com.hot.manage.entity.yg.vo.YGEventPartic;
import com.hot.manage.exception.MyException;

public interface YGEventParticMapper {

	/**
	 * 根据时间,紧急程度,上报人,相关设备,设备地址,处理人等内容模糊查询
	 * 
	 * @param userid
	 *            用户id
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param message
	 *            搜索内容
	 * @return
	 * 
	 * @throws MyException
	 */
	Page<YGEventPartic> selectAllEventParticForTime(@Param("userid") Integer userid,
			@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("message") String message)
					throws MyException;

	/**
	 * 根据事件状态,事件描述查询
	 * 
	 * @param userid
	 *            用户id
	 * @param eventstateid
	 *            事件状态
	 * @param state
	 *            事件描述
	 * @return
	 * 
	 * @throws MyException
	 */
	Page<YGEventPartic> selectAllEventParticForState(@Param("userid") Integer userid,
			@Param("eventstateid") Integer eventstateid, @Param("state") Integer state) throws MyException;

	/**
	 * 事件上报,添加一条事件
	 * 
	 * @param ygevent
	 * @return
	 * @throws MyException
	 */
	Integer insertOneEvent(YGEvent ygEvent) throws MyException;

	/**
	 * 新增事件设备关联
	 * 
	 * @param eventid
	 *            事件id
	 * @param devid
	 *            设备id
	 * @return
	 * @throws MyException
	 */
	Integer insertEventDevRel(@Param("eventid") Integer eventid, @Param("devid") Integer devid) throws MyException;

	/**
	 * 
	 * @param id
	 * @param code
	 * @return
	 * @throws MyException
	 */
	Integer updateEventCode(@Param("id") Integer id, @Param("code") String code) throws MyException;

	/**
	 *@param userid
	 *            用户id
	 * @param eventstateid
	 *            事件状态
	 * @param state
	 *            事件描述
	 *            @param userid
	 *            用户id
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param message
	 *            搜索内容
	 * @return
	 * @throws MyException
	 */
	Page<YGEventPartic> selectAllEventParticForMas(@Param("userid") Integer userid,
			@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("message") String message,@Param("eventstateid") Integer eventstateid, @Param("state") Integer state);
}
