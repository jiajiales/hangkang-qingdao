package com.hot.manage.db.sy;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hot.manage.entity.common.groupgk.AlarmHandleStatistics;
import com.hot.manage.entity.common.groupgk.AlarmStateStatistics;
import com.hot.manage.entity.sy.SYAlarmTendency;
import com.hot.manage.entity.sy.SYChangeUser;
import com.hot.manage.entity.sy.SYDevList;
import com.hot.manage.entity.sy.SYSelectOnId;
import com.hot.manage.entity.sy.value.SYAddDevValue;
import com.hot.manage.entity.sy.value.SYDevListValue;
import com.hot.manage.entity.sy.value.SYUpdateDevVaule;
import com.hot.manage.exception.MyException;
import  com.hot.manage.entity.sy.SYGroupList;
public interface SYMapper {
	
	//设备列表
		Page<SYDevList> SYDevList(SYDevListValue SYDevListValue);
		
//添加设备  
		//查询设备
		List<SYSelectOnId> SYSelectOnId(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid,
				@Param("devid") Integer devid, @Param("devnum") String devnum, @Param("mac") String mac);


		Integer SYAddDev(SYAddDevValue sYAddDevValue);

		Integer SYAddDevGroup(SYAddDevValue sYAddDevValue);

		Integer SYAddDevGroupPic(SYAddDevValue sYAddDevValue);

		Integer SYDevIdByMac(String mac);

//删除设备
		Integer  SYDeleteDevPic(@Param("moduleid")  Integer moduleid, @Param("devid")  Integer devid);

		Integer SYDeleteDev(@Param("moduleid")   Integer moduleid, @Param("devid") Integer devid);

//修改设备
		Integer SYUpdateDev(SYUpdateDevVaule sYUpdateDevVaule);

		Integer SYUpdateDevAddPic(SYUpdateDevVaule sYUpdateDevVaule);

		Integer SYUpdateDevVideoDel(@Param("moduleid") Integer moduleid, @Param("deviceid")  Integer deviceid);

		Integer SYUpdateDevVideo(@Param("moduleid") Integer moduleid, @Param("deviceid") Integer deviceid,
				@Param("videoid") Integer videoid);

		Integer SYUpdateDevVideoAdd(@Param("moduleid") Integer moduleid, @Param("deviceid") Integer deviceid,
				@Param("videoid") Integer videoid);
//加入到签到列表
		Integer SYAddSignDevList(@Param("moduleid") Integer moduleid, @Param("devid") String devid,
		@Param("userid") Integer userid, @Param("patrolid") Integer patrolid);
//查询可更换的项目组
		List<SYGroupList> SYGroupList(@Param("userid") Integer userid, @Param("id") Integer id,
				@Param("moduleid") Integer moduleid, @Param("groupname") String groupname,
				@Param("itemnum") String itemnum);
//更换设备
		SYDevList SYChangeDevMac(@Param("mac") String mac);

		Integer SYChangeDev(SYUpdateDevVaule sYUpdateDevVaule);
// 批量修改责任人
		Integer SYchangeDevOwn(@Param("SYChangeUser") SYChangeUser sYChangeUser);

		List<SYAlarmTendency> selectSYAlarmForDay(@Param("groupid") Integer groupid) throws MyException;

		List<SYAlarmTendency> selectSYAlarmForMonth(@Param("groupid") Integer groupid) throws MyException;

		List<SYAlarmTendency> selectSYAlarmForThreeYear(@Param("groupid") Integer groupid) throws MyException;

		List<AlarmHandleStatistics> selectSYAlarmForWeeken(@Param("groupid") Integer groupid) throws MyException;

		List<AlarmStateStatistics> selectSYAlarmForState(@Param("groupid") Integer groupid,@Param("TheType") Integer TheType) throws MyException;
}
