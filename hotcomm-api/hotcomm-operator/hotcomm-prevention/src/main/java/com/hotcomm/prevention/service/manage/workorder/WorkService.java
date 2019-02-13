package com.hotcomm.prevention.service.manage.workorder;

import java.util.List;
import com.github.pagehelper.Page;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkAllAlarm;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkCompleteApp;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkCompleteListApp;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkDetailedApp;
import com.hotcomm.prevention.bean.mysql.manage.workorder.WorkDetails;
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
import com.hotcomm.prevention.exception.MyException;

public interface WorkService {

	WorkDevnum WorkDevnum(Integer moduleid, Integer userid) throws MyException;

	List<WorkMyproject> WorkMyproject(Integer moduleid, Integer userid) throws MyException;

	Page<WorkListFather> WorkListFather(WorkListcondition WorkListcondition) throws MyException;

	List<WorkAllAlarm> WorkAllAlarm(Integer moduleid, Integer userid) throws MyException;

	List<WorkAllAlarm> WorkAllAlarmOnID(Integer moduleid, Integer userid, String id, Integer type) throws MyException;

	List<WorkDevList> WorkDevList(Integer moduleid, Integer userid) throws MyException;

	Integer WorkNew(WorkNewVaule WorkNewVaule) throws MyException;

	WorkDetails WorkDetails(Integer woid, Integer userid, Integer moduleid) throws MyException;

	WorkTure WorkTure(Integer moduleid, Integer woid) throws MyException;

	Integer WorkDelete(Integer moduleid, Integer userid, Integer woid) throws MyException;

	Integer WorkExamine(String remark, Integer audit, Integer woid, Integer moduleid, Integer userid, Integer assignid)
			throws MyException;

	Integer Reminder(Integer userid, Integer woid, Integer assignid, String code) throws MyException;

	List<WorkUntreatedApp> WorkUntreatedApp(Integer userid, Integer readtype, String dvenumorcode, Integer moduleid)
			throws MyException;

	WorkDetailedApp WorkDetailedApp(Integer moduleid, Integer woid, Integer userid) throws MyException;

	Integer WorkMaintenanceApp(WorkMaintenanceAppVaule WorkMaintenanceAppVaule) throws MyException;

	Integer WorkHangApp(String problemdesc, Integer woid, String pictureurl) throws MyException;

	Integer WorkUpdateApp(WorkUpdateAppVaule WorkUpdateAppVaule) throws MyException;

	WorkCompleteApp WorkCompleteApp(Integer woid, Integer moduleid, Integer userid) throws MyException;

	List<WorkCompleteListApp> WorkCompleteListApp(Integer userid, Integer readtype, String dvenumorcode, Integer moduleid)
			throws MyException;
}
