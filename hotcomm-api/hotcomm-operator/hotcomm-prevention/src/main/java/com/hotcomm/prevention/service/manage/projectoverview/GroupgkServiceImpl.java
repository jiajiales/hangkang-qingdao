package com.hotcomm.prevention.service.manage.projectoverview;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.hotcomm.prevention.bean.mysql.manage.group.AlarmHandleNums;
import com.hotcomm.prevention.bean.mysql.manage.group.AlarmNums;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.AlarmHandleStatistics;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.AlarmStateStatistics;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.AlarmTendecyVo;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.AlarmTendency;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.AlarmingTrendVO;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.Groupgkcount;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.SXDLAlarmingTrendVO;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.SXDYAlarmingTrendVO;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.SYAlarmTendency;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.SYDLAlarmingTrendVO;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.YGAlarmCount;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.YGAlarmPartic;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.YGAlarmingTrend;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.YGAlarmingTrendTest;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.YGDevAlarmHandleByTimeVO;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.alarmcount_class;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.alarmcount_day;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.alarmcount_month;
import com.hotcomm.prevention.db.mysql.manage.projectoverview.GroupgkMapper;
import com.hotcomm.prevention.exception.MyException;

@Transactional
@Service
public class GroupgkServiceImpl implements GroupgkService {
	@Autowired
	private GroupgkMapper groupgkMapper;

	/*
	 * 项目概况
	 */
	@Override
	public Groupgkcount groupgkcount(Integer userid, Integer moduleid) {
		return groupgkMapper.groupgkcount(userid, moduleid);
	}

	@Override
	public List<alarmcount_month> select_alarm_count_month(Integer userid, Integer moduleid, Integer yyyy) {
		return groupgkMapper.select_alarm_count_month(userid, moduleid, yyyy);
	}

	@Override
	public List<alarmcount_day> select_alarm_count_day(Integer userid, Integer moduleid) {
		return groupgkMapper.select_alarm_count_day(userid, moduleid);
	}

	@Override
	public List<alarmcount_class> select_alarm_count_class(Integer userid, Integer moduleid, Integer yyyy) {
		return groupgkMapper.select_alarm_count_class(userid, moduleid, yyyy);
	}

	/*
	 * 可燃气
	 */
	@Override
	public AlarmTendecyVo selectKRQAlarmForDay(Integer groupid) throws MyException {
		// TODO Auto-generated method stub
		AlarmTendecyVo alarmTendecyVo = new AlarmTendecyVo();
		List<AlarmTendency> selectAlarmForDay = groupgkMapper.selectKRQAlarmForDay(groupid);
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
		List<AlarmTendency> selectAlarmForMonth = groupgkMapper.selectKRQAlarmForMonth(groupid);
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
		AlarmTendecyVo alarmTendecyVo = new AlarmTendecyVo();
		Integer alarmcount = 0;
		List<AlarmTendency> year = groupgkMapper.selectKRQAlarmForThreeYear(groupid);
		for (AlarmTendency alarmTendency : year) {
			alarmcount = alarmTendency.getAlarmTime() + alarmcount;
		}
		alarmTendecyVo.setAlarmCount(alarmcount);
		alarmTendecyVo.setObject(year);
		return alarmTendecyVo;
	}

	@Override
	public List<AlarmHandleStatistics> selectKRQAlarmForWeeken(Integer groupid) throws MyException {
		// TODO Auto-generated method stub
		return groupgkMapper.selectKRQAlarmForWeeken(groupid);
	}

	@Override
	public List<AlarmStateStatistics> selectKRQAlarmForState(Integer groupid, Integer TheType) throws MyException {
		// TODO Auto-generated method stub
		return groupgkMapper.selectKRQAlarmForState(groupid, TheType);
	}

	/*
	 * 垃圾桶
	 */
	@Override
	public AlarmTendecyVo selectAlarmForDay(Integer groupid) throws MyException {
		// TODO Auto-generated method stub
		AlarmTendecyVo alarmTendecyVo = new AlarmTendecyVo();
		List<AlarmTendency> selectAlarmForDay = groupgkMapper.selectAlarmForDay(groupid);
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
		List<AlarmTendency> selectAlarmForMonth = groupgkMapper.selectAlarmForMonth(groupid);
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
		AlarmTendecyVo alarmTendecyVo = new AlarmTendecyVo();
		Integer alarmcount = 0;
		List<AlarmTendency> year = groupgkMapper.selectAlarmForThreeYear(groupid);
		for (AlarmTendency alarmTendency : year) {
			alarmcount = alarmTendency.getAlarmTime() + alarmcount;
		}
		alarmTendecyVo.setAlarmCount(alarmcount);
		alarmTendecyVo.setObject(year);
		return alarmTendecyVo;
	}

