package com.hotcomm.prevention.service.manage.devicemanager;

import java.util.List;

import com.github.pagehelper.Page;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.DevList;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.GroupList;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.GroupListDev;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.LogDc;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.LogHw;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.LogJg;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.LogLjt;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.LogPm;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.LogSj;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.LogSxdl;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.LogSy;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.LogSydl;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.MCLog;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.SXDYDevLogList;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.SelectKrqLog;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.SelectOnId;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.SeleteDevMap;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.YGLog;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.ChangeOwn;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.DevListVaule;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.MCLogParam;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.SXDYDevLogListVaule;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.SelectKrqLogParam;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.T_device_all;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.YGLogParam;
import com.hotcomm.prevention.exception.MyException;

public interface DeviceManagerService {

	List<SeleteDevMap> seleteDevMap(Integer groupid, Integer userid, Integer moduleid) throws MyException;

	Integer seleteGroupListDevnum(Integer moduleid, Integer userid, Integer groupid) throws MyException;

	List<GroupListDev> GroupListDev(Integer moduleid, Integer groupid, String site) throws MyException;

	Page<DevList> DevList(DevListVaule devListVaule) throws MyException;

	Integer DeleteDev(Integer moduleid, Integer devid) throws MyException;

	Integer AddDev(T_device_all t_device_all, Integer groupid, Integer itempicid, Integer[] videoid) throws MyException;

	SelectOnId SelectOnId(Integer moduleid, Integer userid, Integer devid) throws MyException;

	List<GroupList> GroupList(Integer userid, Integer moduleid) throws MyException;

	Integer ChangeMac(String mac, Integer devid, Integer moduleid) throws MyException;

	Integer ChangeOwn(ChangeOwn changeOwn) throws MyException;

	Integer UpdateDev(T_device_all t_device_all, Integer groupid, Integer itempicid, Integer[] videoid);

	PageInfo<SelectKrqLog> SelectKrqLog(SelectKrqLogParam selectKrqLogParam) throws MyException;

	PageInfo<YGLog> selectYGLog(YGLogParam yGLogParam) throws MyException;

	PageInfo<MCLog> SelectMCLog(MCLogParam mCLogParam) throws MyException;

	Page<SXDYDevLogList> SelectSxdyDevLogPage(SXDYDevLogListVaule SXDYDevLogListVaule) throws MyException;

	Page<LogSxdl> SelectSxdlLog(String mac) throws MyException;

	Page<LogSydl> SelectSydlLog(String mac) throws MyException;

	Page<LogSy> SelectSyLog(String mac) throws MyException;

	Page<LogJg> SelectJgLog(String mac) throws MyException;

	Page<LogDc> SelectDcLog(String mac) throws MyException;

	Page<LogHw> SelectHwLog(String mac) throws MyException;

	Page<LogLjt> SelectLjtLog(String mac) throws MyException;

	Page<LogSj> SelectSjLog(String mac) throws MyException;

	Page<LogPm> SelectPmLog(String mac) throws MyException;
}
