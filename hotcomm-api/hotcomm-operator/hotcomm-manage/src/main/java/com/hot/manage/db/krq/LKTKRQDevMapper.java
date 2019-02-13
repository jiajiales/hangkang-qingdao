package com.hot.manage.db.krq;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.github.pagehelper.Page;
import com.hot.manage.entity.common.groupgk.AlarmHandleStatistics;
import com.hot.manage.entity.common.groupgk.AlarmStateStatistics;
import com.hot.manage.entity.common.groupgk.AlarmTendency;
import com.hot.manage.entity.krq.LKTKRQDevList;
import com.hot.manage.entity.krq.LKTKRQSelectOnId;
import com.hot.manage.entity.krq.vaule.KRQChangeUser;
import com.hot.manage.entity.krq.vaule.LKTKRQAddDevVaule;
import com.hot.manage.entity.krq.vaule.LKTKRQDevListVaule;
import com.hot.manage.entity.krq.vaule.LKTKRQUpdateDevVaule;
import com.hot.manage.exception.MyException;

public interface LKTKRQDevMapper {

	Page<LKTKRQDevList> LKTKRQDevList(LKTKRQDevListVaule lktkrqDevListVaule);

	Integer LKTKRQUpdateDev(LKTKRQUpdateDevVaule lktkrqUpdateDevVaule);

	Integer LKTKRQUpdateDevAddPic(LKTKRQUpdateDevVaule lktkrqUpdateDevVaule);

	Integer LKTKRQDeleteDev(@Param("moduleid") Integer moduleid, @Param("devid") Integer devid);

	Integer LKTKRQDeleteDevPic(@Param("moduleid") Integer moduleid, @Param("devid") Integer devid);

	LKTKRQSelectOnId LKTKRQSelectOnId(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid,
			@Param("devid") Integer devid, @Param("devnum") String devnum, @Param("macAddr") String macAddr);

	Integer LKTKRQAddDev(LKTKRQAddDevVaule lktkrqAddDevVaule);

	Integer LKTKRQAddDevVideo(@Param("moduleid") Integer moduleid, @Param("deviceid") Integer deviceid,
			@Param("videoid") Integer videoid);

	Integer LKTKRQDeleteDevVideo(@Param("moduleid") Integer moduleid, @Param("deviceid") Integer deviceid);

	Integer LKTKRQAddDevGroup(LKTKRQAddDevVaule lktkrqAddDevVaule);

	Integer LKTKRQAddDevGroupPic(LKTKRQAddDevVaule lktkrqAddDevVaule);

	Integer LKTKRQChangeDev(LKTKRQUpdateDevVaule lktkrqUpdateDevVaule);

	LKTKRQDevList LKTKRQChangeDevMac(@Param("mac") String mac);

	Integer changeDevOwn(@Param("kRQChangeUser") KRQChangeUser kRQChangeUser);

	Integer LKTAddSignDevList(@Param("moduleid") Integer moduleid, @Param("devid") String devid,
			@Param("patrolid") Integer patrolid);

	Integer LKTKRQUpdateDevVideoDel(@Param("moduleid") Integer moduleid, @Param("deviceid") Integer deviceid);

	Integer LKTKRQUpdateDevVideo(@Param("moduleid") Integer moduleid, @Param("deviceid") Integer deviceid,
			@Param("videoid") Integer videoid);

	Integer LKTKRQUpdateDevVideoAdd(@Param("moduleid") Integer moduleid, @Param("deviceid") Integer deviceid,
			@Param("videoid") Integer videoid);
	
	List<AlarmTendency> selectKRQAlarmForDay(@Param("groupid") Integer groupid)throws MyException;
	
	List<AlarmTendency> selectKRQAlarmForMonth(@Param("groupid") Integer groupid)throws MyException;
	
	List<AlarmTendency> selectKRQAlarmForThreeYear(@Param("groupid") Integer groupid)throws MyException;

	List<AlarmHandleStatistics> selectKRQAlarmForWeeken(@Param("groupid") Integer groupid) throws MyException;

	List<AlarmStateStatistics> selectKRQAlarmForState(@Param("groupid") Integer groupid,@Param("TheType") Integer TheType) throws MyException;
}
