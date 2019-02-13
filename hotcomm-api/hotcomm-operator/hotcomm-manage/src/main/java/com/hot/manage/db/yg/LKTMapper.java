package com.hot.manage.db.yg;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hot.manage.entity.yg.LKTCode;
import com.hot.manage.entity.yg.LKTDeviceAll;
import com.hot.manage.entity.yg.LKTEstablishygAlarmAndEvent;
import com.hot.manage.entity.yg.LKTEstablishygDevice;
import com.hot.manage.entity.yg.LKTInstructionsAll;
import com.hot.manage.entity.yg.LKTMyproject;
import com.hot.manage.entity.yg.LKTPatrols;
import com.hot.manage.entity.yg.LKTPatrolsperson;
import com.hot.manage.entity.yg.LKTSelctDevOnid;
import com.hot.manage.entity.yg.LKTSelectAllevent;
import com.hot.manage.entity.yg.LKTSelectGroupWork;
import com.hot.manage.entity.yg.LKTSelectGroupWorkFather;
import com.hot.manage.entity.yg.LKTSelectHandle;
import com.hot.manage.entity.yg.LKTSelectUserApp;
import com.hot.manage.entity.yg.LKTSelectWork;
import com.hot.manage.entity.yg.LKTSelectdevicenum;
import com.hot.manage.entity.yg.LKTSignDeviceListApp;
import com.hot.manage.entity.yg.LKTSignGroupList;
import com.hot.manage.entity.yg.LKTSignHistory;
import com.hot.manage.entity.yg.LKTSignList;
import com.hot.manage.entity.yg.LKTSignListApp;
import com.hot.manage.entity.yg.LKTTureWork;
import com.hot.manage.entity.yg.LKTWorkDetailsApp;
import com.hot.manage.entity.yg.LKTWorkDetailsAppea;
import com.hot.manage.entity.yg.LKTWorkHandleApp;
import com.hot.manage.entity.yg.LKTWorkTobetreatedApp;
import com.hot.manage.entity.yg.LKTWorkUntreated;
import com.hot.manage.entity.yg.item.LKTNewWorkVaule;
import com.hot.manage.entity.yg.item.LKTPatrolscondition;
import com.hot.manage.entity.yg.item.LKTSelectGroupWorkcondition;
import com.hot.manage.entity.yg.item.LKTSelectGroupWorkconditionSon;
import com.hot.manage.entity.yg.item.LKTSignDeviceListAppVaule;
import com.hot.manage.entity.yg.item.LKTSignDeviceListUserVaule;
import com.hot.manage.entity.yg.item.LKTSignDeviceVaule;
import com.hot.manage.entity.yg.item.LKTSignHistorycondition;
import com.hot.manage.entity.yg.item.LKTSignListUpdateVaule;
import com.hot.manage.entity.yg.item.LKTSignListcondition;
import com.hot.manage.entity.yg.item.LKTWorkRepairAppVaule;
import com.hot.manage.entity.yg.item.LKTWorkReplaceAppVaule;
import com.hot.manage.entity.yg.item.LKTWorkUntreatedcondition;

public interface LKTMapper {

	List<LKTMyproject> LkTMyproject(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid);

	LKTSelectdevicenum LKTSelectdevicenum(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid);

	Page<LKTSelectGroupWorkFather> LKTSelectGroupWorkFather(LKTSelectGroupWorkcondition lktSelectGroupWorkcondition);

	List<LKTSelectGroupWork> LKTSelectGroupWork(LKTSelectGroupWorkconditionSon lktSelectGroupWorkconditionSon);

	List<LKTEstablishygAlarmAndEvent> LKTEstablishygAlarmAndEvent();

	List<LKTEstablishygAlarmAndEvent> LKTSelectAlarmAndEvent(@Param("type") Integer type, @Param("id") String id);

	List<LKTEstablishygDevice> LKTEstablishygDevice(@Param("moduleid") Integer moduleid,
			@Param("userid") Integer userid);

	List<LKTSelctDevOnid> LKTSelctDevOnidAlarm(@Param("id") List<Integer> id);

	List<LKTSelctDevOnid> LKTSelctDevOnidEvent(@Param("id") List<Integer> id);

	List<LKTSelectHandle> LKTSelectHandle(@Param("userid") Integer userid);

	LKTCode LKTNewWork(LKTNewWorkVaule lktNewWorkVaule);

	LKTSelectWork LKTSelectWork(@Param("workid") Integer workid);

	List<LKTSelectAllevent> LKTSelectAllevent(@Param("workid") Integer workid);

	List<LKTDeviceAll> LKTDeviceAll(@Param("workid") Integer workid);

