package com.hot.manage.db.common.event;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hot.manage.entity.common.alarm.PictureUrlVo;
import com.hot.manage.entity.common.alarm.VoiceUrlVo;
import com.hot.manage.entity.common.event.AppEventInfo;
import com.hot.manage.entity.common.event.AppEventList;
import com.hot.manage.entity.common.event.EventDeviceRele;
import com.hot.manage.entity.common.event.EventFinishVo;
import com.hot.manage.entity.common.event.EventHandling;
import com.hot.manage.entity.common.event.EventInfoVo;
import com.hot.manage.entity.common.event.EventInstructRele;
import com.hot.manage.entity.common.event.EventListParam;
import com.hot.manage.entity.common.event.EventListVo;
import com.hot.manage.entity.common.event.T_hk_event;
import com.hot.manage.entity.common.event.T_module;
import com.hot.manage.exception.MyException;

import tk.mybatis.mapper.common.Mapper;

public interface EventMapper extends Mapper<T_hk_event> {

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
	Integer closeEvent(@Param("eventid") Integer eventid, @Param("moduleid") Integer moduleid) throws MyException;

	/**
	 * 事件工作指示
	 * 
	 * @param eventid
	 *            事件id
	 * @param moduleid
	 *            模块id
	 * @param type
	 *            类型
	 * @return
	 * @throws MyException
	 */
	List<EventInstructRele> selectWorkInstruction(@Param("eventid") Integer eventid,
			@Param("moduleid") Integer moduleid, @Param("type") Integer type) throws MyException;

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
	List<EventDeviceRele> selectEventDeviceRe(@Param("eventid") Integer eventid, @Param("moduleid") Integer moduleid)
			throws MyException;

	/**
	 * 获取语音资源
	 * 
	 * @param eventid
	 *            事件id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	List<VoiceUrlVo> getVoiceUrl(@Param("eventid") Integer eventid, @Param("moduleid") Integer moduleid)
			throws MyException;

	/**
	 * 获取图片资源
	 * 
	 * @param eventid
	 *            事件id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	List<PictureUrlVo> getPictureUrl(@Param("eventid") Integer eventid, @Param("moduleid") Integer moduleid)
			throws MyException;

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
	EventInfoVo selectEventInfo(@Param("eventid") Integer eventid, @Param("moduleid") Integer moduleid)
			throws MyException;

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
	EventHandling selectEveintHanding(@Param("eventid") Integer eventid, @Param("moduleid") Integer moduleid)
			throws MyException;

	/**
	 * 判断是否转工单处理
	 * 
	 * @param eventid
	 *            事件id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	Integer getishander(@Param("eventid") Integer eventid, @Param("moduleid") Integer moduleid) throws MyException;

	/**
	 * 事件处理完成详情(转工单处理)
	 * 
	 * @param eventid
	 *            事件id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	EventFinishVo eventFinishToWo(@Param("eventid") Integer eventid, @Param("moduleid") Integer moduleid)
			throws MyException;

	/**
	 * 获取事件处理完成的图片路径资源(转工单处理)
	 * 
	 * @param woid
	 *            工单id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	List<PictureUrlVo> getFinishWoPictureUrl(@Param("woid") Integer woid, @Param("moduleid") Integer moduleid)
			throws MyException;

	/**
	 * 事件处理完成(无转工单)
	 * 
	 * @param eventid
	 *            事件id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	EventFinishVo eventFinishToEvent(@Param("eventid") Integer eventid, @Param("moduleid") Integer moduleid)
			throws MyException;

	/**
	 * 获取事件处理完成的图片路径资源(无转工单处理)
	 * 
	 * @param eventid
	 *            事件id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	List<PictureUrlVo> getFinishEventPictureUrl(@Param("eventid") Integer eventid, @Param("moduleid") Integer moduleid)
			throws MyException;

	/**
	 * 根据条件筛选事件列表
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
	Page<EventListVo> selectEventList(@Param("eventListParam") EventListParam eventListParam,
			@Param("userid") Integer userid, @Param("moduleid") Integer moduleid) throws MyException;

	/**
	 * 事件地图展示
	 * 
	 * @param userid
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	List<EventListVo> selectEventListMaps(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid)
			throws MyException;

	/**
	 * 修改设备状态
	 * 
	 * @param moduleid
	 *            模块id
	 * @param type
	 *            状态id
	 * @param devid
	 *            设备id
	 * @return
	 * @throws MyException
	 */
	Integer updateDeviceState(@Param("moduleid") Integer moduleid, @Param("type") Integer type,
			@Param("devid") Integer devid) throws MyException;

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
	AppEventInfo selectAPPEventInfo(@Param("moduleid") Integer moduleid, @Param("eventid") Integer eventid)
			throws MyException;

	/**
	 * APP端事件详情(转工单)
	 * 
	 * @param moduleid
	 *            模块id
	 * @param eventid
	 *            事件id
	 * @return
	 * @throws MyException
	 */
	AppEventInfo selectAPPEventInfoToWo(@Param("moduleid") Integer moduleid, @Param("eventid") Integer eventid)
			throws MyException;

	/**
	 * 获取模块
	 * 
	 * @return
	 * @throws MyException
	 */
	List<T_module> selectAPPModule() throws MyException;

	/**
	 * 创建事件临时表(APP端)
	 * 
	 * @return
	 * @throws MyException
	 */
	Integer createTemporaryTable() throws MyException;

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
	List<AppEventList> getAppEventList(@Param("moduleid") Integer moduleid, @Param("timeout") Integer timeout,
			@Param("message") String message) throws MyException;

	/**
	 * 删除临时表(APP端)
	 * 
	 * @return
	 * @throws MyException
	 */
	Integer dropTemporaryTable() throws MyException;

	/**
	 * 
	 * @param userid
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	Integer selectEventCount(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid) throws MyException;

	/**
	 * 根据设备id查询事件是否有重复
	 * 
	 * @param moduleid
	 *            模块id
	 * @param devid
	 *            设备id
	 * @return
	 * @throws MyException
	 */
	Integer selectEventCountByDevid(@Param("moduleid") Integer moduleid, @Param("devid") Integer devid)
			throws MyException;
}
