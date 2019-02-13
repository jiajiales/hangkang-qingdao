package com.hot.analysis.service.yg;

import java.util.Date;
import java.util.List;

import com.hot.analysis.bean.common.TDeviceYg;
import com.hot.analysis.bean.yg.AlarmHandle;
import com.hot.analysis.bean.yg.GroupInfo;
import com.hot.analysis.bean.yg.SomkeAlarmNum;



public interface ISomkeDataAnalysisService {

	/**
	 * 报警记录/报警类型统计
	 * @param moduleid
	 * @param alarmType
	 * @param userid
	 * @return
	 */
	List<SomkeAlarmNum> selectSomkeAlarmNums(Integer Time, Integer moduleID, Integer userID);
	/**
	 * 报警趋势图
	 * @param startTime
	 * @param endTime
	 * @param moduleid
	 * @param queryType
	 * @param userid
	 * @return
	 */
	List<AlarmHandle> selectAlarmHandle(Date startTime, Date endTime, Integer moduleid, Integer querytype,
			Integer userid);
	/**
	 * 根据Id查询设备信息
	 * @param Id
	 * @return
	 */
	TDeviceYg selectDevById(Integer id);
	/**
	 * 根据组ID查询，组下所有的设备信息
	 * @param userId
	 * @return
	 */
	List<TDeviceYg> selectYgListByGroupId(Integer groupid, Integer moduleid);
	/**
	 * 所有烟感设备信息
	 * @param code 
	 * @param moduleid 
	 * @param userId
	 * @param  code
	 * @return
	 */
	List<TDeviceYg> selectYgList(Integer userid, Integer moduleid, String code);
	/**
	 * 按设备组名字查询
	 * @param userid
	 * @param moduleid
	 * @param name
	 * @return
	 */
	List<GroupInfo> selectGroupInfoList(Integer userid, Integer moduleid);
	/**
	 * 按设备组名字查询
	 * @param userid
	 * @param moduleid
	 * @param name
	 * @return
	 */
	List<GroupInfo> selectGroupInfoByName(Integer userid,Integer moduleid,String name);
	
}
