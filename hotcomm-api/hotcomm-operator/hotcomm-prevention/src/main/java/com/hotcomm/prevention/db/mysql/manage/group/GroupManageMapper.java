package com.hotcomm.prevention.db.mysql.manage.group;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.DeviceParam;
import com.hotcomm.prevention.bean.mysql.manage.group.AlarmHandleNums;
import com.hotcomm.prevention.bean.mysql.manage.group.AlarmNums;
import com.hotcomm.prevention.bean.mysql.manage.group.AllDevByGroupID;
import com.hotcomm.prevention.bean.mysql.manage.group.AreaInfo;
import com.hotcomm.prevention.bean.mysql.manage.group.DevGroupInfoVO;
import com.hotcomm.prevention.bean.mysql.manage.group.GroupDevInfo;
import com.hotcomm.prevention.bean.mysql.manage.group.GroupDevState;
import com.hotcomm.prevention.bean.mysql.manage.group.GroupInfo;
import com.hotcomm.prevention.bean.mysql.manage.group.GroupInfoVO;
import com.hotcomm.prevention.bean.mysql.manage.group.GroupListVO;
import com.hotcomm.prevention.bean.mysql.manage.group.GroupMap;
import com.hotcomm.prevention.bean.mysql.manage.group.GroupMapImg;
import com.hotcomm.prevention.bean.mysql.manage.group.GroupParams;
import com.hotcomm.prevention.bean.mysql.manage.group.GroupPic;
import com.hotcomm.prevention.bean.mysql.manage.group.GroupSiteImgpath;
import com.hotcomm.prevention.bean.mysql.manage.group.GroupVO;
import com.hotcomm.prevention.bean.mysql.manage.group.SiteDevVO;
import com.hotcomm.prevention.bean.mysql.manage.group.entity.AppSum;
import com.hotcomm.prevention.exception.MyException;

import tk.mybatis.mapper.common.Mapper;

public interface GroupManageMapper extends Mapper<GroupPic> {

	List<GroupInfoVO> selectgroupById(@Param("groupId") Integer groupId) throws MyException;

	Integer countdg(@Param("moduleid") Integer moduleid, @Param("groupname") String groupname,
			@Param("itemnum") String itemnum, @Param("id") Integer id) throws MyException;

	Integer insertGroup(GroupParams groupParams) throws MyException;

	void insertGroupImg(@Param("id") Integer id, @Param("picnum") String picnum, @Param("picpath") String picpath,
			@Param("site") String site) throws MyException;

	void insertGroupUserRelation(@Param("id") Integer id, @Param("userid") Integer userid) throws MyException;

	Integer updateGroup(GroupParams groupParams) throws MyException;

	void removeGroupImg(@Param("groupid") Integer groupid) throws MyException;

	Integer selectGroupDev(@Param("groupid") Integer groupid) throws MyException;

	Integer deleteGroup(@Param("groupid") Integer groupid) throws MyException;

	Integer selectGroupUser(@Param("groupid") Integer groupid) throws MyException;

	void deleteGroupUser(@Param("groupid") Integer groupid) throws MyException;

	Page<GroupListVO> selectGroupList(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid,
			@Param("starttime") String starttime, @Param("endtime") String endtime, @Param("keywords") String keywords,
			@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize) throws MyException;

	Integer insertDevMapImg(@Param("picnum") String picnum, @Param("picpath") String picpath,
			@Param("site") String site, @Param("itemid") Integer itemid);

	Integer deleteImg(@Param("id") Integer mapimgid) throws MyException;

	Integer selectExistRelationImg(@Param("mapimgid") Integer mapimgid, @Param("moduleid") Integer moduleid)
			throws MyException;

	GroupInfo groupInfo(@Param("groupid") Integer groupid, @Param("moduleid") Integer moduleid,
			@Param("userid") Integer userid) throws MyException;

	Page<GroupDevState> selectGroupDevState(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid,@Param("groupid") Integer groupid)
			throws MyException;

	Integer groupDevCount(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid,
			@Param("groupid") Integer groupid) throws MyException;

	List<GroupMapImg> selectGroupImgByid(@Param("groupid") Integer groupid, @Param("userid") Integer userid,
			@Param("moduleid") Integer moduleid) throws MyException;

	List<GroupSiteImgpath> groupSiteImgPath(@Param("groupid") Integer groupid, @Param("userid") Integer userid,
			@Param("moduleid") Integer moduleid) throws MyException;

	List<SiteDevVO> selectDevbySiteid(@Param("mapimgid") Integer mapimgid, @Param("moduleid") Integer moduleid)
			throws MyException;

	Integer updateGroupPosition(@Param("groupid") Integer groupid, @Param("x") Double x, @Param("y") Double y,
			@Param("groupcode") String groupcode) throws MyException;

	List<GroupListVO> selectAllItem(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid)
			throws MyException;

	Integer updateDevPosition(@Param("id") Integer id, @Param("x") Double x, @Param("y") Double y,
			@Param("code") String code, @Param("moduleid") Integer moduleid) throws MyException;

	List<GroupMap> selectGroupMap(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid)
			throws MyException;

	List<AllDevByGroupID> selectDevByGroupID(@Param("groupid") Integer groupid, @Param("moduleid") Integer moduleid)
			throws MyException;

	Integer updateItemPic(@Param("itemid") Integer itemid, @Param("updateTime") String updateTime,
			@Param("picurl") String picurl) throws MyException;

	DevGroupInfoVO selectGroupInfo(@Param("moduleid") Integer moduleid, @Param("groupid") Integer groupid)
			throws MyException;

	List<AlarmHandleNums> selectGroupAlarmHandleNums(@Param("moduleID") Integer moduleID,
			@Param("userid") Integer userid, @Param("queryType") Integer queryType, @Param("groupid") Integer groupid)
			throws MyException;

	List<AlarmNums> selectGroupAlarmNums(@Param("moduleID") Integer moduleID, @Param("userid") Integer userid,
			@Param("queryType") Integer queryType, @Param("groupid") Integer groupid) throws MyException;

	Integer insertSelectives(DeviceParam type) throws MyException;

	Integer selectCode(@Param("mac") String mac) throws MyException;

	List<GroupVO> selectGrouplist() throws MyException;

	GroupDevInfo selectGroupDevInfo(@Param("moduleid") Integer moduleid,@Param("groupid")  Integer groupid)throws MyException;

	AppSum AppMapDevnum(@Param("userid") Integer userid)throws MyException;

	List<AreaInfo> selectAreaInfo()throws MyException;

	Integer countdg(@Param("moduleid") Integer moduleid,@Param("groupname")  String groupname,@Param("itemnum")   String itemnum)throws MyException;

	Integer selectDevnum(@Param("devnum") String devnum);

	void addGroup(@Param("groupid") Integer groupid,@Param("id") Integer id,@Param("moduleid") Integer type, @Param("adduserid")  Integer adduserid);

}
