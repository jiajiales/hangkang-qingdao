package com.hot.manage.service.imp.hw;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.db.hw.LKTHWMapper;
import com.hot.manage.db.video.TDevVideoRelationMapper;
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
import com.hot.manage.entity.video.TDevVideoRelation;
import com.hot.manage.entity.yg.LKTCode;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.hw.LKTHWService;
import com.hot.manage.service.video.DevVideoService;
import com.hot.manage.utils.ConverUtil;

@Transactional
@Service
public class LKTHWServiceImpl implements LKTHWService {

	@Autowired
	private TDevVideoRelationMapper tDevVideoRelationMapper;
	@Autowired
	private DevVideoService devVideoService;
	@Autowired
	private LKTHWMapper lKTMapper;

	@Override
	public PageInfo<LKTHWDevList> LKTHWDevList(LKTHWDevListVaule lkthwDevListVaule) throws MyException {
		// TODO Auto-generated method stub
		PageHelper.startPage(lkthwDevListVaule.getPageNum(), lkthwDevListVaule.getPageSize());
		Page<LKTHWDevList> page = lKTMapper.LKTHWDevList(lkthwDevListVaule);
		List<LKTHWDevList> list = ConverUtil.converPage(page, LKTHWDevList.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	@Override
	public Integer LKTHWAddSignDevList(Integer moduleid, String devid, Integer userid, Integer patrolid)
			throws MyException {
		return lKTMapper.LKTHWAddSignDevList(moduleid, devid, userid, patrolid);
	}

	@Override
	public Integer LKTHWUpdateDev(LKTHWUpdateDevVaule lkthwUpdateDevVaule) throws MyException {
		if (lkthwUpdateDevVaule.getDevnum() != null) {
			LKTHWSelectOnId devnum = lKTMapper.LKTHWSelectOnId(lkthwUpdateDevVaule.getModuleid(),
					lkthwUpdateDevVaule.getUserid(), null, lkthwUpdateDevVaule.getDevnum(), null);
			if (devnum != null) {
				if (!devnum.getId().equals(lkthwUpdateDevVaule.getDevid())) {
					return 201;
				}
			}
		}
		Integer code = lKTMapper.LKTHWUpdateDev(lkthwUpdateDevVaule);
		if (code > 0 & lkthwUpdateDevVaule.getItempicid() != null) {
			lKTMapper.LKTHWUpdateDevAddPic(lkthwUpdateDevVaule);
		}

		if (lkthwUpdateDevVaule.getVideoid() != null) {
			String[] v = lkthwUpdateDevVaule.getVideoid().split(",");
			// 删除所有关联
			code = lKTMapper.LKTHWUpdateDevVideoDel(lkthwUpdateDevVaule.getModuleid(), lkthwUpdateDevVaule.getDevid());
			for (int i = 0; i < v.length; i++) {
				// 如果还需要该关联，修改回来
				code = lKTMapper.LKTHWUpdateDevVideo(lkthwUpdateDevVaule.getModuleid(), lkthwUpdateDevVaule.getDevid(),
						Integer.parseInt(v[i]));
				// 如果需要新增的关联不存在，会修改行数为0,code=0
				if (code <= 0) {
					code = lKTMapper.LKTHWUpdateDevVideoAdd(lkthwUpdateDevVaule.getModuleid(),
							lkthwUpdateDevVaule.getDevid(), Integer.parseInt(v[i]));
				}
			}
		}
		return code;
	}

	@Override
	public Integer LKTHWDeleteDev(Integer moduleid, Integer devid) throws MyException {
		lKTMapper.LKTHWDeleteDevPic(moduleid, devid);
		// 删除与摄像头的绑定
		boolean judge = devVideoService.checkDevVideoRelation(moduleid, devid);
		if (judge == true) {
			devVideoService.cutDevVideoRelation(moduleid, devid);
		}
		return lKTMapper.LKTHWDeleteDev(moduleid, devid);
	}

	@Override
	public List<LKTGroupListDev> LKTHWGroupListDev(Integer moduleid, Integer groupid, String site) throws MyException {
		// TODO Auto-generated method stub
		return lKTMapper.LKTHWGroupListDev(moduleid, groupid, site);
	}

	@Override
	public LKTJgDevNum LKTHWGroupListDevnum(Integer groupid, Integer moduleid, Integer userid) throws MyException {
		// TODO Auto-generated method stub
		return lKTMapper.LKTHWGroupListDevnum(groupid, moduleid, userid);
	}

	@Override
	public LKTJgDevNum LKTHWDevNum(Integer moduleid, Integer userid) throws MyException {
		// TODO Auto-generated method stub
		return lKTMapper.LKTHWDevNum(moduleid, userid);
	}

	@Override
	public PageInfo<LKTJgItemList> LKTHWItemList(LKTHWDevListVaule lkthwDevListVaule) throws MyException {
		// TODO Auto-generated method stub
		PageHelper.startPage(lkthwDevListVaule.getPageNum(), lkthwDevListVaule.getPageSize());
		Page<LKTJgItemList> page = lKTMapper.LKTHWItemList(lkthwDevListVaule);
		List<LKTJgItemList> list = ConverUtil.converPage(page, LKTJgItemList.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	@Override
	public Integer LKTHWDeleteItem(Integer id) {
		// TODO Auto-generated method stub
		LKTCode code = lKTMapper.LKTHWDeleteItemcondition(id);
		if (code.getCode() > 0) {
			return 0;
		}
		return lKTMapper.LKTHWDeleteItem(id);
	}

	@Override
	public LKTHWSelectOnId LKTHWSelectOnId(Integer moduleid, Integer userid, Integer devid, String devnum,
			String macAddr) throws MyException {
		// TODO Auto-generated method stub
		return lKTMapper.LKTHWSelectOnId(moduleid, userid, devid, devnum, macAddr);
	}

	@Override
	public List<LKTGroupList> LKTHWGroupList(Integer userid, Integer id, Integer moduleid, String groupname,
			String itemnum) {
		// TODO Auto-generated method stub
		return lKTMapper.LKTHWGroupList(userid, id, moduleid, groupname, itemnum);
	}

	@Override
	public Integer LKTHWUpdateItem(LKTUpdateItemVaule lktUpdateItemVaule) {
		if (lktUpdateItemVaule.getGroupname() != null) {
			List<LKTGroupList> groupname = lKTMapper.LKTHWGroupList(lktUpdateItemVaule.getUserid(), null, 8,
					lktUpdateItemVaule.getGroupname(), null);
			if (groupname.size() != 0) {
				if (!groupname.get(0).getId().equals(lktUpdateItemVaule.getItemid())) {
					return 201;
				}
			}
		}
		if (lktUpdateItemVaule.getItemnum() != null) {
			List<LKTGroupList> item = lKTMapper.LKTHWGroupList(lktUpdateItemVaule.getUserid(), null, 8, null,
					lktUpdateItemVaule.getItemnum());
			if (item.size() != 0) {
				if (!item.get(0).getId().equals(lktUpdateItemVaule.getItemid())) {
					return 202;
				}
			}
		}
		Integer code = lKTMapper.LKTHWUpdateItem(lktUpdateItemVaule);
		if (code > 0 & lktUpdateItemVaule.getSitelist() != null) {
			code = lKTMapper.LKTHWUpdateItemDelPic(lktUpdateItemVaule);// 全部删除
			for (int i = 0; i < lktUpdateItemVaule.getSitelist().size(); i++) {
				Date day = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				code = lKTMapper.LKTHWUpdateItemPic(lktUpdateItemVaule.getSitelist().get(i).getPicnum(),
						lktUpdateItemVaule.getSitelist().get(i).getPicpath(),
						lktUpdateItemVaule.getSitelist().get(i).getSite(), lktUpdateItemVaule.getItemid(),
						df.format(day));// 修改楼层信息，并修改为未删除状态
				if (code <= 0) {// 修改行数为0，数据不存在，即为新增数据
					code = lKTMapper.LKTHWAddGroupPic(lktUpdateItemVaule.getSitelist().get(i).getPicnum(),
							lktUpdateItemVaule.getSitelist().get(i).getPicpath(),
							lktUpdateItemVaule.getSitelist().get(i).getSite(), lktUpdateItemVaule.getItemid(),
							df.format(day));
				}
			}
			if (code <= 0) {
				return code;
			}
		}
		return code;
	}

	@Override
	public List<LKTJgItemListMap> LKTHWItemListMap(Integer moduleid, Integer userid) throws MyException {
		// TODO Auto-generated method stub
		return lKTMapper.LKTHWItemListMap(moduleid, userid);
	}

	@Override
	public Integer LKTHWAddDev(LKTHWAddDevVaule lkthwAddDevVaule) throws MyException {
		// TODO Auto-generated method stub
		LKTHWSelectOnId devnum = lKTMapper.LKTHWSelectOnId(lkthwAddDevVaule.getModuleid(), lkthwAddDevVaule.getUserid(),
				null, lkthwAddDevVaule.getDevnum(), null);
		if (devnum != null) {
			return 201;
		}
		LKTHWSelectOnId macAddr = lKTMapper.LKTHWSelectOnId(lkthwAddDevVaule.getModuleid(),
				lkthwAddDevVaule.getUserid(), null, null, lkthwAddDevVaule.getMac());
		if (macAddr != null) {
			return 202;
		}
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		lkthwAddDevVaule.setAddtime(df.format(day));
		lkthwAddDevVaule.setState(0);
		Integer code = lKTMapper.LKTHWAddDev(lkthwAddDevVaule);
		if (code > 0) {
			code = lKTMapper.LKTHWAddDevGroup(lkthwAddDevVaule);
		}
		if (code > 0 & lkthwAddDevVaule.getItempicid() != null) {
			code = lKTMapper.LKTHWAddDevGroupPic(lkthwAddDevVaule);
		}
		// int id = lKTMapper.LKTHWDevIdByMac(lkthwAddDevVaule.getMac());
		if (lkthwAddDevVaule.getVideoId() != null && !lkthwAddDevVaule.getVideoId().equals("")) {
			for (int j = 0; j < lkthwAddDevVaule.getVideoId().size(); j++) {
				TDevVideoRelation tDevVideoRelation = new TDevVideoRelation();
				tDevVideoRelation.setDeviceid(lkthwAddDevVaule.getId());
				tDevVideoRelation.setVideoid(lkthwAddDevVaule.getVideoId().get(j));
				tDevVideoRelation.setIsdelete(0);
				tDevVideoRelation.setModuleid(lkthwAddDevVaule.getModuleid());
				tDevVideoRelationMapper.insert(tDevVideoRelation);
			}
		}
		return code;
	}

	@Override
	public Integer LKTHWAddGroup(LKTHWAddGroupVaule lkthwAddGroupVaule) throws MyException {
		List<LKTGroupList> groupname = lKTMapper.LKTHWGroupList(lkthwAddGroupVaule.getUserid(), null,
				lkthwAddGroupVaule.getModuleid(), lkthwAddGroupVaule.getGroupname(), null);
		if (groupname.size() != 0) {
			return 201;
		}
		List<LKTGroupList> item = lKTMapper.LKTHWGroupList(lkthwAddGroupVaule.getUserid(), null,
				lkthwAddGroupVaule.getModuleid(), null, lkthwAddGroupVaule.getItemnum());
		if (item.size() != 0) {
			return 202;
		}
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		lkthwAddGroupVaule.setFatherid(0);
		lkthwAddGroupVaule.setAddtime(df.format(day));
		Integer code = lKTMapper.LKTHWAddGroup(lkthwAddGroupVaule);
		if (code > 0) {
			code = lKTMapper.LKTHWAddGroupUser(lkthwAddGroupVaule);
		}
		if (code > 0 & lkthwAddGroupVaule.getSitelist() != null) {
			for (int i = 0; i < lkthwAddGroupVaule.getSitelist().size(); i++) {
				code = lKTMapper.LKTHWAddGroupPic(lkthwAddGroupVaule.getSitelist().get(i).getPicnum(),
						lkthwAddGroupVaule.getSitelist().get(i).getPicpath(),
						lkthwAddGroupVaule.getSitelist().get(i).getSite(), lkthwAddGroupVaule.getId(),
						lkthwAddGroupVaule.getAddtime());
			}
			if (code <= 0) {
				return code;
			}
		}
		return code;
	}

	@Override
	public List<LKTSeleteMap> LKTHWSeleteMap(Integer groupid, Integer userid) {
		// TODO Auto-generated method stub
		return lKTMapper.LKTHWSeleteMap(groupid, userid);
	}

	@Override
	public Integer LKTHWChangeDev(Integer devid, String mac) {
		LKTHWDevList list = lKTMapper.LKTHWChangeDevMac(mac);
		if (list != null) {
			return 201;
		}
		return lKTMapper.LKTHWChangeDev(devid, mac);
	}

	@Override
	public Integer changeDevOwn(HWChangeUser hWChangeUser) {

		return lKTMapper.changeDevOwn(hWChangeUser);
	}

	@Override
	public List<HWAlarmNum> selectHWAlarmNums(Integer Time, Integer moduleID, Integer userid) throws MyException {
		List<HWAlarmNum> list=lKTMapper.selectHWAlarmNums(Time,  moduleID, userid);
		return list;
	}
}
