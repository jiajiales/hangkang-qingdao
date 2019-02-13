package com.hotcomm.prevention.service.manage.home;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hotcomm.prevention.bean.mysql.manage.home.HomeAlarmTrend;
import com.hotcomm.prevention.bean.mysql.manage.home.HomeDataForDay;
import com.hotcomm.prevention.bean.mysql.manage.home.HomeTotalData;
import com.hotcomm.prevention.bean.mysql.manage.home.HomeWoTrend;
import com.hotcomm.prevention.db.mysql.manage.home.HomeMapper;
import com.hotcomm.prevention.exception.MyException;

@Service
public class HomeServiceImpl implements HomeService {

	@Autowired
	HomeMapper homeMapper;

	/**
	 * 首页，设备、报警、工单、巡检数据统计
	 */
	@Override
	public HomeTotalData selectTotalData(Integer userid, Integer module) throws MyException {
		/*
		 * homeMapper.dropTemporaryTable(); // 删除临时表
		 * homeMapper.createTemporaryTable(); // 创建临时表
		 */
		return homeMapper.selectTotalData(userid, module);
	}

	/**
	 * 设备报警趋势，近7天，近1个月，报警数据
	 */
	@Override
	public List<HomeAlarmTrend> selectAlarmTrend(Integer userid, Integer type, Integer module) throws MyException {
		List<HomeAlarmTrend> list = homeMapper.selectAlarmTrend(userid, type, module);
		return list;
	}

	/**
	 * 首页，工单处理趋势分析
	 */
	@Override
	public List<HomeWoTrend> selectWoTrend(Integer userid, Integer type, Integer module) throws MyException {
		return homeMapper.selectWoTrend(userid, type, module);
	}

	/**
	 * 查询当天报警、工单统计
	 */
	@Override
	public List<HomeDataForDay> selectDataForDay(Integer userid, Integer module) throws MyException {
		return homeMapper.selectDataForDay(userid, module);
	}

}