	List<LKTInstructionsAll> LKTInstructionsAll(@Param("workid") Integer workid);

	List<LKTTureWork> LKTTureWork(@Param("workid") Integer workid);

	LKTCode LKTDeleteWork(@Param("workid") Integer workid);

	LKTCode LKTWorkExamine(@Param("workid") Integer workid, @Param("audit") Integer audit);

	Page<LKTPatrols> LKTPatrols(LKTPatrolscondition lktPatrolscondition);

	LKTCode LKTPatrolsFrozen(@Param("userid") Integer userid, @Param("isenable") Integer isenable);

	LKTCode LKTPatrolsDelete(@Param("patrolsid") Integer patrolsid);

	LKTCode LKTPatrolsInsert(@Param("adduserid") Integer adduserid);

	List<LKTPatrolsperson> LKTPatrolsperson(@Param("userid") Integer userid);

	List<LKTPatrolsperson> LKTPatrolsuser(@Param("userid") Integer userid);

	Page<LKTSignList> LKTSignList(LKTSignListcondition lktSignListcondition);

	Page<LKTSignHistory> LKTSignHistory(LKTSignHistorycondition lktSignHistorycondition);

	LKTCode LKTSignListDelete(@Param("id") Integer id);

	LKTCode LKTPatrolChange(@Param("patrolsided") Integer patrolsided, @Param("patrolsid") Integer patrolsid);

	List<LKTSignGroupList> LKTSignGroupList();

	Integer LKTSignDevice(LKTSignDeviceVaule lktSignDeviceVaule);

	LKTCode LKTSignDeviceUser(LKTSignDeviceVaule lktSignDeviceVaule);

	Integer LKTSignListUpdate(LKTSignListUpdateVaule lktSignListUpdateVaule);

	LKTCode LKTSignListUpdateUser(LKTSignListUpdateVaule lktSignListUpdateVaule);

	Page<LKTSignList> LKTSignDeviceList(LKTSignListcondition lktSignListcondition);

	LKTCode LKTSignDeviceDelete(@Param("id") Integer id);

	Page<LKTSignHistory> LKTSignDeviceHistory(LKTSignHistorycondition lktSignHistorycondition);

	LKTCode LKTSignDeviceListUser(LKTSignDeviceListUserVaule lktSignDeviceListUserVaule);

	List<LKTSignDeviceListApp> LKTSignDeviceListApp(LKTSignDeviceListAppVaule lktSignDeviceListAppVaule);

	LKTSelectUserApp LKTSelectUserApp(@Param("userid") Integer userid);

	LKTCode LKTSignDeviceApp(@Param("userid") Integer userid, @Param("id") Integer id, @Param("state") Integer state,
			@Param("type") Integer type);

	List<LKTSignListApp> LKTSignListApp(@Param("userid") Integer userid);

	List<LKTWorkUntreated> LKTWorkUntreated(LKTWorkUntreatedcondition lktWorkUntreatedcondition);

	LKTWorkDetailsApp LKTWorkDetailsApp(@Param("woid") Integer woid);

	LKTWorkHandleApp LKTWorkDetailsAppdate(@Param("woid") Integer woid);

	List<LKTWorkDetailsAppea> LKTWorkDetailsAppea(@Param("woid") Integer woid, @Param("devid") Integer devid);

	LKTWorkDetailsApp LKTWorkDetailsAppimg(@Param("Resourcestype") Integer Resourcestype, @Param("type") Integer type,
			@Param("id") Integer id);

	LKTCode LKTWorkRepairApp(LKTWorkRepairAppVaule lktWorkRepairAppVaule);

	Integer LKTWorkRepairAppInsert(@Param("woid") Integer woid, @Param("resourcetype") Integer resourcetype,
			@Param("url") String url);

	Integer LKTWorkRepairAppUpevent(@Param("eventid") Integer eventid);

	Integer LKTWorkRepairAppUpalarm(@Param("userid") Integer userid, @Param("alarmid") Integer alarmid);

	LKTCode LKTWorkRepairAppRecord(@Param("woid") Integer woid, @Param("devid") Integer devid);

	LKTCode LKTWorkGbApp(@Param("woid") Integer woid, @Param("problemdesc") String problemdesc);

	LKTWorkHandleApp LKTWorkHandleApp(@Param("woid") Integer woid);

	Integer LKTWorkReplaceAppDev(LKTWorkReplaceAppVaule lktWorkReplaceAppVaule);

	Integer LKTWorkReplaceAppWo(LKTWorkReplaceAppVaule lktWorkReplaceAppVaule);
	
	List<LKTWorkTobetreatedApp> LKTWorkTobetreatedApp(LKTWorkUntreatedcondition lktWorkUntreatedcondition);
}