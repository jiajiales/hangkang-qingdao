package com.hotcomm.prevention.service.manage.group;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import com.hotcomm.prevention.bean.mysql.manage.group.GroupMaps;
import com.hotcomm.prevention.bean.mysql.manage.group.GroupParams;
import com.hotcomm.prevention.bean.mysql.manage.group.GroupPic;
import com.hotcomm.prevention.bean.mysql.manage.group.GroupSiteImgpath;
import com.hotcomm.prevention.bean.mysql.manage.group.GroupVO;
import com.hotcomm.prevention.bean.mysql.manage.group.SiteDevVO;
import com.hotcomm.prevention.bean.mysql.manage.group.entity.AppSum;
import com.hotcomm.prevention.db.mysql.manage.group.GroupManageMapper;
import com.hotcomm.prevention.exception.MyException;

@Service
public class GroupManageServiceImpl implements GroupManageService {
	@Autowired
	private GroupManageMapper groupManageMapper;

	@Override
	public List<GroupInfoVO> selectgroupById(Integer groupId) throws MyException {
		return groupManageMapper.selectgroupById(groupId);
	}

	// 查询项目名称和编号是否重复
	@Override
	public Integer countdg(Integer moduleid, String groupname, String itemnum, Integer id) throws MyException {
		return groupManageMapper.countdg(moduleid, groupname, itemnum, id);
	}

	// 新增项目
	@Transactional
	@Override
	public Integer insertgroup(GroupParams groupParams) throws MyException {
		Integer code = groupManageMapper.insertGroup(groupParams);
		if (code > 0) {
			if (groupParams.getSitelist() != null) {
				int a = groupParams.getSitelist().size();
				for (int i = 0; i < a; i++) {
					groupManageMapper.insertGroupImg(groupParams.getId(), groupParams.getSitelist().get(i).getPicnum(),
							groupParams.getSitelist().get(i).getPicpath(), groupParams.getSitelist().get(i).getSite());
				}
			}
			groupManageMapper.insertGroupUserRelation(groupParams.getId(), groupParams.getUserid());
		}
		return code;
	}

	// 更新项目
	@Transactional
	@Override
	public Integer updateGroup(GroupParams groupParams) throws MyException {
		Integer code = groupManageMapper.updateGroup(groupParams);
		if (code > 0) {

			groupManageMapper.removeGroupImg(groupParams.getId());

			if (groupParams.getSitelist() != null) {

				for (GroupMaps g : groupParams.getSitelist()) {
					if (g.getId() == null) {
						groupManageMapper.insertGroupImg(groupParams.getId(), g.getPicnum(), g.getPicpath(),
								g.getSite());

					} else {
						GroupPic tItemPic = new GroupPic();
						tItemPic.setId(g.getId());
						tItemPic.setIsdelete(false);
						tItemPic.setIsenable(true);
						int selective = groupManageMapper.updateByPrimaryKeySelective(tItemPic);
						if (selective <= 0) {
							throw new MyException("0", "失败");
						}
					}

				}

			}

		}
		return code;
	}

	@Override
	public Integer selectGroupDev(Integer groupid) throws MyException {
		return groupManageMapper.selectGroupDev(groupid);
	}

	// 删除项目
	@Transactional
	@Override
	public Integer deleteGroup(Integer groupid) throws MyException {
		Integer code = groupManageMapper.selectGroupUser(groupid);

		if (code > 0) {
			groupManageMapper.deleteGroupUser(groupid);
			return groupManageMapper.deleteGroup(groupid);
		} else {
			return groupManageMapper.deleteGroup(groupid);
		}

	}

	// 项目列表
	@Override
	public Page<GroupListVO> selectGroupList(Integer userid, Integer moduleid, String starttime, String endtime,
			String keywords, Integer pageNum, Integer pageSize) throws MyException {
		return groupManageMapper.selectGroupList(userid, moduleid, starttime, endtime, keywords, pageNum, pageSize);
	}

	@Override
	public Integer insertDevMapImg(String picnum, String picpath, String site, Integer itemid) throws MyException {
		return groupManageMapper.insertDevMapImg(picnum, picpath, site, itemid);
	}

	// 删除楼层图片
	@Override
	public Integer deleteImg(Integer mapimgid, Integer moduleid) throws MyException {
		Integer code = groupManageMapper.selectExistRelationImg(mapimgid, moduleid);

		if (code > 0) {
			return -1;
		} else {
			return groupManageMapper.deleteImg(mapimgid);
		}
	}

	// 地图项目弹框
	@Override
	public GroupInfo groupInfo(Integer groupid, Integer moduleid, Integer userid) throws MyException {

		return groupManageMapper.groupInfo(groupid, moduleid, userid);
	}

	@Override
	public Page<GroupDevState> selectGroupList(Integer userid, Integer moduleid, Integer groupid, Integer pageNum,
			Integer pageSize) throws MyException {

		return groupManageMapper.selectGroupDevState(userid, moduleid, groupid);
	}

