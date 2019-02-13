package com.hotcomm.prevention.db.mysql.manage.workorder;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.github.pagehelper.Page;
import com.hotcomm.prevention.bean.mysql.manage.appPush.T_hk_apppush;
import com.hotcomm.prevention.bean.mysql.manage.workorder.SelDevmsg;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkAllAlarm;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkCompleteApp;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkCompleteListApp;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkDetailedApp;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkDetails;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkDetailsAllevent;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkDetailsDeviceAll;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkDetailsInstructions;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkDevList;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkDevnum;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkListFather;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkMyproject;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkTure;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkUntreatedApp;
import com.hotcomm.prevention.bean.mysql.manage.workorder.vaule.WorkListcondition;
import com.hotcomm.prevention.bean.mysql.manage.workorder.vaule.WorkMaintenanceAppVaule;
import com.hotcomm.prevention.bean.mysql.manage.workorder.vaule.WorkNewVaule;
import com.hotcomm.prevention.bean.mysql.manage.workorder.vaule.WorkUpdateAppVaule;

public interface WorkMapper {

	WorkDevnum WorkDevnum(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid);

	List<WorkMyproject> WorkMyproject(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid);

	Page<WorkListFather> WorkListFather(WorkListcondition WorkListcondition);

	List<WorkAllAlarm> WorkAllAlarm(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid,
			@Param("id") List<Integer> id, @Param("aeid") List<Integer> aeid);

	List<WorkAllAlarm> WorkAllEven(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid,
			@Param("id") List<Integer> id, @Param("aeid") List<Integer> aeid);

	List<WorkDevList> WorkDevList(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid);

	Integer WorkNew(WorkNewVaule WorkNewVaule);

	Integer WorkNewUpdate(@Param("code") String code, @Param("fatherid") Integer fatherid, @Param("id") Integer id);

	Integer WorkNewDev(@Param("woid") Integer woid, @Param("devid") Integer devid,
			@Param("moduleid") Integer moduleid);

	Integer WorkNewAE(@Param("fateherid") Integer fateherid, @Param("aeid") Integer aeid,
			@Param("type") Integer type, @Param("moduleid") Integer moduleid);

	Integer WorkNewAlarmUp(@Param("assignid") Integer assignid, @Param("alarmid") Integer alarmid,
			@Param("moduleid") Integer moduleid);

	Integer WorkNewEventUp(@Param("eventid") Integer eventid, @Param("moduleid") Integer moduleid);

	WorkDetails WorkDetails(@Param("woid") Integer woid, @Param("userid") Integer userid,
			@Param("moduleid") Integer moduleid);

	WorkDetailsDeviceAll WorkDetailsDeviceAll(@Param("woid") Integer woid, @Param("moduleid") Integer moduleid,
			@Param("userid") Integer userid);

	List<WorkDetailsAllevent> WorkDetailsAllAlarmonf(@Param("woid") Integer woid, @Param("userid") Integer userid,
			@Param("moduleid") Integer moduleid);

	List<WorkDetailsAllevent> WorkDetailsAllEvenonf(@Param("woid") Integer woid, @Param("userid") Integer userid,
			@Param("moduleid") Integer moduleid);

	List<WorkDetailsInstructions> WorkDetailsInstructions(@Param("woid") Integer woid,
			@Param("moduleid") Integer moduleid);

	WorkDetailsInstructions WorkDetailsInstructionsOnid(@Param("moduleid") Integer moduleid,
			@Param("type") Integer type, @Param("aeid") Integer aeid);

	WorkTure WorkTure(@Param("moduleid") Integer moduleid, @Param("woid") Integer woid);

	Integer WorkDeletef(@Param("moduleid") Integer moduleid, @Param("woid") Integer woid);

	Integer WorkDeleteDev(@Param("moduleid") Integer moduleid, @Param("woid") Integer woid);

	Integer WorkDeleteAE(@Param("moduleid") Integer moduleid, @Param("woid") Integer woid);

	Integer WorkDeleteResources(@Param("woid") Integer woid);

	Integer WorkExamine(@Param("state") Integer state, @Param("audit") Integer audit, @Param("woid") Integer woid,
			@Param("moduleid") Integer moduleid);

	Integer WorkExamineRepair(@Param("remark") String remark, @Param("audit") Integer audit,
			@Param("woid") Integer woid);

	Integer WorkExamineAlarmUp(@Param("alarmid") Integer alarmid, @Param("moduleid") Integer moduleid);

	Integer WorkExamineEventUp(@Param("eventid") Integer eventid, @Param("moduleid") Integer moduleid);

	Integer WorkExamineDevUp(@Param("id") Integer id, @Param("moduleid") Integer moduleid);

	Integer WorkExamineDelRs(@Param("woid") Integer woid);

	List<WorkUntreatedApp> WorkUntreatedApp(@Param("userid") Integer userid, @Param("readtype") Integer readtype,
			@Param("dvenumorcode") String dvenumorcode, @Param("moduleid") Integer moduleid);

	WorkDetailedApp WorkDetailedApp(@Param("moduleid") Integer moduleid, @Param("woid") Integer woid);

	WorkDetailedApp WorkDetailedAppimg(@Param("moduleid") Integer moduleid, @Param("id") List<Integer> id,
			@Param("type") Integer type);

	Integer WorkMaintenanceApp(WorkMaintenanceAppVaule WorkMaintenanceAppVaule);

	Integer WorkMaintenanceAppImg(@Param("woid") Integer woid, @Param("resourcetype") Integer resourcetype,
			@Param("url") String url);

	Integer WorkMaintenanceAppal(@Param("alarmid") Integer alarmid, @Param("moduleid") Integer moduleid,
			@Param("deviceid") Integer deviceid);

	Integer WorkMaintenanceAppev(@Param("evenid") Integer evenid, @Param("moduleid") Integer moduleid);

	Integer WorkMaintenanceApplog(@Param("woid") Integer woid, @Param("devid") Integer devid,
			@Param("userid") Integer userid);

	Integer WorkHangApp(@Param("handleType") Integer handleType, @Param("problemdesc") String problemdesc,
			@Param("woid") Integer woid);

	Integer WorkUpdateApp(WorkUpdateAppVaule WorkUpdateAppVaule);

	Integer WorkUpdateAppdev(WorkUpdateAppVaule WorkUpdateAppVaule);

	WorkCompleteApp WorkCompleteApp(@Param("woid") Integer woid, @Param("moduleid") Integer moduleid);

	WorkCompleteApp WorkCompleteAppList(@Param("woid") Integer woid, @Param("moduleid") Integer moduleid);

	List<WorkCompleteListApp> WorkCompleteListApp(@Param("userid") Integer userid,
			@Param("readtype") Integer readtype, @Param("dvenumorcode") String dvenumorcode,
			@Param("moduleid") Integer moduleid);

	WorkNewVaule SelectWo(@Param("woid") Integer woid);

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
	
	@Select("SELECT * FROM t_hk_apppush WHERE userid=#{userid}")
	T_hk_apppush getUserRegid(@Param("userid") Integer userid);
}
