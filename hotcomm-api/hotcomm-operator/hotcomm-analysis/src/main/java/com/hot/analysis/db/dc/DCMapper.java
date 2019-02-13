package com.hot.analysis.db.dc;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hot.analysis.bean.common.TGroup;
import com.hot.analysis.bean.dc.DCParkingSlotsData;
import com.hot.analysis.bean.dc.DevStateNum;
import com.hot.analysis.bean.dc.DevUseRate;
import com.hot.analysis.bean.dc.DCDevInfo;
import com.hot.analysis.bean.dc.DCIncome;

public interface DCMapper {

	DevStateNum DevStateNum(@Param("userid") Integer userid);
	
	List<DevUseRate> DevUseRate(@Param("querytype") Integer querytype);
	
	List<DCParkingSlotsData> DCParkingSlotsData(@Param("userid") Integer userid);

	List<TGroup> selectGroList(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid);
	
	List<DCIncome> DCIncome(@Param("userid") Integer userid);
	
	List<Integer> selectSixMonthPakingCount(@Param("beforeSixMonth") String beforeSixMonth,
			@Param("userid") Integer userid, @Param("devid") Integer devid);

	Integer selectSixMonthDevCount(@Param("thatMonth") String thatMonth, @Param("userid") Integer userid);
	
	List<Integer> selectSixMonthPakingCountMoney(@Param("beforeSixMonth") String beforeSixMonth,
			@Param("userid") Integer userid, @Param("devid") Integer devid);
	
	DCDevInfo devPackingTimesAndRank(@Param("userid") Integer userid,@Param("devid") Integer devid);
	
	DCDevInfo devPackingMoneyAndRank(@Param("userid") Integer userid,@Param("devid") Integer devid);
	
	DCDevInfo queryDev(@Param("userid") Integer userid,@Param("devid") Integer devid);
}
