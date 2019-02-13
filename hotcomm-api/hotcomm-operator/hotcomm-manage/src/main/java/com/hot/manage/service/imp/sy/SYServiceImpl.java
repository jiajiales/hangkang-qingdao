package com.hot.manage.service.imp.sy;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.Page;
import com.hot.manage.db.sy.SYMapper;
import com.hot.manage.db.video.TDevVideoRelationMapper;
import com.hot.manage.entity.common.groupgk.AlarmHandleStatistics;
import com.hot.manage.entity.common.groupgk.AlarmStateStatistics;
import com.hot.manage.entity.common.groupgk.AlarmTendecyVo;
import com.hot.manage.entity.sy.SYAlarmTendency;
import com.hot.manage.entity.sy.SYChangeUser;
import com.hot.manage.entity.sy.SYDevList;
import com.hot.manage.entity.sy.SYGroupList;
import com.hot.manage.entity.sy.SYSelectOnId;
import com.hot.manage.entity.sy.value.SYAddDevValue;
import com.hot.manage.entity.sy.value.SYDevListValue;
import com.hot.manage.entity.sy.value.SYUpdateDevVaule;
import com.hot.manage.entity.video.TDevVideoRelation;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.sy.SYService;
import com.hot.manage.service.video.DevVideoService;

@Transactional
@Service
public class SYServiceImpl implements SYService {
	@Autowired
	private SYMapper sYMapper;
	
	@Autowired
	private DevVideoService devVideoService;
	
	@Autowired
	private TDevVideoRelationMapper tDevVideoRelationMapper;
	//查询设备列表
	@Override
	public Page<SYDevList> SYDevList(SYDevListValue sYDevListValue) throws MyException {
//		PageHelper.startPage(sYDevListValue.getPageNum(), sYDevListValue.getPageSize());
//		Page<SYDevList> page = sYMapper.SYDevList(sYDevListValue);
//		List<SYDevList> list = ConverUtil.converPage(page, SYDevList.class);
//		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		Page<SYDevList> list = sYMapper.SYDevList(sYDevListValue);
		return list;
	}

  //新增设备
	@Override
	public Integer SYAddDev(SYAddDevValue sYAddDevValue) throws MyException {
		List<SYSelectOnId> devnum = sYMapper.SYSelectOnId(sYAddDevValue.getModuleid(),
				sYAddDevValue.getUserid(), null, sYAddDevValue.getDevnum(), null);
		if (devnum.size() != 0) {
			return 201;
		}
		List<SYSelectOnId> macAddr = sYMapper.SYSelectOnId(sYAddDevValue.getModuleid(),
				sYAddDevValue.getUserid(), null, null, sYAddDevValue.getMac());
		if (macAddr.size() != 0) {
			return 202;
		}
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sYAddDevValue.setAddtime(df.format(day));
		sYAddDevValue.setState(0);
		Integer code = sYMapper.SYAddDev(sYAddDevValue);
		if (code > 0) {
			code = sYMapper.SYAddDevGroup(sYAddDevValue);
		}
		if (code > 0 & sYAddDevValue.getItempicid() != null) {
			code = sYMapper.SYAddDevGroupPic(sYAddDevValue);
		}
//		int id = sYMapper.SYDevIdByMac(sYAddDevValue.getMac());
		if (sYAddDevValue.getVideoId() != null && !sYAddDevValue.getVideoId().equals("")) {
			for (int j = 0; j < sYAddDevValue.getVideoId().size(); j++) {
				TDevVideoRelation tDevVideoRelation = new TDevVideoRelation();
				tDevVideoRelation.setDeviceid(sYAddDevValue.getId());
				tDevVideoRelation.setVideoid(sYAddDevValue.getVideoId().get(j));
				tDevVideoRelation.setIsdelete(0);
				tDevVideoRelation.setModuleid(sYAddDevValue.getModuleid());
				tDevVideoRelationMapper.insert(tDevVideoRelation);
			}
		}
		return code;
		
	}
	//删除设备
	@Override
	public Integer SYDeleteDev(Integer moduleid, Integer devid) throws MyException {
		sYMapper.SYDeleteDevPic(moduleid, devid);
		// 删除与摄像头的绑定
		boolean judge = devVideoService.checkDevVideoRelation(moduleid, devid);
		if (judge == true) {
			devVideoService.cutDevVideoRelation(moduleid, devid);
		}
		return sYMapper.SYDeleteDev(moduleid, devid);
	}

	
	//修改设备
	@Override
	public Integer SYUpdateDev(SYUpdateDevVaule sYUpdateDevVaule) {
		if (sYUpdateDevVaule.getDevnum() != null) {
			List<SYSelectOnId> devnum = sYMapper.SYSelectOnId(sYUpdateDevVaule.getModuleid(),
					sYUpdateDevVaule.getUserid(), null, sYUpdateDevVaule.getDevnum(), null);
			if (devnum.size() != 0) {
				if (!devnum.get(0).getId().equals(sYUpdateDevVaule.getDevid())) {
					return 201;
				}
			}
		}
		Integer code = sYMapper.SYUpdateDev(sYUpdateDevVaule);
		if (code > 0 & sYUpdateDevVaule.getItempicid() != null) {
			sYMapper.SYUpdateDevAddPic(sYUpdateDevVaule);
		}

		if (sYUpdateDevVaule.getVideoid() != null) {
			String[] v = sYUpdateDevVaule.getVideoid().split(",");
			// 删除所有关联
			code = sYMapper.SYUpdateDevVideoDel(sYUpdateDevVaule.getModuleid(), sYUpdateDevVaule.getDevid());
			for (int i = 0; i < v.length; i++) {
				// 如果还需要该关联，修改回来
				code = sYMapper.SYUpdateDevVideo(sYUpdateDevVaule.getModuleid(), sYUpdateDevVaule.getDevid(),
						Integer.parseInt(v[i]));
				// 如果需要新增的关联不存在，会修改行数为0,code=0
				if (code <= 0) {
					code = sYMapper.SYUpdateDevVideoAdd(sYUpdateDevVaule.getModuleid(),
							sYUpdateDevVaule.getDevid(), Integer.parseInt(v[i]));
				}
			}
		}
		return code;
	}
	//加入到签到列表
	@Override
	public Integer SYAddSignDevList(Integer moduleid, String devid, Integer userid, Integer patrolid)
		throws MyException {
			return sYMapper.SYAddSignDevList(moduleid, devid, userid, patrolid);
	}
	// 查询可更换的项目组
	@Override
	public List<SYGroupList> SYGroupList(Integer userid, Integer id, Integer moduleid,
			String groupname, String itemnum) throws MyException {
		
		return sYMapper.SYGroupList(userid, id, moduleid, groupname, itemnum);

	}
	// 根据设备id查询设备
	@Override
	public List<SYSelectOnId> SYSelectOnId(Integer moduleid, Integer userid, Integer devid,
			String devnum, String macAddr) {
		
		return sYMapper.SYSelectOnId(moduleid, userid, devid, devnum, macAddr);

	}
	//更换设备
	@Override
	public Integer SYChangeDev(SYUpdateDevVaule sYUpdateDevVaule) {
		SYDevList list = sYMapper.SYChangeDevMac(sYUpdateDevVaule.getMac());
		if (list != null  && sYUpdateDevVaule.getDevid()!=list.getDevid()) {
			return 201;
		}
		return sYMapper.SYChangeDev(sYUpdateDevVaule);
	}
	// 批量修改责任人
	@Override
	public Integer SYchangeDevOwn(SYChangeUser sYChangeUser) {
		
		return sYMapper.SYchangeDevOwn(sYChangeUser);
	}

