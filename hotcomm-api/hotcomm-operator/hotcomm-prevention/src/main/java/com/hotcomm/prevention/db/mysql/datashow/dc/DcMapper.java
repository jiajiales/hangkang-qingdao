package com.hotcomm.prevention.db.mysql.datashow.dc;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hotcomm.prevention.bean.mysql.datashow.dc.DCDevInfo;
import com.hotcomm.prevention.bean.mysql.datashow.dc.DCParkingSlotsData;
import com.hotcomm.prevention.bean.mysql.datashow.dc.DcCountState;
import com.hotcomm.prevention.bean.mysql.datashow.dc.DevStateNum;
import com.hotcomm.prevention.bean.mysql.datashow.dc.DevUseRate;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.T_device_all;

import tk.mybatis.mapper.common.Mapper;

import com.hotcomm.prevention.bean.mysql.datashow.dc.DcIncome;

public interface DcMapper extends Mapper<T_device_all>{

	DevStateNum DevStateNum(@Param("userid") Integer userid);

	List<DevUseRate> DevUseRate(@Param("userid") Integer userid, @Param("querytype") Integer querytype);

	List<DCParkingSlotsData> DCParkingSlotsData(@Param("userid") Integer userid);

	List<DcIncome> DcIncome(@Param("userid") Integer userid);

	List<Integer> selectSixMonthPakingCount(@Param("beforeSixMonth") String beforeSixMonth,
			@Param("userid") Integer userid, @Param("devid") Integer devid);

	Integer selectSixMonthDevCount(@Param("thatMonth") String thatMonth, @Param("userid") Integer userid);

	List<Integer> selectSixMonthPakingCountMoney(@Param("beforeSixMonth") String beforeSixMonth,
			@Param("userid") Integer userid, @Param("devid") Integer devid);

	DCDevInfo devPackingTimesAndRank(@Param("userid") Integer userid, @Param("devid") Integer devid);

	DCDevInfo devPackingMoneyAndRank(@Param("userid") Integer userid, @Param("devid") Integer devid);

	DCDevInfo queryDev(@Param("userid") Integer userid, @Param("devid") Integer devid);
	
	List<DcCountState> selectCountState(@Param("userid") Integer userid);
	
	List<T_device_all> selectDeviceList(@Param("userid") Integer userid);

}
