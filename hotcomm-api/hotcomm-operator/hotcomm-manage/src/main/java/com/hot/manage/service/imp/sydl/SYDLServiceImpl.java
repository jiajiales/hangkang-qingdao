package com.hot.manage.service.imp.sydl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.hot.manage.db.sydl.SYDLMapper;
import com.hot.manage.db.video.TDevVideoRelationMapper;
import com.hot.manage.entity.sydl.SYDLAlarmingTrendVO;
import com.hot.manage.entity.sydl.SYDLChangeUser;
import com.hot.manage.entity.sydl.SYDLDevList;
import com.hot.manage.entity.sydl.SYDLGroupList;
import com.hot.manage.entity.sydl.SYDLSelectOnId;
import com.hot.manage.entity.sydl.value.SYDLAddDevValue;
import com.hot.manage.entity.sydl.value.SYDLDevListValue;
import com.hot.manage.entity.sydl.value.SYDLUpdateDevVaule;
import com.hot.manage.entity.video.TDevVideoRelation;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.sydl.SYDLService;
import com.hot.manage.service.video.DevVideoService;

@Transactional
@Service
public class SYDLServiceImpl  implements SYDLService{
	@Autowired
	private SYDLMapper sYDLMapper;
	
	@Autowired
	private DevVideoService devVideoService;
	
	@Autowired
	private TDevVideoRelationMapper tDevVideoRelationMapper;
	@Override
	public Page<SYDLDevList> SYDLDevList(SYDLDevListValue sYDLDevListValue)throws MyException {
//			PageHelper.startPage(sYDLDevListValue.getPageNum(), sYDLDevListValue.getPageSize());
//		Page<SYDLDevList> page = sYDLMapper.SYDLDevList(sYDLDevListValue);
//		List<SYDLDevList> list = ConverUtil.converPage(page, SYDLDevList.class);
//		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		Page<SYDLDevList> list = sYDLMapper.SYDLDevList(sYDLDevListValue);
		return list;
	}
	//新增设备
	@Override
	public Integer SYDLAddDev(SYDLAddDevValue sYDLAddDevValue) throws MyException {
		List<SYDLSelectOnId> devnum = sYDLMapper.SYDLSelectOnId(sYDLAddDevValue.getModuleid(),
				sYDLAddDevValue.getUserid(), null, sYDLAddDevValue.getDevnum(), null);
		if (devnum.size() != 0) {
			return 201;
		}
		List<SYDLSelectOnId> macAddr = sYDLMapper.SYDLSelectOnId(sYDLAddDevValue.getModuleid(),
				sYDLAddDevValue.getUserid(), null, null, sYDLAddDevValue.getMac());
		if (macAddr.size() != 0) {
			return 202;
		}
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sYDLAddDevValue.setAddtime(df.format(day));
		sYDLAddDevValue.setState(0);
		Integer code = sYDLMapper.SYDLAddDev(sYDLAddDevValue);
		if (code > 0) {
			code = sYDLMapper.SYDLAddDevGroup(sYDLAddDevValue);
		}
		if (code > 0 & sYDLAddDevValue.getItempicid() != null) {
			code = sYDLMapper.SYDLAddDevGroupPic(sYDLAddDevValue);
		}
//		int id = sYDLMapper.SYDLDevIdByMac(sYDLAddDevValue.getMac());
		if (sYDLAddDevValue.getVideoId() != null && !sYDLAddDevValue.getVideoId().equals("")) {
			for (int j = 0; j < sYDLAddDevValue.getVideoId().size(); j++) {
				TDevVideoRelation tDevVideoRelation = new TDevVideoRelation();
				tDevVideoRelation.setDeviceid(sYDLAddDevValue.getId());
				tDevVideoRelation.setVideoid(sYDLAddDevValue.getVideoId().get(j));
				tDevVideoRelation.setIsdelete(0);
				tDevVideoRelation.setModuleid(sYDLAddDevValue.getModuleid());
				tDevVideoRelationMapper.insert(tDevVideoRelation);
			}
		}
		return code;
		
	
	}
	//删除设备
	@Override
	public Integer SYDLDeleteDev(Integer moduleid, Integer devid) throws MyException {
		sYDLMapper.SYDLDeleteDevPic(moduleid, devid);
		// 删除与摄像头的绑定
		boolean judge = devVideoService.checkDevVideoRelation(moduleid, devid);
		if (judge == true) {
			devVideoService.cutDevVideoRelation(moduleid, devid);
		}
		return sYDLMapper.SYDLDeleteDev(moduleid, devid);
	}
	//修改设备
	@Override
	public Integer SYDLUpdateDev(SYDLUpdateDevVaule sYDLUpdateDevVaule) {
		if (sYDLUpdateDevVaule.getDevnum() != null) {
			List<SYDLSelectOnId> devnum = sYDLMapper.SYDLSelectOnId(sYDLUpdateDevVaule.getModuleid(),
					sYDLUpdateDevVaule.getUserid(), null, sYDLUpdateDevVaule.getDevnum(), null);
			if (devnum.size() != 0) {
				if (!devnum.get(0).getId().equals(sYDLUpdateDevVaule.getDevid())) {
					return 201;
				}
			}
		}
			Integer code = sYDLMapper.SYDLUpdateDev(sYDLUpdateDevVaule);
			if (code > 0 & sYDLUpdateDevVaule.getItempicid() != null) {
				sYDLMapper.SYDLUpdateDevAddPic(sYDLUpdateDevVaule);
			}

			if (sYDLUpdateDevVaule.getVideoid() != null) {
				String[] v = sYDLUpdateDevVaule.getVideoid().split(",");
				// 删除所有关联
				code = sYDLMapper.SYDLUpdateDevVideoDel(sYDLUpdateDevVaule.getModuleid(), sYDLUpdateDevVaule.getDevid());
				for (int i = 0; i < v.length; i++) {
					// 如果还需要该关联，修改回来
					code = sYDLMapper.SYDLUpdateDevVideo(sYDLUpdateDevVaule.getModuleid(), sYDLUpdateDevVaule.getDevid(),
							Integer.parseInt(v[i]));
					// 如果需要新增的关联不存在，会修改行数为0,code=0
					if (code <= 0) {
						code = sYDLMapper.SYDLUpdateDevVideoAdd(sYDLUpdateDevVaule.getModuleid(),
								sYDLUpdateDevVaule.getDevid(), Integer.parseInt(v[i]));
					}
				}
			}
			return code;
	}
	//加入到签到列表
	
