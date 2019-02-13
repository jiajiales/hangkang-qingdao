package com.hotcomm.prevention.db.mysql.manage.devicemanager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
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

import tk.mybatis.mapper.common.Mapper;

public interface DeviceMapper extends Mapper<T_device_all> {

	List<SeleteDevMap> seleteDevMap(@Param("groupid") Integer groupid, @Param("userid") Integer userid,
			@Param("moduleid") Integer moduleid);

	Integer seleteGroupListDevnum(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid,
			@Param("groupid") Integer groupid);

	List<GroupListDev> GroupListDev(@Param("moduleid") Integer moduleid, @Param("groupid") Integer groupid,
			@Param("site") String site);

	Page<DevList> DevList(DevListVaule devListVaule);

	Integer DeleteDev(@Param("moduleid") Integer moduleid, @Param("devid") Integer devid);

	Integer DeleteDevPic(@Param("moduleid") Integer moduleid, @Param("devid") Integer devid);

	Integer cutDevVideoRelation(@Param("moduleid") Integer moduleid, @Param("devid") Integer devid);

	Integer AddDevGroup(@Param("deviceid") Integer deviceid, @Param("groupid") Integer groupid,
			@Param("moduleid") Integer moduleid, @Param("addtime") String addtime, @Param("userid") Integer userid);

	Integer AddDevGroupPic(@Param("itempicid") Integer itempicid, @Param("devid") Integer devid,
			@Param("moduleid") Integer moduleid, @Param("addtime") String addtime);

	Integer AddDevVideo(@Param("deviceid") Integer deviceid, @Param("videoid") Integer videoid,
			@Param("moduleid") Integer moduleid);

	SelectOnId SelectOnId(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid,
			@Param("devid") Integer devid, @Param("devnum") String devnum, @Param("mac") String mac);

	List<GroupList> GroupList(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid);

	Integer ChangeMac(@Param("mac") String mac, @Param("devid") Integer devid, @Param("moduleid") Integer moduleid);

	Integer ChangeOwn(@Param("changeOwn") ChangeOwn changeOwn);

	Integer UpdateDevGroup(@Param("groupid") Integer groupid, @Param("deviceid") Integer deviceid,
			@Param("moduleid") Integer moduleid);

	Integer UpdateDevGroupPic(@Param("itempicid") Integer itempicid, @Param("deviceid") Integer deviceid,
			@Param("moduleid") Integer moduleid);

	Integer UpdateDevGroupVideo(@Param("isdelete") Integer isdelete, @Param("deviceid") Integer deviceid,
			@Param("moduleid") Integer moduleid, @Param("videoid") Integer videoid);

	Page<SelectKrqLog> SelectKrqLog(SelectKrqLogParam selectKrqLogParam);

	Page<YGLog> SelectYGLog(YGLogParam yGLogParam);

	Page<MCLog> SelectMCLog(MCLogParam mCLogParam);

	Page<SXDYDevLogList> SelectSxdyDevLogPage(SXDYDevLogListVaule SXDYDevLogListVaule);

	Page<LogSxdl> SelectSxdlLog(@Param("mac") String mac);

	Page<LogSydl> SelectSydlLog(@Param("mac") String mac);

	Page<LogSy> SelectSyLog(@Param("mac") String mac);

	Page<LogJg> SelectJgLog(@Param("mac") String mac);

	Page<LogDc> SelectDcLog(@Param("mac") String mac);

	Page<LogHw> SelectHwLog(@Param("mac") String mac);

	Page<LogLjt> SelectLjtLog(@Param("mac") String mac);

	Page<LogSj> SelectSjLog(@Param("mac") String mac);

	Page<LogPm> SelectPmLog(@Param("mac") String mac);
}
