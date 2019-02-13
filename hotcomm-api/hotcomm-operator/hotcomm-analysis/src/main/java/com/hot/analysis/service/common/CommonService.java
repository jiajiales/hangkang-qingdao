package com.hot.analysis.service.common;

import java.util.List;

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
import com.hot.analysis.bean.common.SeleteMap;
import com.hot.analysis.bean.common.TDeviceYg;
import com.hot.analysis.bean.common.TGroup;
import com.hot.analysis.bean.common.selectDev;
import com.hot.analysis.exception.MyException;

public interface CommonService {

	// 根据模块ID查询设备电量分布
	BatteryDiagramVO selectBattery(String startTime, String endTime, Integer moduleid, Integer userid)
			throws MyException;

	// 根据模块ID查询设备开启次数
	List<DevOpenTimesVO> selectDevOpenTimes(Integer moduleid, Integer querytype, Integer userid) throws MyException;

	// 根据模块ID查询设备老化率
	List<DevAgingRateVO> selectDevAgingRate(Integer moduleID, Integer userID) throws MyException;

	// 根据模块ID查询单个设备信息
	DevInfo selectOneDevInfo(Integer year,Integer deviceId,Integer moduleID,Integer userID) throws MyException;

	// 查询项目楼层图片
	List<SeleteMap> SeleteMap(Integer groupid, Integer userid);

	// 项目组平面图设备查看
	List<GroupListDev> GroupListDev(Integer moduleid, Integer groupid, String site) throws MyException;

	// 根据模块ID查询设备损坏率
	List<DevFailureRateVO> selectFailureRate(Integer moduleID, Integer userID, String startTime, String endTime);

	// 根据模块ID和时间查询报警处理
	List<DevAlarmHandleByTimeVO> selectDevAlarmHandleByTime(Integer moduleID, Integer userID, String startTime,
			String endTime, Integer queryType);

	// 查询设备信息
	selectDev selectDev(Integer devid, Integer moduleid);

	// 查询设备报警数量
	DevState AlarmList(Integer Time, Integer moduleID, Integer userID, Integer devid);

	// 根据模块ID查询设备报警类型统计(水位设备不适用)
	List<DevAlarmHandleTypeVO> selectDevAlarmHandleType(Integer Time, Integer moduleID, Integer userID);

	// 地图标注所有的项目组
	List<TDeviceYg> selectDevList(Integer userid, Integer moduleid, String code);

	// 根据模块ID查询不同状态的设备数量(地磁不适用)
	DevStateCountVO selectDevStateCount(Integer moduleID, Integer userID, String startTime, String endTime);

}
