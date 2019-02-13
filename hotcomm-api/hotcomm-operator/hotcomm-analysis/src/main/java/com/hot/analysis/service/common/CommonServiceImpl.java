package com.hot.analysis.service.common;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hot.analysis.bean.common.BatteryDiagramVO;
import com.hot.analysis.bean.common.DevAgingRateVO;
import com.hot.analysis.bean.common.DevAlarmHandleByTimeVO;
import com.hot.analysis.bean.common.DevAlarmHandleTypeVO;
import com.hot.analysis.bean.common.DevFailureRateVO;
import com.hot.analysis.bean.common.DevInfo;
import com.hot.analysis.bean.common.DevOpenTimesVO;
import com.hot.analysis.bean.common.DevState;
import com.hot.analysis.bean.common.DevStateCountVO;
import com.hot.analysis.db.common.CommonMapper;
import com.hot.analysis.exception.MyException;
import com.hot.analysis.bean.common.SeleteMap;
import com.hot.analysis.bean.common.TDeviceYg;
import com.hot.analysis.bean.common.TGroup;
import com.hot.analysis.bean.common.GroupListDev;
import com.hot.analysis.bean.common.selectDev;

@Service
@Transactional
public class CommonServiceImpl implements CommonService {

	@Autowired
	private CommonMapper commonMapper;

	// 根据模块ID查询设备电量分布
	@Override
	public BatteryDiagramVO selectBattery(String startTime, String endTime, Integer moduleid, Integer userid)
			throws MyException {
		return commonMapper.selectBattery(startTime, endTime, moduleid, userid);
	}

	// 根据模块ID查询设备开启次数
	@Override
	public List<DevOpenTimesVO> selectDevOpenTimes(Integer moduleid, Integer querytype, Integer userid)
			throws MyException {
		return commonMapper.selectDevOpenTimes(moduleid, querytype, userid);
	}

	// 根据模块ID查询设备老化率
	@Override
	public List<DevAgingRateVO> selectDevAgingRate(Integer moduleID, Integer userID) throws MyException {
		return commonMapper.selectDevAgingRate(moduleID, userID);
	}

	// 查询项目楼层图片
	@Override
	public List<SeleteMap> SeleteMap(Integer groupid, Integer userid) {
		return commonMapper.SeleteMap(groupid, userid);
	}

	@Override
	public List<GroupListDev> GroupListDev(Integer moduleid, Integer groupid, String site) throws MyException {
		return commonMapper.GroupListDev(moduleid, groupid, site);
	}

	// 根据模块ID查询设备损坏率
	@Override
	public List<DevFailureRateVO> selectFailureRate(Integer moduleID, Integer userID, String startTime,
			String endTime) {
		return commonMapper.selectFailureRate(moduleID, userID, startTime, endTime);
	}

	// 根据模块ID和时间查询报警处理
	@Override
	public List<DevAlarmHandleByTimeVO> selectDevAlarmHandleByTime(Integer moduleID, Integer userID, String startTime,
			String endTime, Integer queryType) {
		return commonMapper.selectDevAlarmHandleByTime(moduleID, userID, startTime, endTime, queryType);
	}

	// 查询设备信息
	@Override
	public selectDev selectDev(Integer devid, Integer moduleid) {
		return commonMapper.selectDev(devid, moduleid);
	}

	// 查询设备报警数量
	@Override
	public DevState AlarmList(Integer Time, Integer moduleID, Integer userID, Integer devid) {
		DevState devState = commonMapper.AlarmList(Time, moduleID, userID, devid);
		devState.setModuleID(moduleID);
		devState.setYearStr(Time);
		return devState;
	}

	// 根据模块ID查询设备报警类型统计(水位设备不适用)
	@Override
	public List<DevAlarmHandleTypeVO> selectDevAlarmHandleType(Integer Time, Integer moduleID, Integer userID) {
		return commonMapper.selectDevAlarmHandleType(Time, moduleID, userID);
	}

	// 地图标注所有的项目组
	@Override
	public List<TDeviceYg> selectDevList(Integer userid, Integer moduleid, String code) {
		return commonMapper.selectDevList(userid, moduleid, code);
	}

	// 根据模块ID查询不同状态的设备数量(地磁不适用)
	@Override
	public DevStateCountVO selectDevStateCount(Integer moduleID, Integer userID, String startTime, String endTime) {
		return commonMapper.selectDevStateCount(moduleID, userID, startTime, endTime);
	}

	@Override
	public DevInfo selectOneDevInfo(Integer year, Integer deviceId, Integer moduleID, Integer userID)
			throws MyException {
		return commonMapper.selectOneDevInfo(year, deviceId, moduleID, userID);
	}


}
