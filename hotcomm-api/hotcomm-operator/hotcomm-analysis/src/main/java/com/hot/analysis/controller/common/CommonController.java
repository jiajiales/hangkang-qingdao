package com.hot.analysis.controller.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hot.analysis.bean.common.BatteryDiagramVO;
import com.hot.analysis.bean.common.DevAgingRateVO;
import com.hot.analysis.bean.common.DevAlarmHandleByTimeVO;
import com.hot.analysis.bean.common.DevAlarmHandleTypeVO;
import com.hot.analysis.bean.common.DevFailureRateVO;
import com.hot.analysis.bean.common.DevInfo;
import com.hot.analysis.bean.common.DevOpenTimesVO;
import com.hot.analysis.bean.common.DevState;
import com.hot.analysis.bean.common.DevStateCountVO;
import com.hot.analysis.bean.common.GroupListDev;
import com.hot.analysis.exception.MyException;
import com.hot.analysis.service.common.CommonService;
import com.hot.analysis.bean.common.SeleteMap;
import com.hot.analysis.bean.common.TDeviceYg;
import com.hot.analysis.bean.common.TGroup;
import com.hot.analysis.bean.common.selectDev;

@RestController
public class CommonController {

	@Autowired
	private CommonService commonService;

	// 根据模块ID查询设备电量分布
	@PostMapping("/common/selectBattery")
	public BatteryDiagramVO selectBattery(String starttime, String endtime, Integer moduleid, Integer userid)
			throws MyException {
		return commonService.selectBattery(starttime, endtime, moduleid, userid);
	}

	// 根据模块ID查询设备开启次数
	@PostMapping("/common/selectDevOpenTimes")
	public List<DevOpenTimesVO> selectDevOpenTimes(Integer moduleid, Integer querytype, Integer userid)
			throws MyException {
		return commonService.selectDevOpenTimes(moduleid, querytype, userid);
	}

	// 根据模块ID查询单个设备信息
	@PostMapping("/common/selectOneDevInfo")
	public DevInfo selectOneDevInfo(Integer year,Integer deviceid,Integer mouduleid,Integer userid) throws MyException {
		return commonService.selectOneDevInfo(year, deviceid, mouduleid, userid);
	}

	// 根据模块ID查询设备老化率
	@PostMapping("/common/selectDevAgingRate")
	public List<DevAgingRateVO> selectDevAgingRate(Integer moduleID, Integer userID) throws MyException {
		return commonService.selectDevAgingRate(moduleID, userID);
	}

	// 查询项目楼层图片
	@PostMapping("/common/SeleteMap")
	public List<SeleteMap> SeleteMap(Integer groupid, Integer userid) throws MyException {
		return commonService.SeleteMap(groupid, userid);
	}

	// 项目组平面图设备查看
	@PostMapping("/common/GroupListDev")
	public List<GroupListDev> GroupListDev(Integer moduleid, Integer groupid, String site) throws MyException {
		return commonService.GroupListDev(moduleid, groupid, site);
	}

	// 根据模块ID查询设备损坏率
	@PostMapping("/common/selectFailureRate")
	public List<DevFailureRateVO> selectFailureRate(Integer moduleID, Integer userID, String startTime, String endTime)
			throws MyException {
		return commonService.selectFailureRate(moduleID, userID, startTime, endTime);
	}

	// 根据模块ID和时间查询报警处理
	@PostMapping("/common/selectDevAlarmHandleByTime")
	public List<DevAlarmHandleByTimeVO> selectDevAlarmHandleByTime(Integer moduleID, Integer userID, String startTime,
			String endTime, Integer queryType) throws MyException {
		return commonService.selectDevAlarmHandleByTime(moduleID, userID, startTime, endTime, queryType);
	}

	// 查询设备信息
	@PostMapping("/common/selectDev")
	public selectDev selectDev(Integer devid, Integer moduleid) throws MyException {
		return commonService.selectDev(devid, moduleid);
	}

	// 查询设备报警数量
	@PostMapping("/common/AlarmList")
	public DevState AlarmList(Integer Time, Integer moduleID, Integer userID, Integer devid) throws MyException {
		return commonService.AlarmList(Time, moduleID, userID, devid);
	}

	// 根据模块ID查询设备报警类型统计(水位设备不适用)
	@PostMapping("/common/selectDevAlarmHandleType")
	public List<DevAlarmHandleTypeVO> selectDevAlarmHandleType(Integer Time, Integer moduleID, Integer userID)
			throws MyException {
		return commonService.selectDevAlarmHandleType(Time, moduleID, userID);
	}

	// 根据模块ID查询不同状态的设备数量(地磁不适用)
	@PostMapping("/common/selectDevStateCount")
	public DevStateCountVO selectDevStateCount(Integer moduleID, Integer userID, String startTime, String endTime)
			throws MyException {
		return commonService.selectDevStateCount(moduleID, userID, startTime, endTime);
	}

	// 地图标注所有的项目组
	@PostMapping("/common/selectDevList")
	public List<TDeviceYg> selectDevList(Integer userid, Integer moduleid, String code) {
		return commonService.selectDevList(userid, moduleid, code);
	}

}