	@Override
	public AlarmTendecyVo selectSYAlarmForDay(Integer groupid) throws MyException {
		// TODO Auto-generated method stub
		AlarmTendecyVo alarmTendecyVo = new AlarmTendecyVo();
		List<SYAlarmTendency> selectAlarmForDay = sYMapper.selectSYAlarmForDay(groupid);
		Integer alarmCount = 0;
		for (SYAlarmTendency alarmTendency : selectAlarmForDay) {
			alarmCount = alarmTendency.getToHightAlarm() + alarmCount+alarmTendency.getToLowAlarm();
		}
		alarmTendecyVo.setObject(selectAlarmForDay);
		alarmTendecyVo.setAlarmCount(alarmCount);
		return alarmTendecyVo;
	}

	@Override
	public AlarmTendecyVo selectSYAlarmForMonth(Integer groupid) throws MyException {
		// TODO Auto-generated method stub
		AlarmTendecyVo alarmTendecyVo = new AlarmTendecyVo();
		Integer alarmCount = 0;
		List<SYAlarmTendency> selectAlarmForMonth = sYMapper.selectSYAlarmForMonth(groupid);
		for (SYAlarmTendency alarmTendency : selectAlarmForMonth) {
			alarmCount = alarmTendency.getToHightAlarm() + alarmCount+alarmTendency.getToLowAlarm();
		}
		alarmTendecyVo.setObject(selectAlarmForMonth);
		alarmTendecyVo.setAlarmCount(alarmCount);
		return alarmTendecyVo;
	}

	@Override
	public AlarmTendecyVo selectSYAlarmForThreeYear(Integer groupid) throws MyException {
		// TODO Auto-generated method stub
		AlarmTendecyVo alarmTendecyVo=new AlarmTendecyVo();
		Integer alarmcount=0;
		List<SYAlarmTendency> year = sYMapper.selectSYAlarmForThreeYear(groupid);
		for (SYAlarmTendency alarmTendency : year) {
			alarmcount=alarmTendency.getToHightAlarm()+alarmcount+alarmTendency.getToLowAlarm();
		}
		alarmTendecyVo.setAlarmCount(alarmcount);
		alarmTendecyVo.setObject(year);
		return alarmTendecyVo;
	}

	@Override
	public List<AlarmHandleStatistics> selectSYAlarmForWeeken(Integer groupid) throws MyException {
		// TODO Auto-generated method stub
		return sYMapper.selectSYAlarmForWeeken(groupid);
	}

	@Override
	public List<AlarmStateStatistics> selectSYAlarmForState(Integer groupid, Integer TheType) throws MyException {
		// TODO Auto-generated method stub
		return sYMapper.selectSYAlarmForState(groupid, TheType);
	}
	
}
