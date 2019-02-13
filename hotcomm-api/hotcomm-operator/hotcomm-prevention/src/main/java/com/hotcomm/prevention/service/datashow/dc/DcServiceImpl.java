package com.hotcomm.prevention.service.datashow.dc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotcomm.prevention.bean.mysql.datashow.dc.DCDevInfo;
import com.hotcomm.prevention.bean.mysql.datashow.dc.DcCountState;
import com.hotcomm.prevention.bean.mysql.datashow.dc.DevUseRate;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.T_device_all;
import com.hotcomm.prevention.db.mysql.datashow.dc.DcMapper;

@Service
public class DcServiceImpl implements DcService {

	@Autowired
	private DcMapper dcMapper;

	@Override
	public com.hotcomm.prevention.bean.mysql.datashow.dc.DevStateNum DevStateNum(Integer userid) {
		return dcMapper.DevStateNum(userid);
	}

	@Override
	public List<DevUseRate> DevUseRate(Integer querytype, Integer userid) {
		return dcMapper.DevUseRate(userid, querytype);
	}

	@Override
	public List<com.hotcomm.prevention.bean.mysql.datashow.dc.DCParkingSlotsData> DCParkingSlotsData(Integer userid) {
		return dcMapper.DCParkingSlotsData(userid);
	}

	@Override
	public List<com.hotcomm.prevention.bean.mysql.datashow.dc.DcIncome> DcIncome(Integer userid) {
		return dcMapper.DcIncome(userid);
	}

	@Override
	public DCDevInfo selectSummary(Integer userid, Integer devid) throws ParseException {
		DCDevInfo dCDevInfo = new DCDevInfo();
		List<String> sixMonthAgo = sixMonthAgo();
		String sixMonthAgoStr = sixMonthAgo.get(0);
		sixMonthAgoStr += "-28";
		List<String> monthAverage = new ArrayList<String>();
		//该设备6个月，每个月停车次数
		List<Integer> devMonthTimes = dcMapper.selectSixMonthPakingCount(sixMonthAgoStr, userid, devid);
		//所有设备，6个月，每个月停车次数
		List<Integer> monthAllTimes = dcMapper.selectSixMonthPakingCount(sixMonthAgoStr, userid, 0);

		List<String> allDevSixMonthaverageIncome = new ArrayList<String>();
		//该设备，6个月，每个月的收入
		List<Integer> devMonthMoney = dcMapper.selectSixMonthPakingCountMoney(sixMonthAgoStr, userid, devid);
		//所有设备，6个月，每月收入
		List<Integer> monthAllMoney = dcMapper.selectSixMonthPakingCountMoney(sixMonthAgoStr, userid, 0);
		for (int i = 0; i < 6; i++) {
			String thatMonthStr = sixMonthAgo.get(i);
			//拼接字符串
			thatMonthStr += "-28";
//			DecimalFormat df = new DecimalFormat("0.000");
			//指定月份的设备数量
			int count=dcMapper.selectSixMonthDevCount(thatMonthStr, userid);
			float test;//停车次数
			float test2;//平均收入
			if (count==0) {
				test = 0;
				test2 = 0;
			}else {
				test = (monthAllTimes.get(i) /count);//指定月份，设备平均停车次数
				test2 = (monthAllMoney.get(i)/count );//指定月份，设备平均收入
			}
			monthAverage.add(i, String.valueOf(test));
			allDevSixMonthaverageIncome.add(i, String.valueOf(test2));
		}
		dCDevInfo.setAllDevSixMonthaverageTimes(monthAverage);
		dCDevInfo.setDevSixMonthTimes(devMonthTimes);

		dCDevInfo.setAllDevSixMonthaverageIncome(allDevSixMonthaverageIncome);
		dCDevInfo.setDevSixMonthIncome(devMonthMoney);

		DCDevInfo dCDevInfo2 = dcMapper.queryDev(userid, devid);
		dCDevInfo.setModuleid(1);
		dCDevInfo.setDevnum(dCDevInfo2.getDevnum());
		dCDevInfo.setDAY(dCDevInfo2.getDAY());
		dCDevInfo.setADDTIME(dCDevInfo2.getADDTIME());

		DCDevInfo dCDevInfo3 = dcMapper.devPackingTimesAndRank(userid, devid);
		if (dCDevInfo3 != null) {
			dCDevInfo.setDevid(dCDevInfo3.getDevid());
			dCDevInfo.setParkingCount(dCDevInfo3.getParkingCount());
			dCDevInfo.setRownum(dCDevInfo3.getRownum());
		}

		DCDevInfo dCDevInfo4 = dcMapper.devPackingMoneyAndRank(userid, devid);
		if (dCDevInfo4 != null) {
			dCDevInfo.setParkingMoneyCount(dCDevInfo4.getParkingMoneyCount());
			dCDevInfo.setMoneyRownum(dCDevInfo4.getMoneyRownum());
		}

		return dCDevInfo;
	}

	public List<String> sixMonthAgo() throws ParseException {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -4);
		String before_six = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH);// 六个月前
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月
		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();
		min.setTime(sdf.parse(before_six));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
		max.setTime(sdf.parse(sdf.format(new Date())));
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
		Calendar curr = min;
		while (curr.before(max)) {
			result.add(sdf.format(curr.getTime()));
			curr.add(Calendar.MONTH, 1);
		}
		return result;
	}
	
	@Override
	public List<DcCountState> selectCountState(Integer userid) {
		return dcMapper.selectCountState(userid);
	}

	@Override
	public List<T_device_all> selectDevice(Integer userid) {
		return dcMapper.selectDeviceList(userid);
	}

	

}
