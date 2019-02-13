package com.hot.manage.service.imp.krq;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.db.krq.LKTKRQDevMapper;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.common.groupgk.AlarmHandleStatistics;
import com.hot.manage.entity.common.groupgk.AlarmStateStatistics;
import com.hot.manage.entity.common.groupgk.AlarmTendecyVo;
import com.hot.manage.entity.common.groupgk.AlarmTendency;
import com.hot.manage.entity.krq.LKTKRQDevList;
import com.hot.manage.entity.krq.LKTKRQSelectOnId;
import com.hot.manage.entity.krq.vaule.KRQChangeUser;
import com.hot.manage.entity.krq.vaule.LKTKRQAddDevVaule;
import com.hot.manage.entity.krq.vaule.LKTKRQDevListVaule;
import com.hot.manage.entity.krq.vaule.LKTKRQUpdateDevVaule;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.krq.LKTKRQDevService;
import com.hot.manage.utils.ConverUtil;

@Transactional
@Service
public class LKTKRQDevServiceImpl implements LKTKRQDevService {

	@Autowired
	private LKTKRQDevMapper lktKRQDevMapper;

	@Override
	public PageInfo<LKTKRQDevList> LKTKRQDevList(LKTKRQDevListVaule lktkrqDevListVaule) throws MyException {
		PageHelper.startPage(lktkrqDevListVaule.getPageNum(), lktkrqDevListVaule.getPageSize());
		Page<LKTKRQDevList> page = lktKRQDevMapper.LKTKRQDevList(lktkrqDevListVaule);
		List<LKTKRQDevList> list = ConverUtil.converPage(page, LKTKRQDevList.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	@Override
	public Integer LKTKRQUpdateDev(LKTKRQUpdateDevVaule lktkrqUpdateDevVaule) throws MyException {
		if (lktkrqUpdateDevVaule.getDevnum() != null) {
			LKTKRQSelectOnId devnum = lktKRQDevMapper.LKTKRQSelectOnId(lktkrqUpdateDevVaule.getModuleid(),
					lktkrqUpdateDevVaule.getUserid(), null, lktkrqUpdateDevVaule.getDevnum(), null);
			if (devnum != null) {
				if (!devnum.getId().equals(lktkrqUpdateDevVaule.getDevid())) {
					return 201;
				}
			}
		}
		Integer code = lktKRQDevMapper.LKTKRQUpdateDev(lktkrqUpdateDevVaule);
		if (code >= 1) {
			code = lktKRQDevMapper.LKTKRQUpdateDevAddPic(lktkrqUpdateDevVaule);
		}
		if (lktkrqUpdateDevVaule.getVideoid() != null) {
			String[] v = lktkrqUpdateDevVaule.getVideoid().split(",");
			// 删除所有关联
			code = lktKRQDevMapper.LKTKRQUpdateDevVideoDel(lktkrqUpdateDevVaule.getModuleid(),
					lktkrqUpdateDevVaule.getDevid());
			for (int i = 0; i < v.length; i++) {
				// 如果还需要该关联，修改回来
				code = lktKRQDevMapper.LKTKRQUpdateDevVideo(lktkrqUpdateDevVaule.getModuleid(),
						lktkrqUpdateDevVaule.getDevid(), Integer.parseInt(v[i]));
				// 如果需要新增的关联不存在，会修改行数为0,code=0
				if (code <= 0) {
					code = lktKRQDevMapper.LKTKRQUpdateDevVideoAdd(lktkrqUpdateDevVaule.getModuleid(),
							lktkrqUpdateDevVaule.getDevid(), Integer.parseInt(v[i]));
				}
			}
		}
		return code;
	}

	@Override
	public Integer LKTKRQDeleteDev(Integer moduleid, Integer devid) throws MyException {
		lktKRQDevMapper.LKTKRQDeleteDevVideo(moduleid, devid);
		lktKRQDevMapper.LKTKRQDeleteDevPic(moduleid, devid);
		return lktKRQDevMapper.LKTKRQDeleteDev(moduleid, devid);
	}

	@Override
	public LKTKRQSelectOnId LKTKRQSelectOnId(Integer moduleid, Integer userid, Integer devid) throws MyException {
		return lktKRQDevMapper.LKTKRQSelectOnId(moduleid, userid, devid, null, null);
	}

	@Override
	public Integer LKTKRQAddDev(LKTKRQAddDevVaule lktkrqAddDevVaule) {
		LKTKRQSelectOnId devnum = lktKRQDevMapper.LKTKRQSelectOnId(lktkrqAddDevVaule.getModuleid(),
				lktkrqAddDevVaule.getUserid(), null, lktkrqAddDevVaule.getDevnum(), null);
		if (devnum != null) {
			return 201;
		}
		LKTKRQSelectOnId macAddr = lktKRQDevMapper.LKTKRQSelectOnId(lktkrqAddDevVaule.getModuleid(),
				lktkrqAddDevVaule.getUserid(), null, null, lktkrqAddDevVaule.getMacAddr());
		if (macAddr != null) {
			return 202;
		}
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		lktkrqAddDevVaule.setAddtime(df.format(day));
		Integer code = lktKRQDevMapper.LKTKRQAddDev(lktkrqAddDevVaule);
		if (code > 0) {
			code = lktKRQDevMapper.LKTKRQAddDevGroup(lktkrqAddDevVaule);
		}
		if (code > 0 & lktkrqAddDevVaule.getItempicid() != null) {
			code = lktKRQDevMapper.LKTKRQAddDevGroupPic(lktkrqAddDevVaule);
		}
		if (lktkrqAddDevVaule.getVideoid() != null) {
			String[] v = lktkrqAddDevVaule.getVideoid().split(",");
			for (int i = 0; i < v.length; i++) {
				code = lktKRQDevMapper.LKTKRQAddDevVideo(lktkrqAddDevVaule.getModuleid(), lktkrqAddDevVaule.getId(),
						Integer.parseInt(v[i]));
			}
		}
		return code;
	}

	@Override
	public Integer LKTKRQChangeDev(LKTKRQUpdateDevVaule lktkrqUpdateDevVaule) {
		LKTKRQDevList list = lktKRQDevMapper.LKTKRQChangeDevMac(lktkrqUpdateDevVaule.getMac());
		if (list != null) {
			return 201;
		}
		return lktKRQDevMapper.LKTKRQChangeDev(lktkrqUpdateDevVaule);
	}

	@Override
	public Integer changeDevOwn(KRQChangeUser kRQChangeUser) {

		return lktKRQDevMapper.changeDevOwn(kRQChangeUser);
	}

	@Override
	public Integer LKTAddSignDevList(Integer moduleid, String devid, Integer patrolid) throws MyException {
		return lktKRQDevMapper.LKTAddSignDevList(moduleid, devid, patrolid);
	}

	@Override
	public AlarmTendecyVo selectKRQAlarmForDay(Integer groupid) throws MyException {
		// TODO Auto-generated method stub
		AlarmTendecyVo alarmTendecyVo = new AlarmTendecyVo();
		List<AlarmTendency> selectAlarmForDay = lktKRQDevMapper.selectKRQAlarmForDay(groupid);
		Integer alarmCount = 0;
		for (AlarmTendency alarmTendency : selectAlarmForDay) {
			alarmCount = alarmTendency.getAlarmTime() + alarmCount;
		}
		alarmTendecyVo.setObject(selectAlarmForDay);
		alarmTendecyVo.setAlarmCount(alarmCount);
		return alarmTendecyVo;
	}

	@Override
	public AlarmTendecyVo selectKRQAlarmForMonth(Integer groupid) throws MyException {
		// TODO Auto-generated method stub
		AlarmTendecyVo alarmTendecyVo = new AlarmTendecyVo();
		Integer alarmCount = 0;
		List<AlarmTendency> selectAlarmForMonth = lktKRQDevMapper.selectKRQAlarmForMonth(groupid);
		for (AlarmTendency alarmTendency : selectAlarmForMonth) {
			alarmCount = alarmTendency.getAlarmTime() + alarmCount;
		}
		alarmTendecyVo.setObject(selectAlarmForMonth);
		alarmTendecyVo.setAlarmCount(alarmCount);
		return alarmTendecyVo;
	}

	@Override
	public AlarmTendecyVo selectKRQAlarmForThreeYear(Integer groupid) throws MyException {
		// TODO Auto-generated method stub
		AlarmTendecyVo alarmTendecyVo=new AlarmTendecyVo();
		Integer alarmcount=0;
		List<AlarmTendency> year = lktKRQDevMapper.selectKRQAlarmForThreeYear(groupid);
		for (AlarmTendency alarmTendency : year) {
			alarmcount=alarmTendency.getAlarmTime()+alarmcount;
		}
		alarmTendecyVo.setAlarmCount(alarmcount);
		alarmTendecyVo.setObject(year);
		return alarmTendecyVo;
	}

	@Override
	public List<AlarmHandleStatistics> selectKRQAlarmForWeeken(Integer groupid) throws MyException {
		// TODO Auto-generated method stub
		return lktKRQDevMapper.selectKRQAlarmForWeeken(groupid);
	}

	@Override
	public List<AlarmStateStatistics> selectKRQAlarmForState(Integer groupid, Integer TheType) throws MyException {
		// TODO Auto-generated method stub
		return lktKRQDevMapper.selectKRQAlarmForState(groupid, TheType);
	}

}