	@Override
	public Integer SYDLAddSignDevList(Integer moduleid, String devid, Integer userid, Integer patrolid) 
		throws MyException {
			return sYDLMapper.SYDLAddSignDevList(moduleid, devid, userid, patrolid);
	}
	// 查询可更换的项目组
	@Override
	public List<SYDLGroupList> SYDLGroupList(Integer userid, Integer id, Integer moduleid, String groupname,
			String itemnum) throws MyException {
		return sYDLMapper.SYDLGroupList(userid, id, moduleid, groupname, itemnum);

	}
	// 根据设备id查询设备
	@Override
	public List<SYDLSelectOnId> SYDLSelectOnId(Integer moduleid, Integer userid, Integer devid, String devnum,
			String macAddr) {
		return sYDLMapper.SYDLSelectOnId(moduleid, userid, devid, devnum, macAddr);

	}
	//更换设备
	@Override
	public Integer SYDLChangeDev(SYDLUpdateDevVaule sYDLUpdateDevVaule) {
		SYDLDevList list = sYDLMapper.SYDLChangeDevMac(sYDLUpdateDevVaule.getMac());
		if (list != null && sYDLUpdateDevVaule.getDevid()!=list.getDevid()) {
			return 201;
		}
		return sYDLMapper.SYDLChangeDev(sYDLUpdateDevVaule);
	}
	
	// 批量修改责任人
	@Override
	public Integer SYDLchangeDevOwn(SYDLChangeUser sYDLChangeUser) {
		return sYDLMapper.SYDLchangeDevOwn(sYDLChangeUser);

	}
	@Override
	public List<SYDLAlarmingTrendVO> AlarmingTrendForSYDL(Integer queryType, Integer userid, Integer groupid) {
		return sYDLMapper.AlarmingTrendForSYDL(queryType, userid, groupid);
	}

}
