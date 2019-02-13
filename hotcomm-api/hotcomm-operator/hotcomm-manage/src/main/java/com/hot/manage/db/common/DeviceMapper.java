package com.hot.manage.db.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hot.manage.entity.common.AlarmHandleNums;
import com.hot.manage.entity.common.AlarmNums;
import com.hot.manage.entity.common.AllDevByUserid;
import com.hot.manage.entity.common.AllGroupByUserId;
import com.hot.manage.entity.common.DevOptionalUser;
import com.hot.manage.entity.common.DeviceForDevnum;
import com.hot.manage.entity.common.DeviceHandleTime;
import com.hot.manage.entity.common.DeviceInfo;
import com.hot.manage.entity.common.DeviceInsertParam;
import com.hot.manage.entity.common.DeviceType;
import com.hot.manage.entity.common.GroupInfo;
import com.hot.manage.entity.common.OptionalGroup;
import com.hot.manage.entity.common.alarm.AlarmDev;
import com.hot.manage.exception.MyException;

public interface DeviceMapper {

	/**
	 * 设备详情
	 * 
	 * @param deviceid
	 *            设备id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	DeviceInfo AppDeviceInfo(@Param("deviceid") Integer deviceid, @Param("moduleid") Integer moduleid)
			throws MyException;

	/**
	 * 设备故障详情
	 * 
	 * @param deviceid
	 *            设备id
	 * @param moduleid
	 *            模块id
	 * @return
	 * @throws MyException
	 */
	List<DeviceHandleTime> getDeviceAlarmHandleTime(@Param("deviceid") Integer deviceid,
			@Param("moduleid") Integer moduleid) throws MyException;

	/**
	 * 设备组关联(添加)
	 * 
	 * @param devid
	 * @param groupid
	 * @param moduleid
	 * @param addtime
	 * @param adduserid
	 * @return
	 * @throws MyException
	 */
	Integer insertDevReGroup(@Param("devid") Integer devid, @Param("groupid") Integer groupid,
			@Param("moduleid") Integer moduleid, @Param("addtime") String addtime, @Param("userid") Integer userid)
					throws MyException;

	/**
	 * 设备联动摄像头(添加)
	 * 
	 * @param devid
	 * @param videoid
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	Integer insertDevReVideo(@Param("devid") Integer devid, @Param("videoid") Integer videoid,
			@Param("moduleid") Integer moduleid) throws MyException;

	/**
	 * 设备楼层(添加)
	 * 
	 * @param itempicid
	 * @param devid
	 * @param moduleid
	 * @param addtime
	 * @return
	 * @throws MyException
	 */
	Integer insertDevItemPic(@Param("itempicid") Integer itempicid, @Param("devid") Integer devid,
			@Param("moduleid") Integer moduleid, @Param("addtime") String addtime) throws MyException;

	/**
	 * 设备安装
	 * 
	 * @param moduleid
	 *            模块id
	 * @param userid
	 *            用户id
	 * @param deviceInsertParam
	 *            传入参数
	 * @return
	 * @throws MyException
	 */
	Integer insertAPPDevice(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid,
			@Param("deviceInsertParam") DeviceInsertParam deviceInsertParam) throws MyException;

	/**
	 * 可选项目组
	 * 
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	List<OptionalGroup> selectOptionalGroup(@Param("userid") Integer userid) throws MyException;

	/**
	 * 可选责任人
	 * 
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	List<DevOptionalUser> selectDevOptionalUser(@Param("userid") Integer userid) throws MyException;

	/**
	 * 查询指定设备的责任人
	 * 
	 * @return
	 */
	AlarmDev selectDevForUser(@Param("deviceid") Integer deviceid, @Param("moduleid") Integer moduleid);

	/**
	 * 根据设备编号查找设备信息
	 * 
	 * @param devnum
	 *            设备编号
	 * @return
	 * @throws MyException
	 */
	DeviceForDevnum selectAppDeviceforDevnum(@Param("devnum") String devnum) throws MyException;

	
	/**
	 * 根据mac地址查找设备信息
	 * @param mac
	 * @return
	 * @throws MyException
	 */
	DeviceForDevnum selectAppDeviceforMac(@Param("mac") String mac)throws MyException;
	/**
	 * app端获取设备类型
	 * 
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	List<DeviceType> selectAppDeviceType(@Param("moduleid") Integer moduleid) throws MyException;

	/**
	 * 报警类型统计
	 * 
	 * @param moduleid
	 *            模块id
	 * @param Time
	 *            年份
	 * @param userid
	 *            用户id
	 * @return
	 * @throws MyException
	 */

	List<AlarmNums> selectAlarmNums(@Param("moduleID") Integer moduleID, @Param("userid") Integer userid,
			@Param("queryType") Integer queryType);

	/**
	 * 报警类型统计
	 * 
	 * @param moduleid
	 *            模块id
	 * 
	 * @param userid
	 *            用户id
	 * @param queryType
	 * @return
	 * @throws MyException
	 */
	List<AlarmHandleNums> selectAlarmHandleNums(@Param("moduleID") Integer moduleID, @Param("userid") Integer userid,
			@Param("queryType") Integer queryType);

	/**
	 * 报警类型统计
	 * 
	 * @param moduleid
	 *            模块id
	 * 
	 * @param userid
	 *            用户id
	 * @param queryType
	 * 
	 * @param groupid
	 * @return
	 * @throws MyException
	 */
	List<AlarmHandleNums> selectGroupAlarmHandleNums(@Param("moduleID") Integer moduleID,
			@Param("userid") Integer userid, @Param("queryType") Integer queryType, @Param("groupid") Integer groupid);

	/**
	 * 报警类型统计
	 * 
	 * @param moduleid
	 *            模块id
	 * 
	 * @param userid
	 *            用户id
	 * @param queryType
	 * 
	 * @param groupid
	 * @return
	 * @throws MyException
	 */
	List<AlarmNums> selectGroupAlarmNums(@Param("moduleID") Integer moduleID, @Param("userid") Integer userid,
			@Param("queryType") Integer queryType, @Param("groupid") Integer groupid);

	List<AllGroupByUserId> selectAllGroupByUserId(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid);

	List<AllDevByUserid> selectAllDevByUserid(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid);

	/**
	 * 
	 * 
	 * @param moduleid
	 *            模块id
	 * 
	 * @param groupid
	 * @return
	 * @throws MyException
	 */
	GroupInfo selectGroupInfo(@Param("moduleid") Integer moduleid, @Param("groupid") Integer groupid);
}
