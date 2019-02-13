package com.hotcomm.prevention.service.datashow.firesafety;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotcomm.prevention.bean.mysql.datashow.vo.AlarmIndexVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.AlarmTrendVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.DevInformVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.GroupDataVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.GroupForMapVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.OperationalDataOverviewVo;
import com.hotcomm.prevention.db.mysql.datashow.firesafety.HomeDataMapper;
import com.hotcomm.prevention.exception.MyException;

@Service
public class HomeDataServerImpl implements HomeDataServer {

	@Autowired
	private HomeDataMapper homeDataMapper;

	@Override
	@Transactional
	public GroupDataVo selectGroupData(Integer userid, String moduleid) throws MyException {
		// TODO Auto-generated method stub
		GroupDataVo vo = homeDataMapper.selectGroupData(userid, moduleid);
		if (vo == null) {
			return null;
		}
		return vo;
	}

	@Override
	@Transactional
	public List<GroupForMapVo> selectGroupForMap(Integer userid, String moduleid) throws MyException {
		// TODO Auto-generated method stub
		List<GroupForMapVo> vo = homeDataMapper.selectGroupForMap(userid, moduleid);
		if (vo.size() == 0) {
			return null;
		}
		return homeDataMapper.selectGroupForMap(userid, moduleid);
	}

	@Override
	@Transactional
	public List<DevInformVo> selectDevInform(String moduleid) throws MyException {
		// TODO Auto-generated method stub
		if (homeDataMapper.selectDevInform(moduleid).size() == 0 || homeDataMapper.selectDevInform(moduleid) == null) {
			return null;
		} else {
			return homeDataMapper.selectDevInform(moduleid);
		}
	}

	@Override
	@Transactional
	public OperationalDataOverviewVo selectOperationalDataOverview() throws MyException {
		// TODO Auto-generated method stub
		OperationalDataOverviewVo vo = homeDataMapper.selectOperationalDataOverview();
		if (vo == null) {
			return null;
		}
		vo.setAvetimecount(homeDataMapper.getAveHandleTime());
		return vo;
	}

	@Override
	@Transactional
	public List<AlarmTrendVo> selectAlarmTrend(Integer type, String moduleid) throws MyException {
		// TODO Auto-generated method stub
		List<AlarmTrendVo> alarmTrend = null;
		if (type == 1) {
			alarmTrend = homeDataMapper.selectAlarmTrendByDay(moduleid);
		} else if (type == 2) {
			alarmTrend = homeDataMapper.selectAlarmTrendByYear(moduleid);
		}
		if (alarmTrend.size() == 0) {
			return null;
		}
		return alarmTrend;
	}

	@Override
	public AlarmIndexVo selectAalarmIndex() throws MyException {
		// TODO Auto-generated method stub
		AlarmIndexVo vo = homeDataMapper.selectAalarmIndex();
		if (vo == null) {
			return null;
		}
		vo.setAvetimecount(homeDataMapper.getAveHandleTime());
		return vo;
	}

}