	@Override
	public List<AlarmHandleStatistics> selectAlarmForWeeken(Integer groupid) throws MyException {
		// TODO Auto-generated method stub
		return groupgkMapper.selectAlarmForWeeken(groupid);
	}

	@Override
	public List<AlarmStateStatistics> selectAlarmForState(Integer groupid, Integer TheType) throws MyException {
		// TODO Auto-generated method stub
		return groupgkMapper.selectAlarmForState(groupid, TheType);
	}

	/*
	 * 三项电流
	 */
	@Override
	public List<SXDLAlarmingTrendVO> AlarmingTrendForSXDL(Integer queryType, Integer userid, Integer groupid) {
		return groupgkMapper.AlarmingTrendForSXDL(queryType, userid, groupid);
	}

	/*
	 * 三项电压
	 */
	@Override
	public List<SXDYAlarmingTrendVO> AlarmingTrendForSXDY(Integer queryType, Integer userid, Integer groupid) {
		return groupgkMapper.AlarmingTrendForSXDY(queryType, userid, groupid);
	}

	/*
	 * 水压
	 */
	// 按天查询
	public AlarmTendecyVo selectSYAlarmForDay(Integer groupid) throws MyException {
		// TODO Auto-generated method stub
		AlarmTendecyVo alarmTendecyVo = new AlarmTendecyVo();
		List<SYAlarmTendency> selectAlarmForDay = groupgkMapper.selectSYAlarmForDay(groupid);
		Integer alarmCount = 0;
		for (SYAlarmTendency alarmTendency : selectAlarmForDay) {
			alarmCount = alarmTendency.getToHightAlarm() + alarmCount + alarmTendency.getToLowAlarm();
		}
		alarmTendecyVo.setObject(selectAlarmForDay);
		alarmTendecyVo.setAlarmCount(alarmCount);
		return alarmTendecyVo;
	}

	// 按月查询
	public AlarmTendecyVo selectSYAlarmForMonth(Integer groupid) throws MyException {
		// TODO Auto-generated method stub
		AlarmTendecyVo alarmTendecyVo = new AlarmTendecyVo();
		Integer alarmCount = 0;
		List<SYAlarmTendency> selectAlarmForMonth = groupgkMapper.selectSYAlarmForMonth(groupid);
		for (SYAlarmTendency alarmTendency : selectAlarmForMonth) {
			alarmCount = alarmTendency.getToHightAlarm() + alarmCount + alarmTendency.getToLowAlarm();
		}
		alarmTendecyVo.setObject(selectAlarmForMonth);
		alarmTendecyVo.setAlarmCount(alarmCount);
		return alarmTendecyVo;
	}

	// 按年查询
	public AlarmTendecyVo selectSYAlarmForThreeYear(Integer groupid) throws MyException {
		// TODO Auto-generated method stub
		AlarmTendecyVo alarmTendecyVo = new AlarmTendecyVo();
		Integer alarmcount = 0;
		List<SYAlarmTendency> year = groupgkMapper.selectSYAlarmForThreeYear(groupid);
		for (SYAlarmTendency alarmTendency : year) {
			alarmcount = alarmTendency.getToHightAlarm() + alarmcount + alarmTendency.getToLowAlarm();
		}
		alarmTendecyVo.setAlarmCount(alarmcount);
		alarmTendecyVo.setObject(year);
		return alarmTendecyVo;
	}

	public List<AlarmHandleStatistics> selectSYAlarmForWeeken(Integer groupid) throws MyException {
		// TODO Auto-generated method stub
		return groupgkMapper.selectSYAlarmForWeeken(groupid);
	}

	public List<AlarmStateStatistics> selectSYAlarmForState(Integer groupid, Integer TheType) throws MyException {
		// TODO Auto-generated method stub
		return groupgkMapper.selectSYAlarmForState(groupid, TheType);
	}

	/*
	 * 剩余电流
	 */
	@Override
	public List<SYDLAlarmingTrendVO> AlarmingTrendForSYDL(Integer queryType, Integer userid, Integer groupid) {
		return groupgkMapper.AlarmingTrendForSYDL(queryType, userid, groupid);
	}