	@Override
	public Integer groupDevCount(Integer userid, Integer moduleid, Integer groupid) throws MyException {

		return groupManageMapper.groupDevCount(userid, moduleid, groupid);
	}

	@Override
	public List<GroupMapImg> selectGroupImgByid(Integer groupid, Integer userid, Integer moduleid) throws MyException {

		return groupManageMapper.selectGroupImgByid(groupid, userid, moduleid);
	}

	@Override
	public List<GroupSiteImgpath> groupSiteImgPath(Integer groupid, Integer userid, Integer moduleid)
			throws MyException {

		return groupManageMapper.groupSiteImgPath(groupid, userid, moduleid);
	}

	@Override
	public List<SiteDevVO> selectDevbySiteid(Integer mapimgid, Integer moduleid) throws MyException {
		return groupManageMapper.selectDevbySiteid(mapimgid, moduleid);
	}

	@Override
	public Integer updateGroupPosition(Integer groupid, Double x, Double y, String groupcode) throws MyException {

		return groupManageMapper.updateGroupPosition(groupid, x, y, groupcode);
	}

	@Override
	public List<GroupListVO> selectAllItem(Integer userid, Integer moduleid) throws MyException {

		return groupManageMapper.selectAllItem(userid, moduleid);
	}

	@Override
	public Integer updateDevPosition(Integer id, Double x, Double y, String code, Integer moduleid) throws MyException {

		return groupManageMapper.updateDevPosition(id, x, y, code, moduleid);
	}

	@Override
	public List<GroupMap> selectGroupMap(Integer userid, Integer moduleid) throws MyException {

		return groupManageMapper.selectGroupMap(userid, moduleid);
	}

	@Override
	public List<AllDevByGroupID> selectDevByGroupID(Integer groupid, Integer moduleid) throws MyException {
		return groupManageMapper.selectDevByGroupID(groupid, moduleid);
	}

	@Override
	public Integer updateItemPic(Integer itemid, String picurl) throws MyException {

		return groupManageMapper.updateItemPic(itemid, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
				picurl);
	}

	@Override
	public DevGroupInfoVO selectGroupInfo(Integer moduleid, Integer groupid) throws MyException {
		return groupManageMapper.selectGroupInfo(moduleid, groupid);
	}

	@Override
	public List<AlarmHandleNums> selectGroupAlarmHandleNums(Integer moduleID, Integer userid, Integer queryType,
			Integer groupid) {
		return groupManageMapper.selectGroupAlarmHandleNums(moduleID, userid, queryType, groupid);
	}

	@Override
	public List<AlarmNums> selectGroupAlarmNums(Integer moduleID, Integer userid, Integer queryType, Integer groupid) {

		return groupManageMapper.selectGroupAlarmNums(moduleID, userid, queryType, groupid);
	}
	@Transactional
	@Override
	public ExcelTips saveExcelList(List<DeviceParam> typeLists,Integer groupid) throws MyException {
		List<String> listSuccese=new ArrayList<>();
		List<String> listFailded=new ArrayList<>();
		for (int k=0;k<typeLists.size();k++) {
			// 调用mapper的保存方法
			DeviceParam type=new DeviceParam();
			Integer i = groupManageMapper.selectCode(typeLists.get(k).getMac());
			if (i > 0) {
				listFailded.add(typeLists.get(k).getMac());
			}else {
				listSuccese.add(typeLists.get(k).getMac());
				type=typeLists.get(k);
				groupManageMapper.insertSelectives(type);
				groupManageMapper.addGroup(groupid,type.getId(),type.getModuleid(),type.getAdduserid());
			}
		}
		ExcelTips tips=new ExcelTips();
		tips.setSuccesefull(listSuccese.size());
		tips.setFailed(listFailded.size());
		tips.setMac(listFailded);
		return tips;
	}

	@Override
	public Object saveExcelListb(MultipartFile file, HttpServletRequest request) throws MyException {
		return null;
	}

	@Override
	public List<GroupVO> selectGrouplist() throws MyException {
		return groupManageMapper.selectGrouplist();
	}

	@Override
	public GroupDevInfo selectGroupDevInfo(Integer moduleid, Integer groupid) {
		return groupManageMapper.selectGroupDevInfo(moduleid, groupid);
	}

	@Override
	public AppSum AppMapDevnum(Integer userid) {

		return groupManageMapper.AppMapDevnum(userid);
	}

	@Override
	public List<AreaInfo> selectAreaInfo() throws MyException {

		return groupManageMapper.selectAreaInfo();
	}

	@Override
	public Integer countdgs(Integer moduleid, String groupname, String itemnum, Integer id) {
		return groupManageMapper.countdg(moduleid, groupname, itemnum);
	}

}
