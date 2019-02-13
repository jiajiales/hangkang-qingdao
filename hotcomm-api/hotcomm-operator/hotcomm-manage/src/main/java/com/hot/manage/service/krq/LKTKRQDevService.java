package com.hot.manage.service.krq;

import java.util.List;


import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.common.groupgk.AlarmHandleStatistics;
import com.hot.manage.entity.common.groupgk.AlarmStateStatistics;
import com.hot.manage.entity.common.groupgk.AlarmTendecyVo;
import com.hot.manage.entity.krq.LKTKRQDevList;
import com.hot.manage.entity.krq.LKTKRQSelectOnId;
import com.hot.manage.entity.krq.vaule.KRQChangeUser;
import com.hot.manage.entity.krq.vaule.LKTKRQAddDevVaule;
import com.hot.manage.entity.krq.vaule.LKTKRQDevListVaule;
import com.hot.manage.entity.krq.vaule.LKTKRQUpdateDevVaule;
import com.hot.manage.exception.MyException;

public interface LKTKRQDevService {

	PageInfo<LKTKRQDevList> LKTKRQDevList(LKTKRQDevListVaule lktkrqDevListVaule) throws MyException;

	Integer LKTKRQUpdateDev(LKTKRQUpdateDevVaule lktkrqUpdateDevVaule) throws MyException;
	
	Integer LKTKRQDeleteDev(Integer moduleid, Integer devid) throws MyException;
	
	LKTKRQSelectOnId LKTKRQSelectOnId(Integer moduleid, Integer userid, Integer devid) throws MyException;
	
	Integer LKTKRQAddDev(LKTKRQAddDevVaule lktkrqAddDevVaule);

	Integer LKTKRQChangeDev(LKTKRQUpdateDevVaule lktkrqUpdateDevVaule);

	Integer changeDevOwn(KRQChangeUser kRQChangeUser);

	Integer LKTAddSignDevList(Integer moduleid, String devid, Integer patrolid) throws MyException;


    AlarmTendecyVo selectKRQAlarmForDay(Integer groupid)throws MyException;
	
    AlarmTendecyVo selectKRQAlarmForMonth(Integer groupid)throws MyException;
	
    AlarmTendecyVo selectKRQAlarmForThreeYear(Integer groupid)throws MyException;
    
    List<AlarmHandleStatistics> selectKRQAlarmForWeeken(Integer groupid) throws MyException;

	List<AlarmStateStatistics> selectKRQAlarmForState(Integer groupid,Integer TheType) throws MyException;
}
