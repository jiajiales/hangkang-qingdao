package com.hot.manage.service.common.home;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hot.manage.db.common.HomeMapper;
import com.hot.manage.entity.common.home.HomeAlarmTrend;
import com.hot.manage.entity.common.home.HomeDataForDay;
import com.hot.manage.entity.common.home.HomeTotalData;
import com.hot.manage.entity.common.home.HomeWoTrend;
import com.hot.manage.exception.MyException;

@Service
public class HomeServiceImpl implements HomeService {
	
	@Autowired
	HomeMapper homeMapper;

	/**
	 * 首页，设备、报警、工单、巡检数据统计
	 */
	@Override
	public HomeTotalData selectTotalData(Integer userid) throws MyException {
		return homeMapper.selectTotalData(userid);
	}
	
	/**
	 * 设备报警趋势，近7天，近1个月，报警数据
	 */
	@Override
	public List<HomeAlarmTrend> selectAlarmTrend(Integer userid, Integer type) throws MyException {
		//Map<Integer, List<HomeAlarmTrend>> map = new HashMap<>();
		List<HomeAlarmTrend> list = homeMapper.selectAlarmTrend(userid, type);
	/*	List<Integer> arrayList = new ArrayList<>();
		if (list.size() != 0 || list != null) {
			for (HomeAlarmTrend trend : list) {
				if (!arrayList.contains(trend.getModuleid())) {
					arrayList.add(trend.getModuleid());
				}
			}
			for (Integer array : arrayList) {
				List<HomeAlarmTrend> ss = new ArrayList<>();
				for (HomeAlarmTrend homeAlarmTrend : list) {
					if (array == homeAlarmTrend.getModuleid()) {
						ss.add(homeAlarmTrend);
					}

				}
				map.put(array, ss);
			}
		}*/
		return list;
	}
	
	/**
	 * 首页，工单处理趋势分析
	 */
	@Override
	public List<HomeWoTrend> selectWoTrend(Integer userid, Integer type) throws MyException {
		return homeMapper.selectWoTrend(userid, type);
	}
	
	/**
	 * 查询当天报警、工单统计
	 */
	@Override
	public List<HomeDataForDay> selectDataForDay(Integer userid) throws MyException {
		return homeMapper.selectDataForDay(userid);
	}

}
