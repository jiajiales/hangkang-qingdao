package com.hot.analysis.controller.dc;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hot.analysis.bean.common.TGroup;
import com.hot.analysis.bean.dc.DCDevInfo;
import com.hot.analysis.bean.dc.DCIncome;
import com.hot.analysis.bean.dc.DCParkingSlotsData;
import com.hot.analysis.bean.dc.DevStateNum;
import com.hot.analysis.bean.dc.DevUseRate;
import com.hot.analysis.exception.MyException;
import com.hot.analysis.service.dc.DCService;

@RestController
public class DCController {

	@Autowired
	private DCService dcService;

	// 终端设备数量、停车数量、空闲数量、故障设备数量/设备状态图
	@PostMapping("/dc/DevStateNum")
	public DevStateNum DevStateNum(Integer userid) throws MyException {
		return dcService.DevStateNum(userid);
	}

	// 车位利用率
	@PostMapping("/dc/DevUseRate")
	public List<DevUseRate> DevUseRate(Integer querytype, Integer userid) throws MyException {
		return dcService.DevUseRate(querytype);
	}

	// 停车时段分布
	@PostMapping("/dc/DCParkingSlotsData")
	public List<DCParkingSlotsData> DCParkingSlotsData(Integer userid) throws MyException {
		return dcService.DCParkingSlotsData(userid);
	}

	// 根据用户id和模块id查询项目组
	@PostMapping("/dc/selectGroList")
	public List<TGroup> selectGroList(Integer userid, Integer moduleid) {
		return dcService.selectGroList(userid, moduleid);
	}

	// 收入统计
	@PostMapping("/dc/DCIncome")
	public List<DCIncome> DCIncome(Integer userid) {
		return dcService.DCIncome(userid);
	}
	
	@PostMapping("/dc/selectSixMonthPakingCount")
	public List<Integer> selectSixMonthPakingCount(String beforeSixMonth,Integer userid,Integer devid) {
		return dcService.selectSixMonthPakingCount(beforeSixMonth,userid,devid);
	}
	
	@PostMapping("/dc/selectSixMonthDevCount")
	public Integer selectSixMonthDevCount(String thatMonth,Integer userid) {
		return dcService.selectSixMonthDevCount(thatMonth,userid);
	}
	
	@PostMapping("/dc/selectSixMonthPakingCountMoney")
	public List<Integer> selectSixMonthPakingCountMoney(String beforeSixMonth,Integer userid,Integer devid) {
		return dcService.selectSixMonthPakingCountMoney(beforeSixMonth, userid, devid);
	}
	
	@PostMapping("/dc/devPackingTimesAndRank")
	public DCDevInfo devPackingTimesAndRank(Integer userid,Integer devid) {
		return dcService.devPackingTimesAndRank(userid, devid);
	}
	
	@PostMapping("/dc/devPackingMoneyAndRank")
	public DCDevInfo devPackingMoneyAndRank(Integer userid,Integer devid) {
		return dcService.devPackingMoneyAndRank(userid, devid);
	}
	
	@PostMapping("/dc/queryDev")
	public DCDevInfo queryDev(Integer userid,Integer devid) {
		return dcService.queryDev(userid, devid);
	}
	
	@PostMapping("/dc/selectSummary")
	public DCDevInfo selectSummary(Integer userid,Integer devid) throws ParseException {
		return dcService.selectSummary(userid, devid);
	}
}
