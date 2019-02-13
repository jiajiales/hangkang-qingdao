package com.hot.analysis.controller.yg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hot.analysis.bean.common.DevAlarmHandleByTimeVO;
import com.hot.analysis.bean.common.DevAlarmHandleTypeVO;
import com.hot.analysis.bean.common.TDeviceYg;
import com.hot.analysis.bean.yg.GroupInfo;
import com.hot.analysis.bean.yg.SomkeAlarmNum;
import com.hot.analysis.exception.MyException;
import com.hot.analysis.service.common.CommonService;
import com.hot.analysis.service.yg.ISomkeDataAnalysisService;



@RestController

public class SmokeController {

	@Autowired
	private ISomkeDataAnalysisService somkeDataAnalysisService;
	
	@Autowired
	private CommonService commonService;
	
	
	// 根据模块ID查询设备报警类型统计
		@PostMapping("/somke/selectDevAlarmHandleType")
		public List<DevAlarmHandleTypeVO> selectDevAlarmHandleType(Integer Time, Integer moduleID, Integer userID) throws MyException {
			return commonService.selectDevAlarmHandleType(Time, moduleID, userID);
		}
	
	// 报警记录/报警类型统计
	@PostMapping("/somke/selectSomkeAlarmNums")
	public List<SomkeAlarmNum> selectSomkeAlarmNums(Integer Time, Integer moduleID, Integer userID) {
		
		List<SomkeAlarmNum> list = somkeDataAnalysisService.selectSomkeAlarmNums(Time, moduleID, userID);
		return list;
	}

	// 烟感报警趋势图
		@PostMapping("/somke/selectDevAlarmHandleByTime")
		public List<DevAlarmHandleByTimeVO> selectDevAlarmHandleByTime(Integer moduleID, Integer userID, String startTime,
				String endTime, Integer queryType) throws MyException {
			return commonService.selectDevAlarmHandleByTime(moduleID, userID, startTime, endTime, queryType);
		}
	

	// 根据id单个查询设备
	@PostMapping("/somke/selectDevById")
	public TDeviceYg selectDevById(@RequestParam("id") Integer id) {
		return somkeDataAnalysisService.selectDevById(id);
	}

	// 当前组下设备的列表
	@PostMapping("/somke/selectYgListByGroupId")
	public List<TDeviceYg> selectYgListByGroupId(@RequestParam("groupid") Integer groupid,
			@RequestParam("moduleid") Integer moduleid) {
		return somkeDataAnalysisService.selectYgListByGroupId(groupid, moduleid);
	}

	// 地图标注所有的设备
	@PostMapping("/somke/selectYgList")
	public List<TDeviceYg> selectYgList(@RequestParam("userid") Integer userid, @RequestParam("moduleid") Integer moduleid,  @RequestParam("code") String code) {
		return somkeDataAnalysisService.selectYgList(userid,moduleid,code);
	}

	// 查询全部的组信息列表
	@PostMapping("/somke/selectGroupInfoList")
	public List<GroupInfo> selectGroupInfoList(@RequestParam("userid") Integer userid,
			@RequestParam("moduleid") Integer moduleid) {
		return somkeDataAnalysisService.selectGroupInfoList(userid, moduleid);
	}

	// 按组名称查询组信息
	@PostMapping("/somke/selectGroupInfoByName")
	public List<GroupInfo> selectGroupInfoByName(@RequestParam("userid") Integer userid,
			@RequestParam("moduleid") Integer moduleid, @RequestParam("name") String name) {
		return somkeDataAnalysisService.selectGroupInfoByName(userid, moduleid, name);
	}
	
}
