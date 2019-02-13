package com.hot.manage.service.common.group;

import java.util.List;


import com.hot.manage.entity.PageInfo;
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


public interface GroupallService {
    public PageInfo<Grouplist> selectgrouplist(Integer userid, Integer moduleid, String starttime, String endtime, String keywords,Integer pageNum,Integer pageSize) throws MyException;

    public List<Groupdevstate> selectgroupdevstate(Integer userid, Integer moduleid) throws MyException;

    public Map_group_xq mapgroupdevstate(Integer groupid, Integer moduleid,Integer userid) throws MyException;

    public Groupdevcount selectgroupdevcount(Integer userid, Integer moduleid,Integer groupid) throws MyException;

    public List<Groupone> selectgroupbyid(Integer groupid) throws MyException;

    public Integer delgroup(Integer groupid) throws MyException;

    public Integer insertgroup(Groups groups) throws MyException;

    public Integer updategroups(Groups groups) throws MyException;

    public List<Groupmapimg> selectgroupmapimg(Integer groupid, Integer userid, Integer moduleid ) throws MyException;

    public List<Groupsiteimgpath> selectgroupsite_imgpath(Integer groupid, Integer userid, Integer moduleid ) throws MyException;

    public List<Sitedev> selectsitedev(Integer mapimgidid, Integer moduleid ) throws MyException;

    //删除设备
    public Integer deldev(Integer devid, Integer moduleid ) throws MyException;
    public Integer deldev2(Integer devid, Integer moduleid ) throws MyException;


    public Integer insertdevmapimg(String picnum,String picpath,String site,Integer itemid) throws MyException;

    //删除楼层图片
    public Integer deldevmapimg(Integer mapimgid,Integer moduleid) throws MyException;

    //修改项目前先删除
    public Integer delallgroupimg(Integer groupid) throws MyException;

    public Groupdevcount selectmapimgcount(Integer itemid,String site) throws MyException;

    public Integer updategroupxy(Integer groupid, Double x, Double y,String groupcode) throws MyException;

    public Integer updatedevxy(Integer id, Double x, Double y,String code,Integer moduleid) throws MyException;

    List<Grouplist> selectAllItem(Integer userid,Integer moduleid)throws MyException;

    public List<Groupmap> selectgroupmap(Integer userid,Integer moduleid) throws MyException;
    
    List<AllDevByGroupID> selectDevByGroupID(Integer groupid, Integer moduleid);

    Integer updateItemPic(Integer itemid,String picurl)throws MyException;
}
