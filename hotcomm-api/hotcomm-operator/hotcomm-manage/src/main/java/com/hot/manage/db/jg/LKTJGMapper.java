package com.hot.manage.db.jg;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.github.pagehelper.Page;
import com.hot.manage.entity.jg.JGChangeUser;
import com.hot.manage.entity.jg.JGDevAlarmHandleByTimeVO;
import com.hot.manage.entity.jg.JGDevRate;
import com.hot.manage.entity.jg.LKTDevList;
import com.hot.manage.entity.jg.LKTGroupList;
import com.hot.manage.entity.jg.LKTGroupListDev;
import com.hot.manage.entity.jg.LKTJgDevNum;
import com.hot.manage.entity.jg.LKTJgItemList;
import com.hot.manage.entity.jg.LKTJgItemListMap;
import com.hot.manage.entity.jg.LKTSelectOnId;
import com.hot.manage.entity.jg.LKTSeleteMap;
import com.hot.manage.entity.jg.vaule.LKTDevListVaule;
import com.hot.manage.entity.jg.vaule.LKTJgAddDevVaule;
import com.hot.manage.entity.jg.vaule.LKTJgAddGroupVaule;
import com.hot.manage.entity.jg.vaule.LKTUpdateDevVaule;
import com.hot.manage.entity.jg.vaule.LKTUpdateItemVaule;
import com.hot.manage.entity.jg.vaule.Optionaluser;
import com.hot.manage.entity.yg.LKTCode;
import com.hot.manage.exception.MyException;

public interface LKTJGMapper {

	Page<LKTDevList> LKTDevList(LKTDevListVaule lktDevListVaule);

	Integer LKTAddSignDevList(@Param("moduleid") Integer moduleid, @Param("devid") String devid,
			@Param("patrolid") Integer patrolid);

	Integer LKTUpdateDev(LKTUpdateDevVaule lktUpdateDevVaule);

	Integer LKTUpdateDevAddPic(LKTUpdateDevVaule lktUpdateDevVaule);

	List<LKTGroupList> LKTGroupList(@Param("userid") Integer userid, @Param("id") Integer id,
			@Param("moduleid") Integer moduleid, @Param("groupname") String groupname,
			@Param("itemnum") String itemnum);

	Integer LKTDeleteDev(@Param("moduleid") Integer moduleid, @Param("devid") Integer devid);

	LKTJgDevNum LKTJgDevNum(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid);

	Page<LKTJgItemList> LKTJgItemList(LKTDevListVaule lktDevListVaule);

	Integer LKTDeleteItem(@Param("id") Integer id);

	Integer LKTDeleteItemPic(@Param("id") Integer id);

	LKTCode LKTDeleteItemcondition(@Param("id") Integer id);

	Integer LKTUpdateItem(LKTUpdateItemVaule lktUpdateItemVaule);

	Integer LKTUpdateItemDelPic(LKTUpdateItemVaule lktUpdateItemVaule);

	Integer LKTUpdateItemPic(@Param("picnum") String picnum, @Param("picpath") String picpath,
			@Param("site") String site, @Param("itemid") Integer itemid, @Param("addtime") String addtime);

	List<LKTGroupListDev> LKTGroupListDev(@Param("moduleid") Integer moduleid, @Param("groupid") Integer groupid,
			@Param("site") String site);

	List<LKTJgItemListMap> LKTJgItemListMap(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid);

	LKTJgDevNum LKTGroupListDevnum(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid,
			@Param("groupid") Integer groupid);

	List<LKTSelectOnId> LKTSelectOnIdpic(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid,
			@Param("devid") Integer devid, @Param("devnum") String devnum, @Param("macAddr") String macAddr);

	Integer LKTJgAddDev(LKTJgAddDevVaule lktJgAddDevVaule);

	Integer LKTJgAddDevGroup(LKTJgAddDevVaule lktJgAddDevVaule);

	Integer LKTJgAddDevGroupPic(LKTJgAddDevVaule lktJgAddDevVaule);

	Integer LKTJgAddGroup(LKTJgAddGroupVaule lktJgAddGroupVaule);

	Integer LKTJgAddGroupUser(LKTJgAddGroupVaule lktJgAddGroupVaule);

	Integer LKTJgAddGroupPic(@Param("picnum") String picnum, @Param("picpath") String picpath,
			@Param("site") String site, @Param("id") Integer id, @Param("addtime") String addtime);

	List<LKTSeleteMap> LKTSeleteMap(@Param("groupid") Integer groupid, @Param("userid") Integer userid);

	List<Optionaluser> getUser(@Param("userid") Integer userid) throws MyException;

	LKTSelectOnId LKTSelectOnIdpicToOne(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid,
			@Param("devid") Integer devid, @Param("devnum") String devnum, @Param("macAddr") String macAddr)
					throws MyException;

	Integer updateDeviceOwn(@Param("jgChangeUser") JGChangeUser jgChangeUser);

	Integer insertJgRelationVideo(@Param("deviceid") Integer deviceid, @Param("videoid") Integer videoid);

	Integer LKTDelectVideoRe(@Param("deviceid") Integer deviceid);

	Integer selectCountDevReVideo(@Param("devid") Integer devid);

	Integer checkJgRelationVideo(@Param("devid") Integer devid, @Param("videoid") Integer videoid);

	Integer updateJgRelationVideo(@Param("devid") Integer devid, @Param("videoid") Integer videoid);

	LKTDevList LKTJGChangeDevMac(@Param("mac") String mac);

	Integer LKTJGChangeDev(@Param("devid") Integer devid, @Param("mac") String mac);

	Integer selectCountBypurpose(@Param("purpose") Integer purpose, @Param("userid") Integer userid);

	Integer selectCountByloadbear(@Param("loadbear") Integer loadbear, @Param("userid") Integer userid);

	List<JGDevAlarmHandleByTimeVO> JGselectDevAlarmHandleByTime(@Param("queryType") Integer queryType,
			@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("userid") Integer userid);

	List<JGDevRate> selectDevRate(@Param("queryType") Integer queryType, @Param("startTime") String startTime,
			@Param("endTime") String endTime, @Param("userid") Integer userid);
}
