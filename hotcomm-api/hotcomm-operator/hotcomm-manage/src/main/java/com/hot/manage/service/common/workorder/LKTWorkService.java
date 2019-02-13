package com.hot.manage.service.common.workorder;

import java.util.List;
import com.github.pagehelper.Page;
import com.hot.manage.entity.common.workorder.LKTWorkAllAlarm;
import com.hot.manage.entity.common.workorder.LKTWorkCompleteApp;
import com.hot.manage.entity.common.workorder.LKTWorkCompleteListApp;
import com.hot.manage.entity.common.workorder.LKTWorkDetailedApp;
import com.hot.manage.entity.common.workorder.LKTWorkDetails;
import com.hot.manage.entity.common.workorder.LKTWorkDevList;
import com.hot.manage.entity.common.workorder.LKTWorkDevnum;
import com.hot.manage.entity.common.workorder.LKTWorkListFather;
import com.hot.manage.entity.common.workorder.LKTWorkMyproject;
import com.hot.manage.entity.common.workorder.LKTWorkTure;
import com.hot.manage.entity.common.workorder.LKTWorkUntreatedApp;
import com.hot.manage.entity.common.workorder.vaule.LKTWorkListcondition;
import com.hot.manage.entity.common.workorder.vaule.LKTWorkMaintenanceAppVaule;
import com.hot.manage.entity.common.workorder.vaule.LKTWorkNewVaule;
import com.hot.manage.entity.common.workorder.vaule.LKTWorkUpdateAppVaule;
import com.hot.manage.exception.MyException;

public interface LKTWorkService {

	LKTWorkDevnum LKTWorkDevnum(Integer moduleid, Integer userid) throws MyException;

	List<LKTWorkMyproject> LKTWorkMyproject(Integer moduleid, Integer userid) throws MyException;

	Page<LKTWorkListFather> LKTWorkListFather(LKTWorkListcondition lktWorkListcondition) throws MyException;

	List<LKTWorkAllAlarm> LKTWorkAllAlarm(Integer moduleid, Integer userid) throws MyException;

	List<LKTWorkAllAlarm> LKTWorkAllAlarmOnID(Integer moduleid, Integer userid, String id, Integer type)
			throws MyException;

	List<LKTWorkDevList> LKTWorkDevList(Integer moduleid, Integer userid) throws MyException;

	Integer LKTWorkNew(LKTWorkNewVaule lktWorkNewVaule) throws MyException;

	LKTWorkDetails LKTWorkDetails(Integer woid, Integer userid, Integer moduleid) throws MyException;

	LKTWorkTure LKTWorkTure(Integer moduleid, Integer woid) throws MyException;

	Integer LKTWorkDelete(Integer moduleid, Integer userid, Integer woid) throws MyException;

	Integer LKTWorkExamine(String remark, Integer audit, Integer woid, Integer moduleid, Integer userid,
			Integer assignid) throws MyException;

	Integer LKTReminder(Integer userid, Integer woid, Integer assignid, String code) throws MyException;

	List<LKTWorkUntreatedApp> LKTWorkUntreatedApp(Integer userid, Integer readtype, String dvenumorcode)
			throws MyException;

	LKTWorkDetailedApp LKTWorkDetailedApp(Integer moduleid, Integer woid, Integer userid) throws MyException;

	Integer LKTWorkMaintenanceApp(LKTWorkMaintenanceAppVaule lktWorkMaintenanceAppVaule) throws MyException;

	Integer LKTWorkHangApp(String problemdesc, Integer woid, String pictureurl) throws MyException;

	Integer LKTWorkUpdateApp(LKTWorkUpdateAppVaule lktWorkUpdateAppVaule) throws MyException;

	LKTWorkCompleteApp LKTWorkCompleteApp(Integer woid, Integer moduleid, Integer userid) throws MyException;

	List<LKTWorkCompleteListApp> LKTWorkCompleteListApp(Integer userid, Integer readtype, String dvenumorcode)
			throws MyException;
}
