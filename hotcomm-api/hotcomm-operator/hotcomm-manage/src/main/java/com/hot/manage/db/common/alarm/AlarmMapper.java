package com.hot.manage.db.common.alarm;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hot.manage.entity.common.ModuleAlarmCount;
import com.hot.manage.entity.common.alarm.AlarmDeviceFinishVo;
import com.hot.manage.entity.common.alarm.AlarmDeviceHandingVo;
import com.hot.manage.entity.common.alarm.AlarmDeviceInfoVo;
import com.hot.manage.entity.common.alarm.AlarmInfo;
import com.hot.manage.entity.common.alarm.AlarmListParam;
import com.hot.manage.entity.common.alarm.AlarmListVo;
import com.hot.manage.entity.common.alarm.AppAlarmFinish;
import com.hot.manage.entity.common.alarm.AppAlarmList;
import com.hot.manage.entity.common.alarm.HistoricalDateVO;
import com.hot.manage.entity.common.alarm.HistoricalStateDateVo;
import com.hot.manage.entity.common.alarm.PictureUrlVo;
import com.hot.manage.entity.common.alarm.T_dev_alarm;
import com.hot.manage.entity.common.alarm.VoiceUrlVo;
import com.hot.manage.exception.MyException;

import tk.mybatis.mapper.common.Mapper;

public interface AlarmMapper extends Mapper<T_dev_alarm> {

	/**
	 * 根据条件筛选报警记录
	 * 
	 * @param alarmListParam
	 *            实体类
	 * @param userid
	 *            用户id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	Page<AlarmListVo> selectAlarmList(@Param("alarmListParam") AlarmListParam alarmListParam,
			@Param("userid") Integer userid, @Param("moduleid") Integer moduleid) throws MyException;

	/**
	 * 查询全部报警
	 * 
	 * @param userid
	 *            用户id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	List<AlarmListVo> selectAlarmMaps(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid)
			throws MyException;

	/**
	 * 根据id关闭报警设备
	 * 
	 * @param alarmid
	 *            报警id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	Integer closealarmById(@Param("alarmid") Integer alarmid, @Param("moduleid") Integer moduleid) throws MyException;

	/**
	 * 根据年份查询设备历史数据
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
	List<HistoricalDateVO> selectHistoricalDate(@Param("deviceid") Integer deviceid, @Param("Theyear") Integer Theyear,
			@Param("moduleid") Integer moduleid) throws MyException;

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
	List<HistoricalStateDateVo> selectHistoricalStateDate(@Param("deviceid") Integer deviceid,
			@Param("Theyear") Integer Theyear, @Param("moduleid") Integer moduleid) throws MyException;

	/**
	 * 报警设备详情
	 * 
	 * @param alarmid
	 *            报警id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	AlarmDeviceInfoVo selectByAlarmid(@Param("alarmid") Integer alarmid, @Param("moduleid") Integer moduleid)
			throws MyException;

	/**
	 * 获取语音资源路径
	 * 
	 * @param id
	 *            事件/报警id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	List<VoiceUrlVo> getVoice(@Param("id") Integer id, @Param("moduleid") Integer moduleid) throws MyException;

	/**
	 * 获取图片资源
	 * 
	 * @param id
	 *            事件/报警id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	List<PictureUrlVo> getpicture(@Param("id") Integer id, @Param("moduleid") Integer moduleid) throws MyException;

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
	AlarmDeviceHandingVo selectAlarmDevicHanding(@Param("alarmid") Integer alarmid, @Param("moduleid") Integer moduleid)
			throws MyException;

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
	AlarmDeviceFinishVo selectAlarmDeviceFinish(@Param("alarmid") Integer alarmid, @Param("moduleid") Integer moduleid)
			throws MyException;

	/**
	 * 是否转工单处理
	 * 
	 * @param alarmid
	 *            报警id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	Integer getishandler(@Param("alarmid") Integer alarmid, @Param("moduleid") Integer moduleid) throws MyException;

	/**
	 * app端处理完成详情(已转工单)
	 * 
	 * @param alarmid
	 *            报警id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	AppAlarmFinish selectAppAlarmFinishToWo(@Param("alarmid") Integer alarmid, @Param("moduleid") Integer moduleid)
			throws MyException;

	/**
	 * app端处理完成详情(未转工单)
	 * 
	 * @param alarmid
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	AppAlarmFinish selectAppAlarmFinish(@Param("alarmid") Integer alarmid, @Param("moduleid") Integer moduleid)
			throws MyException;

	/**
	 * 创建临时表(App)
	 * 
	 * @return
	 * @throws MyException
	 */
	Integer createTemporaryTable() throws MyException;

	/**
	 * 删除临时表(App)
	 * 
	 * @return
	 * @throws MyException
	 */
	Integer dropTemporaryTable() throws MyException;

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
	List<AppAlarmList> getAppAlarmList(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid,
			@Param("state") Integer state, @Param("timeout") Integer timeout, @Param("message") String message)
					throws MyException;

	/**
	 * 报警总数
	 * 
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	Integer selectAlarmCount(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid) throws MyException;
	
	/**
	 *模块未处理报警消息数量统计（消息推送调用）
	 * @param userid
	 * @return
	 */
	List<ModuleAlarmCount> queryAlarmCount(@Param("userid") Integer userid);
	
	/**
	 * app轮询当前巡检未处理报警
	 * @param userid
	 * @return
	 */
	List<AlarmInfo> queryUnhandleAlarm(@Param("userid")Integer userid);
	
	/**
	 * 查询未处理报警对应的设备、项目信息
	 * @return
	 */
	AlarmDeviceInfoVo queryDevByAlarm(@Param("deviceid")Integer deviceid,@Param("moduleid")Integer moduleid);
}
