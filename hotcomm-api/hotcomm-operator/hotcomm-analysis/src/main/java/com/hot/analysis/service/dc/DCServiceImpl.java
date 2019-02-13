package com.hot.analysis.service.dc;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hot.analysis.bean.dc.DevStateNum;
import com.hot.analysis.db.dc.DCMapper;
import com.hot.analysis.bean.dc.DevUseRate;
import com.hot.analysis.bean.common.TGroup;
import com.hot.analysis.bean.dc.DCParkingSlotsData;
import com.hot.analysis.bean.dc.DCDevInfo;
import com.hot.analysis.bean.dc.DCIncome;

@Transactional
@Service
public class DCServiceImpl implements DCService {

	@Autowired
	private DCMapper dcmapper;

	// 终端设备数量、停车数量、空闲数量、故障设备数量/设备状态图
	@Override
	public DevStateNum DevStateNum(Integer userid) {
		return dcmapper.DevStateNum(userid);
	}

	// 车位利用率
	@Override
	public List<DevUseRate> DevUseRate(Integer querytype) {
		return dcmapper.DevUseRate(querytype);
	}

	// 停车时段分布
	@Override
	public List<DCParkingSlotsData> DCParkingSlotsData(Integer userid) {
		return dcmapper.DCParkingSlotsData(userid);
	}

	@Override
	public List<TGroup> selectGroList(Integer userid, Integer moduleid) {
		return dcmapper.selectGroList(userid, moduleid);
	}

	// 收入统计
	@Override
	public List<DCIncome> DCIncome(Integer userid) {
		return dcmapper.DCIncome(userid);
	}
	
	@Override
	public List<Integer> selectSixMonthPakingCount(String beforeSixMonth,Integer userid,Integer devid) {
		return dcmapper.selectSixMonthPakingCount(beforeSixMonth,userid,devid);
	}

	@Override
	public Integer selectSixMonthDevCount(String thatMonth,Integer userid) {
		return dcmapper.selectSixMonthDevCount(thatMonth,userid);
	}

	@Override
	public List<Integer> selectSixMonthPakingCountMoney(String beforeSixMonth, Integer userid, Integer devid) {
		return dcmapper.selectSixMonthPakingCountMoney(beforeSixMonth, userid, devid);
	}

	@Override
	public DCDevInfo devPackingTimesAndRank(Integer userid, Integer devid) {
		return dcmapper.devPackingTimesAndRank(userid, devid);
	}

	@Override
	public DCDevInfo devPackingMoneyAndRank(Integer userid, Integer devid) {
		return dcmapper.devPackingMoneyAndRank(userid, devid);
	}

	@Override
	public DCDevInfo queryDev(Integer userid, Integer devid) {
		return dcmapper.queryDev(userid, devid);
	}

	@Override
	public DCDevInfo selectSummary(Integer userid, Integer devid) throws ParseException {
		DCDevInfo dCDevInfo=new DCDevInfo();
		List<String> sixMonthAgo=sixMonthAgo();
		String sixMonthAgoStr=sixMonthAgo.get(0);
		sixMonthAgoStr+="-28";
		List<String> monthAverage=new ArrayList<String>();
		List<Integer> devMonthTimes=dcmapper.selectSixMonthPakingCount(sixMonthAgoStr, userid, devid);
		List<Integer> monthAllTimes=dcmapper.selectSixMonthPakingCount(sixMonthAgoStr, userid, 0);
		
		List<String> allDevSixMonthaverageIncome=new ArrayList<String>();
		List<Integer> devMonthMoney=dcmapper.selectSixMonthPakingCountMoney(sixMonthAgoStr, userid, devid);
		List<Integer> monthAllMoney=dcmapper.selectSixMonthPakingCountMoney(sixMonthAgoStr, userid, 0);
		for (int i = 0; i < 6; i++) {
			String thatMonthStr=sixMonthAgo.get(i);
			thatMonthStr+="-28";
			DecimalFormat df=new DecimalFormat("0.000");
			String test = df.format((float)monthAllTimes.get(i)/dcmapper.selectSixMonthDevCount(thatMonthStr, userid));
			monthAverage.add(i, test);
			
			String test2 = df.format((float)monthAllMoney.get(i)/dcmapper.selectSixMonthDevCount(thatMonthStr, userid));
			allDevSixMonthaverageIncome.add(i, test2);
		}
		dCDevInfo.setAllDevSixMonthaverageTimes(monthAverage);
		dCDevInfo.setDevSixMonthTimes(devMonthTimes);
		
		dCDevInfo.setAllDevSixMonthaverageIncome(allDevSixMonthaverageIncome);
		dCDevInfo.setDevSixMonthIncome(devMonthMoney);
		
		DCDevInfo dCDevInfo2=dcmapper.queryDev(userid, devid);
		dCDevInfo.setModuleid(dCDevInfo2.getModuleid());
		dCDevInfo.setDevnum(dCDevInfo2.getDevnum());
		dCDevInfo.setDAY(dCDevInfo2.getDAY());
		dCDevInfo.setADDTIME(dCDevInfo2.getADDTIME());
		
		DCDevInfo dCDevInfo3=dcmapper.devPackingTimesAndRank(userid, devid);
		if (dCDevInfo3!=null) {
			dCDevInfo.setDevid(dCDevInfo3.getDevid());
			dCDevInfo.setParkingCount(dCDevInfo3.getParkingCount());
			dCDevInfo.setRownum(dCDevInfo3.getRownum());
		}
		
		DCDevInfo dCDevInfo4=dcmapper.devPackingMoneyAndRank(userid, devid);
		if (dCDevInfo4!=null) {
			dCDevInfo.setParkingMoneyCount(dCDevInfo4.getParkingMoneyCount());
			dCDevInfo.setMoneyRownum(dCDevInfo4.getMoneyRownum());
		}
		
		return dCDevInfo; 
	}
	
	public List<String> sixMonthAgo() throws ParseException {  
	    Calendar c = Calendar.getInstance();  
	    c.add(Calendar.MONTH, -4);  
	    String before_six=c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH);//六个月前  
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

}
