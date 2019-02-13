package com.hot.manage.service.imp.yg;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.hot.manage.db.yg.YGAlarmParticMapper;
import com.hot.manage.entity.yg.vo.YGAlarmCount;
import com.hot.manage.entity.yg.vo.YGAlarmPartic;
import com.hot.manage.entity.yg.vo.YGAlarmingTrend;
import com.hot.manage.entity.yg.vo.YGAlarmingTrendTest;
import com.hot.manage.entity.yg.vo.YGDevAlarmHandleByTimeVO;
import com.hot.manage.entity.yg.vo.YGHistoricalDate;
import com.hot.manage.entity.yg.vo.YGHistoricalStateDate;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.yg.YGAlarmParticService;

@Service
@Transactional
public class YGAlarmParticServiceImpl implements YGAlarmParticService {

	@Autowired
	private YGAlarmParticMapper ygalarmParticMappper;

	@Override
	public Page<YGAlarmPartic> selectForState(Integer handlestate, Integer stateid) throws MyException {
		// TODO Auto-generated method stub
		Page<YGAlarmPartic> list = ygalarmParticMappper.selectForState(handlestate, stateid);
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
		Page<YGAlarmPartic> list = ygalarmParticMappper.selectForTime(startTime, endTime, message);
		return list;
	}

	@Override
	public List<YGHistoricalDate> selectHistoricalDate(Integer deviceid, Integer theyear) throws MyException {
		// TODO Auto-generated method stub
		return ygalarmParticMappper.selectHistoricalDate(deviceid, theyear);
	}

	@Override
	public List<YGHistoricalStateDate> selectHistoricalStateDate(Integer deviceid, Integer theyear) throws MyException {
		// TODO Auto-generated method stub
		return ygalarmParticMappper.selectHistoricalStateDate(deviceid, theyear);
	}

	@Override
	public Integer closeYGAlarm(Integer alarmid) throws MyException {
		// TODO Auto-generated method stub
		return ygalarmParticMappper.closeYGalarmById(alarmid);
	}

	@Override
	public List<YGAlarmingTrendTest> smokeAlarmingTrendThreeYear(Integer userid, Integer moduleid,Integer queryType,Integer groupid) throws MyException {
		List<YGAlarmingTrend> test = ygalarmParticMappper.smokeAlarmingTrend(userid, moduleid, queryType,groupid);
		List<YGAlarmingTrend> test1 = new ArrayList<>();
		List<YGAlarmingTrend> test2 = new ArrayList<>();
		List<YGAlarmingTrend> test3 = new ArrayList<>();
		test1.addAll(test.subList(test.size()-12, test.size()));
		test2.addAll(test.subList(test.size()-24, test.size()-12));
		test3.addAll(test.subList(0, test.size()-24));
		YGAlarmingTrendTest all=new YGAlarmingTrendTest();
		all.setThisYear(test3);
		all.setLastYear(test2);
		all.setTheYearBefore(test1);
		List<YGAlarmingTrendTest> go= new ArrayList<>();
		go.add(all);
		return go;
	}

	@Override
	public List<YGDevAlarmHandleByTimeVO> YGselectDevAlarmHandleByTime(Integer queryType, String startTime,
			String endTime,Integer userid) throws MyException {
		return ygalarmParticMappper.YGselectDevAlarmHandleByTime(queryType, startTime, endTime,userid);
	}

	@Override
	public List<YGAlarmCount> YGAlarmTypeCount(Integer userid, Integer year) throws MyException {
		return ygalarmParticMappper.YGAlarmTypeCount(userid, year);
	}

	@Override
	public List<YGAlarmingTrend> smokeAlarmingTrendByGroup(Integer userid, Integer moduleid, Integer queryType,
			Integer groupid) throws MyException {
		return ygalarmParticMappper.smokeAlarmingTrendByGroup(userid, moduleid, queryType, groupid);
	}

	@Override
	public List<YGAlarmingTrend> smokeAlarmingTrendDay(Integer userid, Integer moduleid, Integer queryType,Integer groupid)
			throws MyException {
		return ygalarmParticMappper.smokeAlarmingTrend(userid, moduleid, queryType,groupid);
	}

	@Override
	public List<YGAlarmingTrend> smokeAlarmingTrendHalfYear(Integer userid, Integer moduleid, Integer queryType,Integer groupid)
			throws MyException {
		return ygalarmParticMappper.smokeAlarmingTrend(userid, moduleid, queryType,groupid);
	}

	@Override
	public List<YGAlarmingTrend> smokeAlarmingTrendThreeYear2(Integer userid, Integer moduleid, Integer queryType,
			Integer groupid) throws MyException {
		return ygalarmParticMappper.smokeAlarmingTrend(userid, moduleid, queryType,groupid);
	}

}
