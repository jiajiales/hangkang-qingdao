package com.hot.manage.service.imp.jg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.db.jg.LKTJGMapper;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.jg.JGChangeUser;
import com.hot.manage.entity.jg.JGDevAlarmHandleByTimeVO;
import com.hot.manage.entity.jg.JGDevCount;
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
import com.hot.manage.service.jg.LKTJGService;
import com.hot.manage.utils.ConverUtil;

@Transactional
@Service
public class LKTJGServiceImpl implements LKTJGService {

	@Autowired
	private LKTJGMapper lKTMapper;

	// 设备列表数据
	@Override
	public PageInfo<LKTDevList> LKTDevList(LKTDevListVaule lktDevListVaule) throws MyException {
		// TODO Auto-generated method stub
		PageHelper.startPage(lktDevListVaule.getPageNum(), lktDevListVaule.getPageSize());
		Page<LKTDevList> page = lKTMapper.LKTDevList(lktDevListVaule);
		List<LKTDevList> list = ConverUtil.converPage(page, LKTDevList.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	// 加入设备签到列表
	@Override
	public Integer LKTAddSignDevList(Integer moduleid, String devid, Integer patrolid) throws MyException {
		// TODO Auto-generated method stub
		return lKTMapper.LKTAddSignDevList(moduleid, devid, patrolid);
	}

	// 修改设备数据
	@Override
	public Integer LKTUpdateDev(LKTUpdateDevVaule lktUpdateDevVaule) throws MyException {
		// TODO Auto-generated method stub
		if (lktUpdateDevVaule.getDevnum() != null) {
			List<LKTSelectOnId> devnum = lKTMapper.LKTSelectOnIdpic(lktUpdateDevVaule.getModuleid(),
					lktUpdateDevVaule.getUserid(), null, lktUpdateDevVaule.getDevnum(), null);
			if (devnum.size() != 0) {
				if (!devnum.get(0).getId().equals(lktUpdateDevVaule.getDevid())) {
					return 201;
				}
			}
		}
		Integer code = lKTMapper.LKTUpdateDev(lktUpdateDevVaule);
		Integer result = lKTMapper.selectCountDevReVideo(lktUpdateDevVaule.getDevid());
		if (result > 0) {
			result = lKTMapper.LKTDelectVideoRe(lktUpdateDevVaule.getDevid());
		}
		if (lktUpdateDevVaule.getVideoid() != null) {
			for (Integer videoid : lktUpdateDevVaule.getVideoid()) {
				Integer re = lKTMapper.checkJgRelationVideo(lktUpdateDevVaule.getDevid(), videoid);
				if (re > 0) {
					lKTMapper.updateJgRelationVideo(lktUpdateDevVaule.getDevid(), videoid);
				} else {
					lKTMapper.insertJgRelationVideo(lktUpdateDevVaule.getDevid(), videoid);
				}
			}
		}
		if (code > 0 & lktUpdateDevVaule.getItempicid() != null) {
			lKTMapper.LKTUpdateDevAddPic(lktUpdateDevVaule);
		}
		return code;
	}

	@Override
	public List<LKTGroupList> LKTGroupList(Integer userid, Integer id, Integer moduleid, String groupname,
			String itemnum) {
		// TODO Auto-generated method stub
		return lKTMapper.LKTGroupList(userid, id, moduleid, groupname, itemnum);
	}

	@Override
	public Integer LKTDeleteDev(Integer moduleid, Integer devid) throws MyException {
		// TODO Auto-generated method stub
		Integer result = lKTMapper.selectCountDevReVideo(devid);
		if (result > 0) {
			result = lKTMapper.LKTDelectVideoRe(devid);
		}
		return lKTMapper.LKTDeleteDev(moduleid, devid);
	}

	@Override
	public LKTJgDevNum LKTJgDevNum(Integer moduleid, Integer userid) throws MyException {
		// TODO Auto-generated method stub
		return lKTMapper.LKTJgDevNum(moduleid, userid);
	}

	@Override
	public PageInfo<LKTJgItemList> LKTJgItemList(LKTDevListVaule lktDevListVaule) throws MyException {
		// TODO Auto-generated method stub
		PageHelper.startPage(lktDevListVaule.getPageNum(), lktDevListVaule.getPageSize());
		Page<LKTJgItemList> page = lKTMapper.LKTJgItemList(lktDevListVaule);
		List<LKTJgItemList> list = ConverUtil.converPage(page, LKTJgItemList.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	@Override
	public Integer LKTDeleteItem(Integer id) throws MyException {
		// TODO Auto-generated method stub
		LKTCode code = lKTMapper.LKTDeleteItemcondition(id);
		if (code.getCode() > 0) {
			return 201;
		}
		lKTMapper.LKTDeleteItemPic(id);
		return lKTMapper.LKTDeleteItem(id);
	}

	@Override
	public Integer LKTUpdateItem(LKTUpdateItemVaule lktUpdateItemVaule) throws MyException {
		// TODO Auto-generated method stub
		if (lktUpdateItemVaule.getGroupname() != null) {
			List<LKTGroupList> groupname = lKTMapper.LKTGroupList(lktUpdateItemVaule.getUserid(), null, 3,
					lktUpdateItemVaule.getGroupname(), null);
			if (groupname.size() != 0) {
				if (!groupname.get(0).getId().equals(lktUpdateItemVaule.getItemid())) {
					return 201;
				}
			}
		}
		if (lktUpdateItemVaule.getItemnum() != null) {
			List<LKTGroupList> item = lKTMapper.LKTGroupList(lktUpdateItemVaule.getUserid(), null, 3, null,
					lktUpdateItemVaule.getItemnum());
			if (item.size() != 0) {
				if (!item.get(0).getId().equals(lktUpdateItemVaule.getItemid())) {
					return 202;
				}
			}
		}
		Integer code = lKTMapper.LKTUpdateItem(lktUpdateItemVaule);
		if (code > 0 & lktUpdateItemVaule.getSitelist() != null) {
			code = lKTMapper.LKTUpdateItemDelPic(lktUpdateItemVaule);// 全部删除
			for (int i = 0; i < lktUpdateItemVaule.getSitelist().size(); i++) {
				Date day = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				code = lKTMapper.LKTUpdateItemPic(lktUpdateItemVaule.getSitelist().get(i).getPicnum(),
						lktUpdateItemVaule.getSitelist().get(i).getPicpath(),
						lktUpdateItemVaule.getSitelist().get(i).getSite(), lktUpdateItemVaule.getItemid(),
						df.format(day));// 修改楼层信息，并修改为未删除状态
				if (code <= 0) {// 修改行数为0，数据不存在，即为新增数据
					code = lKTMapper.LKTJgAddGroupPic(lktUpdateItemVaule.getSitelist().get(i).getPicnum(),
							lktUpdateItemVaule.getSitelist().get(i).getPicpath(),
							lktUpdateItemVaule.getSitelist().get(i).getSite(), lktUpdateItemVaule.getItemid(),
							df.format(day));
				}
				if (code <= 0) {
					return code;
				}
			}
		}
		return code;
	}

	@Override
	public List<LKTGroupListDev> LKTGroupListDev(Integer moduleid, Integer groupid, String site) throws MyException {
		// TODO Auto-generated method stub
		return lKTMapper.LKTGroupListDev(moduleid, groupid, site);
	}

	@Override
	public List<LKTJgItemListMap> LKTJgItemListMap(Integer moduleid, Integer userid) throws MyException {
		// TODO Auto-generated method stub

		return lKTMapper.LKTJgItemListMap(moduleid, userid);
	}

	@Override
	public LKTJgDevNum LKTGroupListDevnum(Integer moduleid, Integer userid, Integer groupid) throws MyException {
		// TODO Auto-generated method stub
		return lKTMapper.LKTGroupListDevnum(moduleid, userid, groupid);
	}

	@Override
	public LKTSelectOnId LKTSelectOnIdpic(Integer moduleid, Integer userid, Integer devid, String devnum,
			String macAddr) throws MyException {
		// TODO Auto-generated method stub
		return lKTMapper.LKTSelectOnIdpicToOne(moduleid, userid, devid, devnum, macAddr);
	}

	@Override
	public Integer LKTJgAddDev(LKTJgAddDevVaule lktJgAddDevVaule) throws MyException {
		// TODO Auto-generated method stub
		List<LKTSelectOnId> devnum = lKTMapper.LKTSelectOnIdpic(lktJgAddDevVaule.getModuleid(),
				lktJgAddDevVaule.getUserid(), null, lktJgAddDevVaule.getDevnum(), null);
		if (devnum.size() != 0) {
			return 201;
		}
		List<LKTSelectOnId> macAddr = lKTMapper.LKTSelectOnIdpic(lktJgAddDevVaule.getModuleid(),
				lktJgAddDevVaule.getUserid(), null, null, lktJgAddDevVaule.getMacAddr());
		if (macAddr.size() != 0) {
			return 202;
		}
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		lktJgAddDevVaule.setAddtime(df.format(day));
		Integer code = lKTMapper.LKTJgAddDev(lktJgAddDevVaule);
		if (code > 0) {
			code = lKTMapper.LKTJgAddDevGroup(lktJgAddDevVaule);
		}
		if (code > 0 & lktJgAddDevVaule.getItempicid() != null) {
			code = lKTMapper.LKTJgAddDevGroupPic(lktJgAddDevVaule);
		}
		if (code > 0 & lktJgAddDevVaule.getVideoid() != null) {
			for (Integer videoid : lktJgAddDevVaule.getVideoid()) {
				code = lKTMapper.insertJgRelationVideo(lktJgAddDevVaule.getId(), videoid);
			}
		}
		return code;
	}

	@Override
	public Integer LKTJgAddGroup(LKTJgAddGroupVaule lktJgAddGroupVaule) throws MyException {
		List<LKTGroupList> groupname = lKTMapper.LKTGroupList(lktJgAddGroupVaule.getUserid(), null,
				lktJgAddGroupVaule.getModuleid(), lktJgAddGroupVaule.getGroupname(), null);
		if (groupname.size() != 0) {
			return 201;
		}
		List<LKTGroupList> item = lKTMapper.LKTGroupList(lktJgAddGroupVaule.getUserid(), null,
				lktJgAddGroupVaule.getModuleid(), null, lktJgAddGroupVaule.getItemnum());
		if (item.size() != 0) {
			return 202;
		}
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		lktJgAddGroupVaule.setFatherid(0);
		lktJgAddGroupVaule.setCount(0);
		lktJgAddGroupVaule.setAddtime(df.format(day));
		Integer code = lKTMapper.LKTJgAddGroup(lktJgAddGroupVaule);
		if (code > 0) {
			code = lKTMapper.LKTJgAddGroupUser(lktJgAddGroupVaule);
		}
		if (code > 0 & lktJgAddGroupVaule.getSitelist() != null) {
			for (int i = 0; i < lktJgAddGroupVaule.getSitelist().size(); i++) {
				code = lKTMapper.LKTJgAddGroupPic(lktJgAddGroupVaule.getSitelist().get(i).getPicnum(),
						lktJgAddGroupVaule.getSitelist().get(i).getPicpath(),
						lktJgAddGroupVaule.getSitelist().get(i).getSite(), lktJgAddGroupVaule.getId(),
						lktJgAddGroupVaule.getAddtime());
				if (code <= 0) {
					return code;
				}
			}
		}
		return code;
	}

	@Override
	public List<LKTSeleteMap> LKTSeleteMap(Integer groupid, Integer userid) {
		return lKTMapper.LKTSeleteMap(groupid, userid);
	}

	@Override
	public List<Optionaluser> getUser(Integer userid) throws MyException {
		return lKTMapper.getUser(userid);
	}

	@Override
	public Integer updateDeviceOwn(JGChangeUser jgChangeUser) {
		return lKTMapper.updateDeviceOwn(jgChangeUser);
	}

	@Override
	public Integer LKTJGChangeDev(Integer devid, String mac) throws MyException {
		LKTDevList list = lKTMapper.LKTJGChangeDevMac(mac);
		if (list != null) {
			return 201;
		}
		return lKTMapper.LKTJGChangeDev(devid, mac);
	}

	@Override
	public List<JGDevCount> selectCountBypurpose(Integer userid) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "电信井盖");
		map.put(2, "自来水井盖");
		map.put(3, "电力井盖");
		map.put(4, "消费井盖");
		map.put(5, "环卫井盖");
		List<JGDevCount> list = new ArrayList<>();
		for (int i = 1; i <= map.size(); i++) {
			JGDevCount jGDevCount=new JGDevCount();
			jGDevCount.setType(map.get(i));
			jGDevCount.setCount(lKTMapper.selectCountBypurpose(i, userid));
			list.add(jGDevCount);
		}
		return list;
	}

	@Override
	public List<JGDevCount> selectCountByloadbear(Integer userid) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "轻型");
		map.put(2, "普通型");
		map.put(3, "重型");
		map.put(4, "超重型");
		List<JGDevCount> list = new ArrayList<>();
		for (int i = 1; i <= map.size(); i++) {
			JGDevCount jGDevCount=new JGDevCount();
			jGDevCount.setType(map.get(i));
			jGDevCount.setCount(lKTMapper.selectCountByloadbear(i, userid));
			list.add(jGDevCount);
		}
		return list;
	}

	@Override
	public List<JGDevAlarmHandleByTimeVO> JGselectDevAlarmHandleByTime(Integer queryType, String startTime,
			String endTime,Integer userid) {
		return lKTMapper.JGselectDevAlarmHandleByTime(queryType, startTime, endTime,userid);
	}

	@Override
	public List<JGDevRate> selectDevRate(Integer queryType, String startTime, String endTime, Integer userid) {
		return lKTMapper.selectDevRate(queryType, startTime, endTime, userid);
	}

}
