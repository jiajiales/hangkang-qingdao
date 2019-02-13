package com.hot.manage.db.common.workorder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hot.manage.entity.common.workorder.LKTWorkAllAlarm;
import com.hot.manage.entity.common.workorder.LKTWorkCompleteApp;
import com.hot.manage.entity.common.workorder.LKTWorkCompleteListApp;
import com.hot.manage.entity.common.workorder.LKTWorkDetailedApp;
import com.hot.manage.entity.common.workorder.LKTWorkDetails;
import com.hot.manage.entity.common.workorder.LKTWorkDetailsAllevent;
import com.hot.manage.entity.common.workorder.LKTWorkDetailsDeviceAll;
import com.hot.manage.entity.common.workorder.LKTWorkDetailsInstructions;
import com.hot.manage.entity.common.workorder.LKTWorkDevList;
import com.hot.manage.entity.common.workorder.LKTWorkDevnum;
import com.hot.manage.entity.common.workorder.LKTWorkList;
import com.hot.manage.entity.common.workorder.LKTWorkListFather;
import com.hot.manage.entity.common.workorder.LKTWorkMyproject;
import com.hot.manage.entity.common.workorder.LKTWorkTure;
import com.hot.manage.entity.common.workorder.LKTWorkUntreatedApp;
import com.hot.manage.entity.common.workorder.SelDevmsg;
import com.hot.manage.entity.common.workorder.vaule.LKTWorkListcondition;
import com.hot.manage.entity.common.workorder.vaule.LKTWorkListconditionSon;
import com.hot.manage.entity.common.workorder.vaule.LKTWorkMaintenanceAppVaule;
import com.hot.manage.entity.common.workorder.vaule.LKTWorkNewVaule;
import com.hot.manage.entity.common.workorder.vaule.LKTWorkUpdateAppVaule;

public interface LKTWorkMapper {

	LKTWorkDevnum LKTWorkDevnum(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid);

	List<LKTWorkMyproject> LKTWorkMyproject(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid);

	Page<LKTWorkListFather> LKTWorkListFather(LKTWorkListcondition lktWorkListcondition);

	List<LKTWorkList> LKTWorkList(LKTWorkListconditionSon lktWorkListconditionSon);

	List<LKTWorkAllAlarm> LKTWorkAllAlarm(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid,
			@Param("id") List<Integer> id, @Param("aeid") List<Integer> aeid);

	List<LKTWorkAllAlarm> LKTWorkAllEven(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid,
			@Param("id") List<Integer> id, @Param("aeid") List<Integer> aeid);

	List<LKTWorkDevList> LKTWorkDevList(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid);

	Integer LKTWorkNew(LKTWorkNewVaule lktWorkNewVaule);

	Integer LKTWorkNewUpdate(@Param("code") String code, @Param("fatherid") Integer fatherid, @Param("id") Integer id);

	Integer LKTWorkNewDev(@Param("woid") Integer woid, @Param("devid") Integer devid,
			@Param("moduleid") Integer moduleid);

	Integer LKTWorkNewAE(@Param("fateherid") Integer fateherid, @Param("aeid") Integer aeid,
			@Param("type") Integer type, @Param("moduleid") Integer moduleid);

	Integer LKTWorkNewAlarmUp(@Param("assignid") Integer assignid, @Param("alarmid") Integer alarmid,
			@Param("moduleid") Integer moduleid);

	Integer LKTWorkNewEventUp(@Param("eventid") Integer eventid, @Param("moduleid") Integer moduleid);

	LKTWorkDetails LKTWorkDetails(@Param("woid") Integer woid, @Param("userid") Integer userid,
			@Param("moduleid") Integer moduleid);

	LKTWorkDetailsDeviceAll LKTWorkDetailsDeviceAll(@Param("woid") Integer woid, @Param("moduleid") Integer moduleid,
			@Param("userid") Integer userid);

	List<LKTWorkDetailsAllevent> LKTWorkDetailsAllAlarmonf(@Param("woid") Integer woid, @Param("userid") Integer userid,
			@Param("moduleid") Integer moduleid);

	List<LKTWorkDetailsAllevent> LKTWorkDetailsAllEvenonf(@Param("woid") Integer woid, @Param("userid") Integer userid,
			@Param("moduleid") Integer moduleid);

	/*
	 * List<LKTWorkDetailsAllevent> LKTWorkDetailsAllAlarmons(@Param("woid")
	 * Integer woid, @Param("userid") Integer userid,
	 * 
	 * @Param("moduleid") Integer moduleid);
	 * 
	 * List<LKTWorkDetailsAllevent> LKTWorkDetailsAllEvenons(@Param("woid")
	 * Integer woid, @Param("userid") Integer userid,
	 * 
	 * @Param("moduleid") Integer moduleid);
	 */
	List<LKTWorkDetailsInstructions> LKTWorkDetailsInstructions(@Param("woid") Integer woid,
			@Param("moduleid") Integer moduleid);

