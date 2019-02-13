package com.hot.analysis.service.dc;

import java.text.ParseException;
import java.util.List;
import com.hot.analysis.bean.common.TGroup;
import com.hot.analysis.bean.dc.DCDevInfo;
import com.hot.analysis.bean.dc.DCIncome;
import com.hot.analysis.bean.dc.DCParkingSlotsData;
import com.hot.analysis.bean.dc.DevStateNum;
import com.hot.analysis.bean.dc.DevUseRate;

public interface DCService {
	DevStateNum DevStateNum(Integer userid);

	List<DevUseRate> DevUseRate(Integer querytype);
	
	List<DCParkingSlotsData> DCParkingSlotsData(Integer userid);

	List<TGroup> selectGroList(Integer userid, Integer moduleid);
	
	List<DCIncome> DCIncome(Integer userid);
	
	List<Integer> selectSixMonthPakingCount(String beforeSixMonth, Integer userid, Integer devid);

	Integer selectSixMonthDevCount(String thatMonth, Integer userid);

	List<Integer> selectSixMonthPakingCountMoney(String beforeSixMonth, Integer userid, Integer devid);
	
	DCDevInfo devPackingTimesAndRank(Integer userid,Integer devid);
	
	DCDevInfo devPackingMoneyAndRank(Integer userid,Integer devid);
	
	DCDevInfo queryDev(Integer userid,Integer devid);
	
	DCDevInfo selectSummary(Integer userid,Integer devid) throws ParseException;
}
