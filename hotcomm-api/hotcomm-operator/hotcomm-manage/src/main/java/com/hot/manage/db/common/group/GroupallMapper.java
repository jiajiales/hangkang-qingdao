package com.hot.manage.db.common.group;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hot.manage.entity.common.AllDevByGroupID;
import com.hot.manage.entity.common.group.Groupdevcount;
import com.hot.manage.entity.common.group.Groupdevstate;
import com.hot.manage.entity.common.group.Grouplist;
import com.hot.manage.entity.common.group.Groupmap;
import com.hot.manage.entity.common.group.Groupmapimg;
import com.hot.manage.entity.common.group.Groupone;
import com.hot.manage.entity.common.group.Groups;
import com.hot.manage.entity.common.group.Groupsiteimgpath;
import com.hot.manage.entity.common.group.Map_group_xq;
import com.hot.manage.entity.common.group.Sitedev;
import com.hot.manage.exception.MyException;

public interface GroupallMapper {
	public Page<Grouplist> selectgrouplist(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid,
			@Param("starttime") String starttime, @Param("endtime") String endtime, @Param("keywords") String keywords,
			@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

	public List<Groupdevstate> selectgroupdevstate(@Param("userid") Integer userid,
			@Param("moduleid") Integer moduleid);

	public Map_group_xq mapgroupdevstate(@Param("groupid") Integer groupid, @Param("moduleid") Integer moduleid,
			@Param("userid") Integer userid);

	public List<Groupmap> selectgroupmap(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid);

	public Groupdevcount selectgroupdevcount(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid,
			@Param("groupid") Integer groupid);

	public List<Groupone> selectgroupbyid(@Param("groupid") Integer groupid);

	public Integer delgroup(@Param("groupid") Integer groupid);

	public Integer insertgroup(Groups groups);

	public Integer insertgroupimg(@Param("id") Integer id, @Param("picnum") String picnum,
			@Param("picpath") String picpath, @Param("site") String site);

	public Integer insert_dev_group_user(@Param("id") Integer id, @Param("userid") Integer userid);

	public Integer updategroups(Groups groups);

	public List<Groupmapimg> selectgroupmapimg(@Param("groupid") Integer groupid, @Param("userid") Integer userid,
			@Param("moduleid") Integer moduleid);

	public List<Groupsiteimgpath> selectgroupsite_imgpath(@Param("groupid") Integer groupid,
			@Param("userid") Integer userid, @Param("moduleid") Integer moduleid);

	public List<Sitedev> selectsitedev(@Param("mapimgidid") Integer mapimgidid, @Param("moduleid") Integer moduleid);

	public Integer deldev(@Param("devid") Integer devid, @Param("moduleid") Integer moduleid);

	public Integer deldev2(@Param("devid") Integer devid, @Param("moduleid") Integer moduleid);

	public Integer insertdevmapimg(@Param("picnum") String picnum, @Param("picpath") String picpath,
			@Param("site") String site, @Param("itemid") Integer itemid);

	public Integer deldevmapimg(@Param("id") Integer id);

	public Integer delallgroupimg(@Param("groupid") Integer groupid);

	public Integer insertvodrelation(@Param("deviceid") Integer deviceid, @Param("videoid") Integer videoid,
			@Param("moduleid") Integer moduleid);

	public Groupdevcount selectmapimgcount(@Param("itemid") Integer itemid, @Param("site") String site);

	public Integer updategroupxy(@Param("groupid") Integer groupid, @Param("x") Double x, @Param("y") Double y,
			@Param("groupcode") String groupcode);

	public Integer updatedevxy(@Param("id") Integer id, @Param("x") Double x, @Param("y") Double y,
			@Param("code") String code, @Param("moduleid") Integer moduleid);

	List<Grouplist> selectAllItem(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid);

	public Integer delitem(@Param("groupid") Integer groupid);
	
	List<AllDevByGroupID> selectDevByGroupID(@Param("groupid") Integer groupid, @Param("moduleid") Integer moduleid);
	
	Integer  updateItemPic(@Param("itemid") Integer itemid, @Param("updateTime") String updateTime,@Param("picurl") String picurl)throws MyException;
}
