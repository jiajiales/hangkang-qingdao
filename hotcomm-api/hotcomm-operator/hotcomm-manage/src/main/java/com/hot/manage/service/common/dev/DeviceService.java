package com.hot.manage.service.common.dev;

import java.util.List;
import com.hot.manage.entity.common.AlarmHandleNums;
import com.hot.manage.entity.common.AlarmNums;
import com.hot.manage.entity.common.AllDevAndGroupByUserId;
import com.hot.manage.entity.common.AllDevByUserid;
import com.hot.manage.entity.common.AllGroupByUserId;
import com.hot.manage.entity.common.DevOptionalUser;
import com.hot.manage.entity.common.DeviceForDevnum;
import com.hot.manage.entity.common.DeviceInfo;
import com.hot.manage.entity.common.DeviceInsertParam;
import com.hot.manage.entity.common.DeviceType;
import com.hot.manage.entity.common.GroupInfo;
import com.hot.manage.entity.common.OptionalGroup;
import com.hot.manage.entity.common.alarm.AlarmDev;
import com.hot.manage.exception.MyException;

public interface DeviceService {

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
	DeviceInfo selectAPPDeviceInfo(Integer deviceid, Integer moduleid) throws MyException;

	Integer insertAppDevice(Integer moduleid, Integer userid, DeviceInsertParam deviceInsertParam) throws MyException;

	/**
	 * 可选项目组
	 * 
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	List<OptionalGroup> selectOpaitonalGroup(Integer userid) throws MyException;

	/**
	 * 可选责任人
	 * 
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	List<DevOptionalUser> selectOpationalUserid(Integer userid) throws MyException;

	/**
	 * 查询设备责任人
	 * 
	 * @param deviceid
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	AlarmDev selectDevForUser(Integer deviceid, Integer moduleid) throws MyException;

	/**
	 * 根据设备编号获取设备信息
	 * 
	 * @param devnum
	 * @return
	 * @throws MyException
	 */
	DeviceForDevnum selectAppDeviceForDevnum(String devnum) throws MyException;
	
	/**
	 * 根据mac获取设备信息
	 * 
	 * @param mac
	 * @return
	 * @throws MyException
	 */
	DeviceForDevnum selectAppDeviceForMac(String mac) throws MyException;
	/**
	 * app端获取设备类型
	 * 
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	List<DeviceType> selectAppDeviceType(Integer moduleid) throws MyException;

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
	List<AlarmNums> selectAlarmNums(Integer moduleID, Integer userid, Integer queryType);

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
	List<AlarmHandleNums> selectAlarmHandleNums(Integer moduleID, Integer userid, Integer queryType);

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
	List<AlarmHandleNums> selectGroupAlarmHandleNums(Integer moduleID, Integer userid, Integer queryType,
			Integer groupid);

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
	List<AlarmNums> selectGroupAlarmNums(Integer moduleID, Integer userid, Integer queryType, Integer groupid);

	List<AllGroupByUserId> selectAllGroupByUserId(Integer moduleid, Integer userid);

	List<AllDevByUserid> selectAllDevByUserid(Integer moduleid, Integer userid);

	List<AllDevAndGroupByUserId> selectAllDevAndGroupByUserId(Integer moduleid, Integer userid);

	/**
	 * 报警类型统计
	 * 
	 * @param moduleid
	 *            模块id
	 * 
	 * @param groupid
	 * @return
	 * @throws MyException
	 */
	GroupInfo selectGroupInfo(Integer moduleid, Integer groupid);
}
