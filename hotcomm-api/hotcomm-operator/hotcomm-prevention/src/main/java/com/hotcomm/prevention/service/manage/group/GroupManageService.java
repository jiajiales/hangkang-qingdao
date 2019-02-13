package com.hotcomm.prevention.service.manage.group;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.hotcomm.prevention.bean.mysql.common.vo.ExcelTips;
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
import com.hotcomm.prevention.bean.mysql.manage.group.GroupSiteImgpath;
import com.hotcomm.prevention.bean.mysql.manage.group.GroupVO;
import com.hotcomm.prevention.bean.mysql.manage.group.SiteDevVO;
import com.hotcomm.prevention.bean.mysql.manage.group.entity.AppSum;
import com.hotcomm.prevention.exception.MyException;

public interface GroupManageService {

	List<GroupInfoVO> selectgroupById(Integer groupId) throws MyException;

	Integer countdg(Integer moduleid, String groupname, String itemnum, Integer id) throws MyException;

	Integer insertgroup(GroupParams groupParams) throws MyException;

	Integer updateGroup(GroupParams groupParams) throws MyException;

	Integer selectGroupDev(Integer groupid) throws MyException;

	Integer deleteGroup(Integer groupid) throws MyException;

	Page<GroupListVO> selectGroupList(Integer userid, Integer moduleid, String starttime, String endtime,
			String keywords, Integer pageNum, Integer pageSize) throws MyException;

	Integer insertDevMapImg(String picnum, String picpath, String site, Integer itemid) throws MyException;

	Integer deleteImg(Integer mapimgid, Integer moduleid) throws MyException;

	GroupInfo groupInfo(Integer groupid, Integer moduleid, Integer userid) throws MyException;

	Page<GroupDevState> selectGroupList(Integer userid, Integer moduleid, Integer groupid ,Integer pageNum, Integer pageSize)
			throws MyException;

	Integer groupDevCount(Integer userid, Integer moduleid, Integer groupid) throws MyException;

	List<GroupMapImg> selectGroupImgByid(Integer groupid, Integer userid, Integer moduleid) throws MyException;

	List<GroupSiteImgpath> groupSiteImgPath(Integer groupid, Integer userid, Integer moduleid) throws MyException;

	List<SiteDevVO> selectDevbySiteid(Integer mapimgid, Integer moduleid) throws MyException;

	Integer updateGroupPosition(Integer groupid, Double x, Double y, String groupcode) throws MyException;

	List<GroupListVO> selectAllItem(Integer userid, Integer moduleid) throws MyException;

	Integer updateDevPosition(Integer id, Double x, Double y, String code, Integer moduleid) throws MyException;

	List<GroupMap> selectGroupMap(Integer userid, Integer moduleid) throws MyException;

	List<AllDevByGroupID> selectDevByGroupID(Integer groupid, Integer moduleid) throws MyException;

	Integer updateItemPic(Integer itemid, String picurl) throws MyException;

	DevGroupInfoVO selectGroupInfo(Integer moduleid, Integer groupid) throws MyException;

	List<AlarmHandleNums> selectGroupAlarmHandleNums(Integer moduleID, Integer userid, Integer queryType,
			Integer groupid);

	List<AlarmNums> selectGroupAlarmNums(Integer moduleID, Integer userid, Integer queryType, Integer groupid);

	ExcelTips saveExcelList(List<DeviceParam> typeLists,Integer groupid)throws MyException;

	Object saveExcelListb(MultipartFile file, HttpServletRequest request) throws MyException;

	List<GroupVO> selectGrouplist()throws MyException;

	GroupDevInfo selectGroupDevInfo(Integer moduleid, Integer groupid)throws MyException;

	AppSum AppMapDevnum(Integer userid)throws MyException;

	List<AreaInfo>  selectAreaInfo()throws MyException;

	Integer countdgs(Integer moduleid, String groupname, String itemnum, Integer id);	
 
}
