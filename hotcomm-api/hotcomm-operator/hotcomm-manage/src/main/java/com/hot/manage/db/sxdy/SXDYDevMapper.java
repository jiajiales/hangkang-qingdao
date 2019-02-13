package com.hot.manage.db.sxdy;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hot.manage.entity.sxdy.value.SXDYAddDevVaule;
import com.hot.manage.entity.sxdy.value.SXDYChangeUser;
import com.hot.manage.entity.sxdy.value.SXDYDevListVaule;
import com.hot.manage.entity.sxdy.value.SXDYUpdateDevVaule;
import com.hot.manage.entity.sxdy.SXDYAlarmingTrendVO;
import com.hot.manage.entity.sxdy.SXDYDevList;
import com.hot.manage.entity.sxdy.SXDYSelectOnId;

public interface SXDYDevMapper {

	Page<SXDYDevList> SXDYDevList(SXDYDevListVaule SXDYDevListVaule);

	Integer SXDYUpdateDev(SXDYUpdateDevVaule SXDYUpdateDevVaule);

	Integer SXDYUpdateDevAddPic(SXDYUpdateDevVaule SXDYUpdateDevVaule);

	Integer SXDYDeleteDev(@Param("moduleid") Integer moduleid, @Param("devid") Integer devid);

	Integer SXDYDeleteDevPic(@Param("moduleid") Integer moduleid, @Param("devid") Integer devid);

	List<SXDYSelectOnId> SXDYSelectOnId(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid,
			@Param("devid") Integer devid, @Param("devnum") String devnum, @Param("macAddr") String macAddr);

	Integer SXDYAddDev(SXDYAddDevVaule SXDYAddDevVaule);

	Integer SXDYAddDevVideo(@Param("moduleid") Integer moduleid, @Param("deviceid") Integer deviceid,
			@Param("videoid") Integer videoid);

	Integer SXDYDeleteDevVideo(@Param("moduleid") Integer moduleid, @Param("deviceid") Integer deviceid);

	Integer SXDYAddDevGroup(SXDYAddDevVaule SXDYAddDevVaule);

	Integer SXDYAddDevGroupPic(SXDYAddDevVaule SXDYAddDevVaule);

	Integer SXDYChangeDev(SXDYUpdateDevVaule SXDYUpdateDevVaule);

	SXDYDevList SXDYChangeDevMac(@Param("mac") String mac);

	Integer changeDevOwn(@Param("SXDYChangeUser") SXDYChangeUser SXDYChangeUser);

	Integer LKTAddSignDevList(@Param("moduleid") Integer moduleid, @Param("devid") String devid,
			@Param("patrolid") Integer patrolid);

	Integer SXDYUpdateDevVideoDel(@Param("moduleid") Integer moduleid, @Param("deviceid") Integer deviceid);

	Integer SXDYUpdateDevVideo(@Param("moduleid") Integer moduleid, @Param("deviceid") Integer deviceid,
			@Param("videoid") Integer videoid);

	Integer SXDYUpdateDevVideoAdd(@Param("moduleid") Integer moduleid, @Param("deviceid") Integer deviceid,
			@Param("videoid") Integer videoid);

	List<SXDYAlarmingTrendVO> AlarmingTrendForSXDY(@Param("queryType") Integer queryType, @Param("userid") Integer userid,
			@Param("groupid") Integer groupid);
}
