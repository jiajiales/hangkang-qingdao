package com.hotcomm.prevention.service.datashow.dc;

import java.text.ParseException;
import java.util.List;

import com.hotcomm.prevention.bean.mysql.datashow.dc.DCDevInfo;
import com.hotcomm.prevention.bean.mysql.datashow.dc.DCParkingSlotsData;
import com.hotcomm.prevention.bean.mysql.datashow.dc.DcCountState;
import com.hotcomm.prevention.bean.mysql.datashow.dc.DcIncome;
import com.hotcomm.prevention.bean.mysql.datashow.dc.DevStateNum;
import com.hotcomm.prevention.bean.mysql.datashow.dc.DevUseRate;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.T_device_all;

public interface DcService {

	DevStateNum DevStateNum(Integer userid);

	List<DevUseRate> DevUseRate(Integer querytype,Integer userid);
	
	List<DCParkingSlotsData> DCParkingSlotsData(Integer userid);

	List<DcIncome> DcIncome(Integer userid);
	
	DCDevInfo selectSummary(Integer userid,Integer devid) throws ParseException;
	
	List<DcCountState> selectCountState(Integer userid);
	
	List<T_device_all> selectDevice(Integer userid);
	
}