	/*
	 * 烟感
	 */
	@Override
	public Page<YGAlarmPartic> selectForState(Integer handlestate, Integer stateid) throws MyException {
		// TODO Auto-generated method stub
		Page<YGAlarmPartic> list = groupgkMapper.selectForState(handlestate, stateid);
		return list;
	}

	@Override
	public Page<YGAlarmPartic> selectForTime(String startTime, String endTime, String message) throws MyException {
		if (startTime != null && startTime.equals("")) {
			startTime = null;
		}
		if (endTime != null && endTime.equals("")) {
			endTime = null;
		}
		Page<YGAlarmPartic> list = groupgkMapper.selectForTime(startTime, endTime, message);
		return list;
	}

	@Override
	public Integer closeYGAlarm(Integer alarmid) throws MyException {
		// TODO Auto-generated method stub
		return groupgkMapper.closeYGalarmById(alarmid);
	}

	@Override
	public List<YGAlarmingTrendTest> smokeAlarmingTrendThreeYear(Integer userid, Integer moduleid, Integer queryType,
			Integer groupid) throws MyException {
		List<YGAlarmingTrend> test = groupgkMapper.smokeAlarmingTrend(userid, moduleid, queryType, groupid);
		List<YGAlarmingTrend> test1 = new ArrayList<>();
		List<YGAlarmingTrend> test2 = new ArrayList<>();
		List<YGAlarmingTrend> test3 = new ArrayList<>();
		test1.addAll(test.subList(test.size() - 12, test.size()));
		test2.addAll(test.subList(test.size() - 24, test.size() - 12));
		test3.addAll(test.subList(0, test.size() - 24));
		YGAlarmingTrendTest all = new YGAlarmingTrendTest();
		all.setThisYear(test3);
		all.setLastYear(test2);
		all.setTheYearBefore(test1);
		List<YGAlarmingTrendTest> go = new ArrayList<>();
		go.add(all);
		return go;
	}

	@Override
	public List<YGDevAlarmHandleByTimeVO> YGselectDevAlarmHandleByTime(Integer queryType, String startTime,
			String endTime, Integer userid) throws MyException {
		return groupgkMapper.YGselectDevAlarmHandleByTime(queryType, startTime, endTime, userid);
	}

	@Override
	public List<YGAlarmCount> YGAlarmTypeCount(Integer userid, Integer year) throws MyException {
		return groupgkMapper.YGAlarmTypeCount(userid, year);
	}

	@Override
	public List<YGAlarmingTrend> smokeAlarmingTrendByGroup(Integer userid, Integer moduleid, Integer queryType,
			Integer groupid) throws MyException {
		return groupgkMapper.smokeAlarmingTrendByGroup(userid, moduleid, queryType, groupid);
	}

	@Override
	public List<YGAlarmingTrend> smokeAlarmingTrendDay(Integer userid, Integer moduleid, Integer queryType,
			Integer groupid) throws MyException {
		return groupgkMapper.smokeAlarmingTrend(userid, moduleid, queryType, groupid);
	}

	@Override
	public List<YGAlarmingTrend> smokeAlarmingTrendHalfYear(Integer userid, Integer moduleid, Integer queryType,
			Integer groupid) throws MyException {
		return groupgkMapper.smokeAlarmingTrend(userid, moduleid, queryType, groupid);
	}

	@Override
	public List<YGAlarmingTrend> smokeAlarmingTrendThreeYear2(Integer userid, Integer moduleid, Integer queryType,
			Integer groupid) throws MyException {
		return groupgkMapper.smokeAlarmingTrend(userid, moduleid, queryType, groupid);
	}

	/*
	 * 液位计
	 */
	@Override
	public List<AlarmingTrendVO> AlarmingTrendForWhichHasBoundaryValue(Integer queryType, Integer userid,
			Integer groupid) {
		// TODO Auto-generated method stub
		return groupgkMapper.AlarmingTrendForWhichHasBoundaryValue(queryType, userid, groupid);

	} 
	
	/*
	 * 公共模快,报警处理类型,报警处理率
	 */
	@Override
	public List<AlarmNums> selectAlarmNums(Integer moduleID, Integer userid,Integer queryType) {
		List<AlarmNums> list=groupgkMapper.selectAlarmNums(moduleID,userid,queryType);
		return list;
	}
	
	@Override
	public List<AlarmHandleNums> selectAlarmHandleNums(Integer moduleID, Integer userid,Integer queryType) {
		List<AlarmHandleNums> list=groupgkMapper.selectAlarmHandleNums(moduleID,userid,queryType);
		
		return list;
	}

}
