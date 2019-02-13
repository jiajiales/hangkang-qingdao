package com.hot.manage.db.sydl;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hot.manage.entity.sydl.SYDLAlarmingTrendVO;
import com.hot.manage.entity.sydl.SYDLChangeUser;
import com.hot.manage.entity.sydl.SYDLDevList;
import com.hot.manage.entity.sydl.SYDLGroupList;
import com.hot.manage.entity.sydl.SYDLSelectOnId;
import com.hot.manage.entity.sydl.value.SYDLAddDevValue;
import com.hot.manage.entity.sydl.value.SYDLDevListValue;
import com.hot.manage.entity.sydl.value.SYDLUpdateDevVaule;



public interface SYDLMapper {

	//设备列表
		Page<SYDLDevList> SYDLDevList(SYDLDevListValue sYDLDevListValue);
		
//添加设备  
		//查询设备
		List<SYDLSelectOnId> SYDLSelectOnId(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid,
				@Param("devid") Integer devid, @Param("devnum") String devnum, @Param("mac") String mac);


		Integer SYDLAddDev(SYDLAddDevValue sYDLAddDevValue);

		Integer SYDLAddDevGroup(SYDLAddDevValue sYDLAddDevValue);

		Integer SYDLAddDevGroupPic(SYDLAddDevValue sYDLAddDevValue);

		Integer SYDLDevIdByMac(String mac);

//删除设备
		Integer  SYDLDeleteDevPic(@Param("moduleid")  Integer moduleid, @Param("devid")  Integer devid);

		Integer SYDLDeleteDev(@Param("moduleid")   Integer moduleid, @Param("devid") Integer devid);

//修改设备
		Integer SYDLUpdateDev(SYDLUpdateDevVaule sYDLUpdateDevVaule);

		Integer SYDLUpdateDevAddPic(SYDLUpdateDevVaule sYDLUpdateDevVaule);

		Integer SYDLUpdateDevVideoDel(@Param("moduleid")  Integer moduleid,@Param("deviceid") Integer deviceid);

		Integer SYDLUpdateDevVideo(@Param("moduleid") Integer moduleid, @Param("deviceid") Integer deviceid,
				@Param("videoid") Integer videoid);

		Integer SYDLUpdateDevVideoAdd(@Param("moduleid") Integer moduleid, @Param("deviceid") Integer deviceid,
				@Param("videoid") Integer videoid);
//加入到签到列表
		Integer SYDLAddSignDevList(@Param("moduleid") Integer moduleid, @Param("devid") String devid,
		@Param("userid") Integer userid, @Param("patrolid") Integer patrolid);
//查询可更换的项目组
		List<SYDLGroupList> SYDLGroupList(@Param("userid") Integer userid, @Param("id") Integer id,
				@Param("moduleid") Integer moduleid, @Param("groupname") String groupname,
				@Param("itemnum") String itemnum);
//更换设备
		SYDLDevList SYDLChangeDevMac(@Param("mac") String mac);

		Integer SYDLChangeDev(SYDLUpdateDevVaule sYDLUpdateDevVaule);
// 批量修改责任人
		Integer SYDLchangeDevOwn(@Param("SYDLChangeUser") SYDLChangeUser sYDLChangeUser);
		
		List<SYDLAlarmingTrendVO> AlarmingTrendForSYDL(@Param("queryType") Integer queryType,
				@Param("userid") Integer userid, @Param("groupid") Integer groupid);

}