	LKTWorkDetailsInstructions LKTWorkDetailsInstructionsOnid(@Param("moduleid") Integer moduleid,
			@Param("type") Integer type, @Param("aeid") Integer aeid);

	LKTWorkTure LKTWorkTure(@Param("moduleid") Integer moduleid, @Param("woid") Integer woid);

	Integer LKTWorkDeletef(@Param("moduleid") Integer moduleid, @Param("woid") Integer woid);

	Integer LKTWorkDeleteDev(@Param("moduleid") Integer moduleid, @Param("woid") Integer woid);

	Integer LKTWorkDeleteAE(@Param("moduleid") Integer moduleid, @Param("woid") Integer woid);

	Integer LKTWorkDeleteResources(@Param("woid") Integer woid);

	Integer LKTWorkExamine(@Param("state") Integer state, @Param("audit") Integer audit, @Param("woid") Integer woid,
			@Param("moduleid") Integer moduleid);

	Integer LKTWorkExamineRepair(@Param("remark") String remark, @Param("audit") Integer audit,
			@Param("woid") Integer woid);

	Integer LKTWorkExamineAlarmUp(@Param("alarmid") Integer alarmid, @Param("moduleid") Integer moduleid);

	Integer LKTWorkExamineEventUp(@Param("eventid") Integer eventid, @Param("moduleid") Integer moduleid);

	Integer LKTWorkExamineDevUp(@Param("id") Integer id, @Param("moduleid") Integer moduleid);

	Integer LKTWorkExamineDelRs(@Param("woid") Integer woid);

	List<LKTWorkUntreatedApp> LKTWorkUntreatedApp(@Param("userid") Integer userid, @Param("readtype") Integer readtype,
			@Param("dvenumorcode") String dvenumorcode, @Param("moduleid") Integer moduleid);

	LKTWorkDetailedApp LKTWorkDetailedApp(@Param("moduleid") Integer moduleid, @Param("woid") Integer woid);

	LKTWorkDetailedApp LKTWorkDetailedAppimg(@Param("moduleid") Integer moduleid, @Param("id") List<Integer> id,
			@Param("type") Integer type);

	Integer LKTWorkMaintenanceApp(LKTWorkMaintenanceAppVaule lktWorkMaintenanceAppVaule);

	Integer LKTWorkMaintenanceAppImg(@Param("woid") Integer woid, @Param("resourcetype") Integer resourcetype,
			@Param("url") String url);

	Integer LKTWorkMaintenanceAppal(@Param("alarmid") Integer alarmid, @Param("moduleid") Integer moduleid,
			@Param("deviceid") Integer deviceid);

	Integer LKTWorkMaintenanceAppev(@Param("evenid") Integer evenid, @Param("moduleid") Integer moduleid);

	Integer LKTWorkMaintenanceApplog(@Param("woid") Integer woid, @Param("devid") Integer devid,
			@Param("userid") Integer userid);

	Integer LKTWorkHangApp(@Param("handleType") Integer handleType, @Param("problemdesc") String problemdesc,
			@Param("woid") Integer woid);

	Integer LKTWorkUpdateApp(LKTWorkUpdateAppVaule lktWorkUpdateAppVaule);

	Integer LKTWorkUpdateAppdev(LKTWorkUpdateAppVaule lktWorkUpdateAppVaule);

	LKTWorkCompleteApp LKTWorkCompleteApp(@Param("woid") Integer woid, @Param("moduleid") Integer moduleid);

	LKTWorkCompleteApp LKTWorkCompleteAppList(@Param("woid") Integer woid, @Param("moduleid") Integer moduleid);

	List<LKTWorkCompleteListApp> LKTWorkCompleteListApp(@Param("userid") Integer userid,
			@Param("readtype") Integer readtype, @Param("dvenumorcode") String dvenumorcode,
			@Param("moduleid") Integer moduleid);

	LKTWorkNewVaule SelectWo(@Param("woid") Integer woid);

	SelDevmsg SelDevmsg(@Param("id") Integer id, @Param("moduleid") Integer moduleid);

	Integer AddDevmsg(SelDevmsg seldevmsg);

	SelDevmsg SelDevmsgGroup(@Param("id") Integer id, @Param("moduleid") Integer moduleid);

	Integer AddDevmsgGroup(@Param("deviceid") Integer deviceid, @Param("groupid") Integer groupid,
			@Param("moduleid") Integer moduleid, @Param("lat") String lat, @Param("lng") String lng);

	Integer UpdataEvent(@Param("devid") Integer devid, @Param("eventid") Integer eventid);

	Integer UpdataWoDevnum(@Param("newdevnum") String newdevnum, @Param("id") Integer id);

	Integer UpdataWoDev(@Param("devid") Integer devid, @Param("moduleid") Integer moduleid,
			@Param("woid") Integer woid);

	SelDevmsg SelWoci(@Param("woid") Integer woid);
}
