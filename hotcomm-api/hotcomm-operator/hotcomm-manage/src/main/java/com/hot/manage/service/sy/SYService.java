package com.hot.manage.service.sy;

import java.util.List;


import com.github.pagehelper.Page;
import com.hot.manage.entity.common.groupgk.AlarmHandleStatistics;
import com.hot.manage.entity.common.groupgk.AlarmStateStatistics;
import com.hot.manage.entity.common.groupgk.AlarmTendecyVo;
import com.hot.manage.entity.sy.SYChangeUser;
import com.hot.manage.entity.sy.SYDevList;
import  com.hot.manage.entity.sy.SYGroupList;
import com.hot.manage.entity.sy.SYSelectOnId;
import com.hot.manage.entity.sy.value.SYAddDevValue;
import com.hot.manage.entity.sy.value.SYDevListValue;
import com.hot.manage.entity.sy.value.SYUpdateDevVaule;
import com.hot.manage.exception.MyException;
public interface SYService {

	 //查询设备列表
     Page<SYDevList> SYDevList(SYDevListValue sYDevListValue) throws MyException;
     
     //新增设备
	Integer SYAddDev(SYAddDevValue sYAddDevValue) throws MyException;
	
	//删除设备
	Integer SYDeleteDev(Integer moduleid, Integer devid) throws MyException;
   
	//修改设备
	Integer SYUpdateDev(SYUpdateDevVaule sYUpdateDevVaule);
    
	//加入到签到列表
	Integer SYAddSignDevList(Integer moduleid, String devid, Integer userid, Integer patrolid);
	
	// 查询可更换的项目组
	List<SYGroupList> SYGroupList(Integer userid, Integer id, Integer moduleid, String groupname, String itemnum) throws MyException;
	
	// 根据设备id查询设备
	List<SYSelectOnId>  SYSelectOnId(Integer moduleid, Integer userid, Integer devid, String devnum, String macAddr);
	
	//更换设备
	Integer SYChangeDev(SYUpdateDevVaule sYUpdateDevVaule);

	// 批量修改责任人
	Integer SYchangeDevOwn(SYChangeUser sYChangeUser);;

	AlarmTendecyVo selectSYAlarmForDay(Integer groupid) throws MyException;

	AlarmTendecyVo selectSYAlarmForMonth( Integer groupid) throws MyException;

	AlarmTendecyVo selectSYAlarmForThreeYear( Integer groupid) throws MyException;

	List<AlarmHandleStatistics> selectSYAlarmForWeeken(Integer groupid) throws MyException;

	List<AlarmStateStatistics> selectSYAlarmForState( Integer groupid,Integer TheType) throws MyException;
}
