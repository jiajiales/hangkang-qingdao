package com.hotcomm.prevention.service.manage.alarm;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.hotcomm.prevention.bean.mysql.manage.alarm.AlarmState;
import com.hotcomm.prevention.bean.mysql.manage.alarm.AppAlarmList;
import com.hotcomm.prevention.bean.mysql.manage.alarm.ModuleAlarmCount;
import com.hotcomm.prevention.bean.mysql.manage.alarm.VO.AlarmDeviceFinishVo;
import com.hotcomm.prevention.bean.mysql.manage.alarm.VO.AlarmDeviceHandingVo;
import com.hotcomm.prevention.bean.mysql.manage.alarm.VO.AlarmDeviceInfoVo;
import com.hotcomm.prevention.bean.mysql.manage.alarm.VO.AlarmListVo;
import com.hotcomm.prevention.bean.mysql.manage.alarm.VO.AppAlarmFinish;
import com.hotcomm.prevention.bean.mysql.manage.alarm.VO.HistoricalDateVO;
import com.hotcomm.prevention.bean.mysql.manage.alarm.VO.HistoricalStateDateVo;
import com.hotcomm.prevention.bean.mysql.manage.alarm.param.AlarmHandleParam;
import com.hotcomm.prevention.bean.mysql.manage.alarm.param.AlarmListParam;
import com.hotcomm.prevention.bean.mysql.manage.alarm.param.AlarmStateParam;
import com.hotcomm.prevention.exception.MyException;

public interface AlarmService {

	/**
	 * 根据条件筛选报警记录
	 * 
	 * @param userid
	 *            用户id
	 * @param moduleid
	 *            模块id
	 * @param alarmListParam
	 *            实体类
	 * @return
	 * @throws MyException
	 */
	Page<AlarmListVo> selectAlarmList(Integer pageNum, Integer pageSize,Integer userid, Integer moduleid, AlarmListParam alarmListParam)
			throws MyException;

	/**
	 * 查询所有报警信息
	 * 
	 * @param userid
	 *            用户id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	List<AlarmListVo> selectAlarmMaps(Integer userid, Integer moduleid) throws MyException;

	/**
	 * 根据id 模块id 关闭报警设备
	 * 
	 * @param alarmid
	 *            报警id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	Integer closealarmById(Integer alarmid, Integer moduleid) throws MyException;

	/**
	 * 查询设备报警历史数据
	 * 
	 * @param deviceid
	 *            设备id
	 * @param Theyear
	 *            年份
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	List<HistoricalDateVO> selectHistoricalDate(Integer deviceid, Integer Theyear, Integer moduleid) throws MyException;

	/**
	 * 根据设备id与年份查询历史报警类型
	 * 
	 * @param deviceid
	 *            设备id
	 * @param Theyear
	 *            年份
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	List<HistoricalStateDateVo> selectHistoricalStateDate(Integer deviceid, Integer Theyear, Integer moduleid)
			throws MyException;

	/**
	 * 报警设备详情
	 * 
	 * @param alarmid
	 *            报警id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 * 
	 */
	AlarmDeviceInfoVo selectByAlarmid(Integer alarmid, Integer moduleid) throws MyException;

	/**
	 * 报警处理中情况
	 * 
	 * @param alarmid
	 *            报警id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	AlarmDeviceHandingVo selectAlarmDevicHanding(Integer alarmid, Integer moduleid) throws MyException;

	/**
	 * 报警已处理情况
	 * 
	 * @param alarmid
	 *            报警id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	AlarmDeviceFinishVo selectAlarmDeviceFinish(Integer alarmid, Integer moduleid) throws MyException;

	/**
	 * 根据模块查询所有状态
	 * 
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	List<AlarmState> selectAllState(Integer moduleid) throws MyException;

	/**
	 * 根据状态id 查询单个信息
	 * 
	 * @param stateid
	 *            状态id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	AlarmState selectStateOneByid(Integer stateid, Integer moduleid) throws MyException;

	/**
	 * 修改状态
	 * 
	 * @param alarmStateParam
	 *            实体类
	 * @return
	 * @throws MyException
	 */
	Integer updateStateOneByid(AlarmStateParam alarmStateParam) throws MyException;

	/**
	 * 删除状态
	 * 
	 * @param stateid
	 *            状态id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	Integer deleteStateOneByid(Integer stateid, Integer moduleid) throws MyException;

	/**
	 * 添加状态
	 * 
	 * @param alarmStateParam
	 *            实体类
	 * @return
	 * @throws MyException
	 */
	Integer insertStateOne(AlarmStateParam alarmStateParam) throws MyException;

	/**
	 * app端处理完成详情
	 * 
	 * @param alarmid
	 *            报警id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	AppAlarmFinish selectAppAlarmFinish(Integer alarmid, Integer moduleid) throws MyException;

	/**
	 * 报警处理
	 * 
	 * @param userid
	 *            用户id
	 * @param moduleid
	 *            模块id
	 * @param alarmHandleParam
	 *            实体类
	 * @return
	 * @throws MyException
	 */
	Integer APPAlarmHandle(Integer userid, Integer moduleid, AlarmHandleParam alarmHandleParam) throws MyException;

	/**
	 * APP报警信息
	 * 
	 * @param userid
	 *            用户id
	 * @param moduleid
	 *            模块id
	 * @param state
	 *            状态: 0:未处理/未处理 1:已处理
	 * @param timeout
	 *            时间筛选: 1:1个月 2:3个月
	 * @param message
	 *            根据设备编号/地址筛选
	 * @return
	 * @throws MyException
	 */
	List<AppAlarmList> selectAppAlarmList(Integer userid, Integer moduleid, Integer state, Integer timeout,
			String message) throws MyException;

	/**
	 * 报警总数
	 * 
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	Integer selectAlarmCount(Integer userid, Integer moduleid) throws MyException;

	/**
	 * 模块未处理报警消息数量统计（消息推送调用）
	 * 
	 * @param userid
	 * @return
	 */
	List<ModuleAlarmCount> queryAlarmCount(Integer userid,Integer fatherid);
	
	/**
	 * app轮询当前巡检未处理报警信息
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	List<AlarmDeviceInfoVo> queryUnhandleAlarm(Integer userid) throws MyException;
	
	Map<String, Object> appIndexInfoAboutAlarmAndEvent(Integer userid)throws MyException, ParseException;

}
