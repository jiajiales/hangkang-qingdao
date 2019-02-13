package com.hot.manage.db.sxdl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.github.pagehelper.Page;
import com.hot.manage.entity.sxdl.value.SXDLAddDevVaule;
import com.hot.manage.entity.sxdl.value.SXDLChangeUser;
import com.hot.manage.entity.sxdl.value.SXDLDevListVaule;
import com.hot.manage.entity.sxdl.value.SXDLUpdateDevVaule;
import com.hot.manage.entity.sxdl.SXDLAlarmingTrendVO;
import com.hot.manage.entity.sxdl.SXDLDevList;
import com.hot.manage.entity.sxdl.SXDLSelectOnId;

public interface SXDLDevMapper {

	Page<SXDLDevList> SXDLDevList(SXDLDevListVaule SXDLDevListVaule);

	Integer SXDLUpdateDev(SXDLUpdateDevVaule SXDLUpdateDevVaule);

	Integer SXDLUpdateDevAddPic(SXDLUpdateDevVaule SXDLUpdateDevVaule);

	Integer SXDLDeleteDev(@Param("moduleid") Integer moduleid, @Param("devid") Integer devid);

	Integer SXDLDeleteDevPic(@Param("moduleid") Integer moduleid, @Param("devid") Integer devid);

	List<SXDLSelectOnId> SXDLSelectOnId(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid,
			@Param("devid") Integer devid, @Param("devnum") String devnum, @Param("macAddr") String macAddr);

	Integer SXDLAddDev(SXDLAddDevVaule SXDLAddDevVaule);

	Integer SXDLAddDevVideo(@Param("moduleid") Integer moduleid, @Param("deviceid") Integer deviceid,
			@Param("videoid") Integer videoid);

	Integer SXDLDeleteDevVideo(@Param("moduleid") Integer moduleid, @Param("deviceid") Integer deviceid);

	Integer SXDLAddDevGroup(SXDLAddDevVaule SXDLAddDevVaule);

	Integer SXDLAddDevGroupPic(SXDLAddDevVaule SXDLAddDevVaule);

	Integer SXDLChangeDev(SXDLUpdateDevVaule SXDLUpdateDevVaule);

	SXDLDevList SXDLChangeDevMac(@Param("mac") String mac);

	Integer changeDevOwn(@Param("SXDLChangeUser") SXDLChangeUser SXDLChangeUser);

	Integer LKTAddSignDevList(@Param("moduleid") Integer moduleid, @Param("devid") String devid,
			@Param("patrolid") Integer patrolid);

	Integer SXDLUpdateDevVideoDel(@Param("moduleid") Integer moduleid, @Param("deviceid") Integer deviceid);

	Integer SXDLUpdateDevVideo(@Param("moduleid") Integer moduleid, @Param("deviceid") Integer deviceid,
			@Param("videoid") Integer videoid);

	Integer SXDLUpdateDevVideoAdd(@Param("moduleid") Integer moduleid, @Param("deviceid") Integer deviceid,
			@Param("videoid") Integer videoid);

	List<SXDLAlarmingTrendVO> AlarmingTrendForSXDL(@Param("queryType") Integer queryType,
			@Param("userid") Integer userid, @Param("groupid") Integer groupid);

}
