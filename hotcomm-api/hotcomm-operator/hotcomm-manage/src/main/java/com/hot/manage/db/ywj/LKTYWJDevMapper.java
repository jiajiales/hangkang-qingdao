package com.hot.manage.db.ywj;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hot.manage.entity.ywj.AlarmingTrendVO;
import com.hot.manage.entity.ywj.LKTYWJDevList;
import com.hot.manage.entity.ywj.LKTYWJDevNum;
import com.hot.manage.entity.ywj.LKTYWJGroupList;
import com.hot.manage.entity.ywj.LKTYWJGroupListDev;
import com.hot.manage.entity.ywj.LKTYWJSelectOnId;
import com.hot.manage.entity.ywj.LKTYWJSeleteMap;
import com.hot.manage.entity.ywj.vaule.LKTYWJAddDevVaule;
import com.hot.manage.entity.ywj.vaule.LKTYWJDevListVaule;
import com.hot.manage.entity.ywj.vaule.LKTYWJUpdateDevVaule;

public interface LKTYWJDevMapper {

	Page<LKTYWJDevList> LKTYWJDevList(LKTYWJDevListVaule lktywjDevListVaule);

	Integer LKTYWJAddSignDevList(@Param("moduleid") Integer moduleid, @Param("devid") String devid,
			@Param("patrolid") Integer patrolid);

	Integer LKTYWJUpdateDev(LKTYWJUpdateDevVaule lktywjUpdateDevVaule);

	Integer LKTYWJUpdateDevAddPic(LKTYWJUpdateDevVaule lktywjUpdateDevVaule);

	List<LKTYWJGroupList> LKTYWJGroupList(@Param("userid") Integer userid, @Param("id") Integer id,
			@Param("moduleid") Integer moduleid, @Param("groupname") String groupname,
			@Param("itemnum") String itemnum);

	Integer LKTYWJDeleteDev(@Param("moduleid") Integer moduleid, @Param("devid") Integer devid);

	List<LKTYWJGroupListDev> LKTYWJGroupListDev(@Param("moduleid") Integer moduleid, @Param("groupid") Integer groupid,
			@Param("site") String site);

	LKTYWJDevNum LKTYWJGroupListDevnum(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid,
			@Param("groupid") Integer groupid);

	List<LKTYWJSelectOnId> LKTYWJSelectOnId(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid,
			@Param("devid") Integer devid, @Param("devnum") String devnum, @Param("macAddr") String macAddr);

	List<LKTYWJSeleteMap> LKTYWJSeleteMap(@Param("groupid") Integer groupid, @Param("userid") Integer userid);

	Integer LKTYWJAddDev(LKTYWJAddDevVaule lktywjAddDevVaule);

	Integer LKTYWJAddDevGroup(LKTYWJAddDevVaule lktywjAddDevVaule);

	Integer LKTYWJAddDevGroupPic(LKTYWJAddDevVaule lktywjAddDevVaule);

	Integer LKTYWJChangeYwjOwner(@Param("ownid") Integer ownid, @Param("devid") String devid);

	Integer LKTAddSignDevList(@Param("moduleid") Integer moduleid, @Param("devid") String devid,
			@Param("patrolid") Integer patrolid);

	List<LKTYWJSelectOnId> checkMac(@Param("mac") String mac);

	Integer changeMac(@Param("devid") Integer devid, @Param("mac") String mac);

	List<AlarmingTrendVO> AlarmingTrendForWhichHasBoundaryValue(@Param("queryType") Integer queryType,
			@Param("userid") Integer userid, @Param("groupid") Integer groupid);

}
