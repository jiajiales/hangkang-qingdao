package com.hot.manage.db.hw;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
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
import com.hot.manage.entity.yg.LKTCode;

public interface LKTHWMapper {

	Page<LKTHWDevList> LKTHWDevList(LKTHWDevListVaule lkthwDevListVaule);

	Integer LKTHWAddSignDevList(@Param("moduleid") Integer moduleid, @Param("devid") String devid,
			@Param("userid") Integer userid, @Param("patrolid") Integer patrolid);

	Integer LKTHWUpdateDev(LKTHWUpdateDevVaule lkthwUpdateDevVaule);

	Integer LKTHWUpdateDevAddPic(LKTHWUpdateDevVaule lkthwUpdateDevVaule);

	Integer LKTHWDeleteDev(@Param("moduleid") Integer moduleid, @Param("devid") Integer devid);

	Integer LKTHWDeleteDevPic(@Param("moduleid") Integer moduleid, @Param("devid") Integer devid);

	List<LKTGroupListDev> LKTHWGroupListDev(@Param("moduleid") Integer moduleid, @Param("groupid") Integer groupid,
			@Param("site") String site);

	LKTJgDevNum LKTHWGroupListDevnum(@Param("groupid") Integer groupid, @Param("moduleid") Integer moduleid,
			@Param("userid") Integer userid);

	LKTJgDevNum LKTHWDevNum(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid);

	Page<LKTJgItemList> LKTHWItemList(LKTHWDevListVaule lkthwDevListVaule);

	Integer LKTHWDeleteItem(@Param("id") Integer id);

	LKTCode LKTHWDeleteItemcondition(@Param("id") Integer id);

	LKTHWSelectOnId LKTHWSelectOnId(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid,
			@Param("devid") Integer devid, @Param("devnum") String devnum, @Param("mac") String mac);

	List<LKTGroupList> LKTHWGroupList(@Param("userid") Integer userid, @Param("id") Integer id,
			@Param("moduleid") Integer moduleid, @Param("groupname") String groupname,
			@Param("itemnum") String itemnum);

	Integer LKTHWUpdateItem(LKTUpdateItemVaule lktUpdateItemVaule);

	Integer LKTHWUpdateItemDelPic(LKTUpdateItemVaule lktUpdateItemVaule);

	Integer LKTHWUpdateItemPic(@Param("picnum") String picnum, @Param("picpath") String picpath,
			@Param("site") String site, @Param("itemid") Integer itemid, @Param("addtime") String addtime);

	List<LKTJgItemListMap> LKTHWItemListMap(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid);

	Integer LKTHWAddDev(LKTHWAddDevVaule lkthwAddDevVaule);

	Integer LKTHWAddDevGroup(LKTHWAddDevVaule lkthwAddDevVaule);

	Integer LKTHWAddDevGroupPic(LKTHWAddDevVaule lkthwAddDevVaule);

	Integer LKTHWAddGroup(LKTHWAddGroupVaule lkthwAddGroupVaule);

	Integer LKTHWAddGroupUser(LKTHWAddGroupVaule lkthwAddGroupVaule);

	Integer LKTHWAddGroupPic(@Param("picnum") String picnum, @Param("picpath") String picpath,
			@Param("site") String site, @Param("id") Integer id, @Param("addtime") String addtime);

	List<LKTSeleteMap> LKTHWSeleteMap(@Param("groupid") Integer groupid, @Param("userid") Integer userid);

	Integer LKTHWChangeDev(@Param("devid") Integer devid,@Param("mac") String mac);

	LKTHWDevList LKTHWChangeDevMac(@Param("mac") String mac);

	Integer changeDevOwn(@Param("hWChangeUser") HWChangeUser hWChangeUser);

	Integer LKTHWDevIdByMac(String mac);

	Integer LKTHWUpdateDevVideoDel(@Param("moduleid") Integer moduleid, @Param("deviceid") Integer deviceid);

	Integer LKTHWUpdateDevVideo(@Param("moduleid") Integer moduleid, @Param("deviceid") Integer deviceid,
			@Param("videoid") Integer videoid);

	Integer LKTHWUpdateDevVideoAdd(@Param("moduleid") Integer moduleid, @Param("deviceid") Integer deviceid,
			@Param("videoid") Integer videoid);

	List<HWAlarmNum> selectHWAlarmNums( @Param("Time")Integer Time,@Param("moduleID") Integer moduleID, @Param("userid") Integer userid);
}
