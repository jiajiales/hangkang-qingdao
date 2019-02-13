package com.hot.manage.service.ljt;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.db.group.IsxistMapper;
import com.hot.manage.db.ljt.LJTDevMapper;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.common.groupgk.AlarmHandleStatistics;
import com.hot.manage.entity.common.groupgk.AlarmStateStatistics;
import com.hot.manage.entity.common.groupgk.AlarmTendecyVo;
import com.hot.manage.entity.common.groupgk.AlarmTendency;
import com.hot.manage.entity.group.Isexist;
import com.hot.manage.entity.ljt.LJTDev;
import com.hot.manage.entity.ljt.LJTDevone;
import com.hot.manage.entity.ljt.LJTDevv;
import com.hot.manage.exception.MyException;
import com.hot.manage.utils.ConverUtil;

@Transactional
@Service
public class LJTDevServiceImpl implements LJTDevService {
	@Autowired
	private LJTDevMapper ljtDevMapper;

	@Autowired
	private IsxistMapper isxistMapper;

	@Override
	public PageInfo<LJTDev> selectdevlist(LJTDevv ljtDevv) {
		PageHelper.startPage(ljtDevv.getPageNum(), ljtDevv.getPageSize());
		Page<LJTDev> page = ljtDevMapper.selectdevlist(ljtDevv);
		List<LJTDev> list = ConverUtil.converPage(page, LJTDev.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	@Override
	public LJTDevone selectdevbyid(Integer devid) {
		return ljtDevMapper.selectdevbyid(devid);
	}

	@Override
	public Integer updatedevmac(Integer devid, String mac) {

		return ljtDevMapper.updatedevmac(devid, mac);
	}

	@Override
	public Integer updatedev(Integer devid, String devnum, String mac, Integer groupid, String code, Double lat,
			Double lng, Integer x, Integer y, Integer mapimgid, Integer own_id, Integer moduleid) {

		ljtDevMapper.updatedev(devid, devnum, mac, groupid, code, lat, lng, x, y, mapimgid, own_id);

		Isexist res = isxistMapper.selectsiterelation(devid, moduleid);
		Integer aa = res.getDgcount();
		if (aa > 0) {
			return ljtDevMapper.updatesiterelation(devid, mapimgid);
		} else {
			return ljtDevMapper.insertsiterelation(devid, mapimgid);
		}
	}

	@Override
	public Integer insertdev(LJTDev ljtDev) {

		if (ljtDev.getMapimgid() != null) {
			ljtDevMapper.insertdev(ljtDev);
			ljtDevMapper.insertgroup(ljtDev);
			return ljtDevMapper.insertimggroup(ljtDev);
		} else {
			ljtDevMapper.insertdev(ljtDev);
			return ljtDevMapper.insertgroup(ljtDev);

		}
	}

	@Override
	public Integer updateownid(String devids, Integer own_id) {
		List<Object> list = new ArrayList<>();
		if (devids != null) {
			String[] split = devids.split(",");
			for (String s : split) {
				list.add(s);
			}
		}
		return ljtDevMapper.updateownid(list, own_id);
	}

	@Override
	public AlarmTendecyVo selectAlarmForDay(Integer groupid) throws MyException {
		// TODO Auto-generated method stub
		AlarmTendecyVo alarmTendecyVo = new AlarmTendecyVo();
		List<AlarmTendency> selectAlarmForDay = ljtDevMapper.selectAlarmForDay(groupid);
		Integer alarmCount = 0;
		for (AlarmTendency alarmTendency : selectAlarmForDay) {
			alarmCount = alarmTendency.getAlarmTime() + alarmCount;
		}
		alarmTendecyVo.setObject(selectAlarmForDay);
		alarmTendecyVo.setAlarmCount(alarmCount);
		return alarmTendecyVo;
	}

	@Override
	public AlarmTendecyVo selectAlarmForMonth(Integer groupid) throws MyException {
		// TODO Auto-generated method stub
		AlarmTendecyVo alarmTendecyVo = new AlarmTendecyVo();
		Integer alarmCount = 0;
		List<AlarmTendency> selectAlarmForMonth = ljtDevMapper.selectAlarmForMonth(groupid);
		for (AlarmTendency alarmTendency : selectAlarmForMonth) {
			alarmCount = alarmTendency.getAlarmTime() + alarmCount;
		}
		alarmTendecyVo.setObject(selectAlarmForMonth);
		alarmTendecyVo.setAlarmCount(alarmCount);
		return alarmTendecyVo;
	}

	@Override
	public AlarmTendecyVo selectAlarmForThreeYear(Integer groupid) throws MyException {
		// TODO Auto-generated method stub
		AlarmTendecyVo alarmTendecyVo=new AlarmTendecyVo();
		Integer alarmcount=0;
		List<AlarmTendency> year = ljtDevMapper.selectAlarmForThreeYear(groupid);
		for (AlarmTendency alarmTendency : year) {
			alarmcount=alarmTendency.getAlarmTime()+alarmcount;
		}
		alarmTendecyVo.setAlarmCount(alarmcount);
		alarmTendecyVo.setObject(year);
		return alarmTendecyVo;
	}


	@Override
	public List<AlarmHandleStatistics> selectAlarmForWeeken(Integer groupid) throws MyException {
		// TODO Auto-generated method stub
		return ljtDevMapper.selectAlarmForWeeken(groupid);
	}

	@Override
	public List<AlarmStateStatistics> selectAlarmForState(Integer groupid,Integer TheType) throws MyException {
		// TODO Auto-generated method stub
		return ljtDevMapper.selectAlarmForState(groupid,TheType);
	}
}
