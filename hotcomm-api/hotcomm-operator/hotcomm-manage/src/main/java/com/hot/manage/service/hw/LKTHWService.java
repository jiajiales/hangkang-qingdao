package com.hot.manage.service.hw;

import java.util.List;

import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.hw.LKTHWDevList;
import com.hot.manage.entity.hw.LKTHWSelectOnId;
import com.hot.manage.entity.hw.vaule.HWChangeUser;
import com.hot.manage.entity.hw.vaule.LKTHWAddDevVaule;
import com.hot.manage.entity.hw.vaule.LKTHWAddGroupVaule;
import com.hot.manage.entity.hw.vaule.LKTHWDevListVaule;
import com.hot.manage.entity.hw.vaule.LKTHWUpdateDevVaule;
import com.hot.manage.entity.hw.vaule.HWAlarmNum;
import com.hot.manage.entity.jg.LKTGroupList;
import com.hot.manage.entity.jg.LKTGroupListDev;
import com.hot.manage.entity.jg.LKTJgDevNum;
import com.hot.manage.entity.jg.LKTJgItemList;
import com.hot.manage.entity.jg.LKTJgItemListMap;
import com.hot.manage.entity.jg.LKTSeleteMap;
import com.hot.manage.entity.jg.vaule.LKTUpdateItemVaule;
import com.hot.manage.exception.MyException;

public interface LKTHWService {

	PageInfo<LKTHWDevList> LKTHWDevList(LKTHWDevListVaule lkthwDevListVaule) throws MyException;

	Integer LKTHWAddSignDevList(Integer moduleid, String devid, Integer userid, Integer patrolid) throws MyException;

	Integer LKTHWUpdateDev(LKTHWUpdateDevVaule lkthwUpdateDevVaule) throws MyException;

	Integer LKTHWDeleteDev(Integer moduleid, Integer devid) throws MyException;

	List<LKTGroupListDev> LKTHWGroupListDev(Integer moduleid, Integer groupid, String site) throws MyException;

	LKTJgDevNum LKTHWGroupListDevnum(Integer groupid, Integer moduleid, Integer userid) throws MyException;

	LKTJgDevNum LKTHWDevNum(Integer moduleid, Integer userid) throws MyException;

	PageInfo<LKTJgItemList> LKTHWItemList(LKTHWDevListVaule lkthwDevListVaule) throws MyException;

	Integer LKTHWDeleteItem(Integer id) throws MyException;

	LKTHWSelectOnId LKTHWSelectOnId(Integer moduleid, Integer userid, Integer devid, String devnum,
			String macAddr) throws MyException;

	List<LKTGroupList> LKTHWGroupList(Integer userid, Integer id, Integer moduleid, String groupname, String itemnum)
			throws MyException;

	Integer LKTHWUpdateItem(LKTUpdateItemVaule lktUpdateItemVaule) throws MyException;

	List<LKTJgItemListMap> LKTHWItemListMap(Integer moduleid, Integer userid) throws MyException;

	Integer LKTHWAddDev(LKTHWAddDevVaule lkthwAddDevVaule) throws MyException;

	Integer LKTHWAddGroup(LKTHWAddGroupVaule lkthwAddGroupVaule) throws MyException;

	List<LKTSeleteMap> LKTHWSeleteMap(Integer groupid, Integer userid) throws MyException;

	Integer LKTHWChangeDev(Integer devid, String mac) throws MyException;

	Integer changeDevOwn(HWChangeUser hWChangeUser) throws MyException;

	List<HWAlarmNum> selectHWAlarmNums(Integer Time, Integer moduleID, Integer userid)throws MyException;

}
