package com.hot.analysis.db.yg;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hot.analysis.bean.common.TDeviceYg;
import com.hot.analysis.bean.yg.AlarmHandle;
import com.hot.analysis.bean.yg.GroupInfo;
import com.hot.analysis.bean.yg.SomkeAlarmNum;


@Mapper
public interface SomkeAlarmNumMapper {
	List<SomkeAlarmNum> selectSomkeAlarmNums(@Param("Time") Integer Time,@Param("moduleID")  Integer moduleID,@Param("userID") Integer userID);

	List<AlarmHandle> selectAlarmHandle(@Param("startTime") Date startTime, @Param("endTime") Date endTime, 
			@Param("moduleid") Integer moduleid,  @Param("querytype")Integer querytype,
			 @Param("userid") Integer userid);

	TDeviceYg selectDevById(@Param("id") Integer id);
	
	/**
	 * 根据组ID查询，组下所有的设备
	 * @param groupid
	 * @param moduleid
	 * @return
	 */
	List<TDeviceYg> selectYgListByGroupId(@Param("groupid") Integer groupid, @Param("moduleid") Integer moduleid);
	/**
	 * 所有的 烟感设备信息
	 * @param userid
	 * @param code 
	 * @param moduleid 
	 * @return
	 */
	List<TDeviceYg> selectYgList(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid, @Param("code") String code);
	/**
	 * 设备组信息列表
	 * @param userid
	 * @param moduleid
	 * @return
	 */
	List<GroupInfo> selectGroupInfoList(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid);
	/**
	 * 按名字查找组信息
	 * @return
	 */
	List<GroupInfo> selectGroupInfoByName(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid,
			@Param("name") String name);
}
