package com.hot.analysis.db.common;

import java.util.List;
import org.apache.ibatis.annotations.Param;
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

public interface CommonMapper {

	// 根据模块ID查询设备电量分布
	BatteryDiagramVO selectBattery(@Param("starttime") String starttime, @Param("endtime") String endtime,
			@Param("moduleid") Integer moduleid, @Param("userid") Integer userid);

	// 根据模块ID查询设备开启次数
	List<DevOpenTimesVO> selectDevOpenTimes(@Param("moduleid") Integer moduleid, @Param("querytype") Integer querytype,
			@Param("userid") Integer userid);

	// 根据模块ID查询设备老化率
	List<DevAgingRateVO> selectDevAgingRate(@Param("moduleID") Integer moduleID, @Param("userID") Integer userID);

	// 根据模块ID查询单个设备信息
	DevInfo selectOneDevInfo(@Param("year") Integer year, @Param("deviceId") Integer deviceId,
			@Param("moduleID") Integer moduleID, @Param("userid") Integer userid);

	// 查询项目楼层图片
	List<SeleteMap> SeleteMap(@Param("groupid") Integer groupid, @Param("userid") Integer userid);

	// 项目组平面图设备查看
	List<GroupListDev> GroupListDev(@Param("moduleid") Integer moduleid, @Param("groupid") Integer groupid,
			@Param("site") String site);

	// 根据模块ID查询设备损坏率
	List<DevFailureRateVO> selectFailureRate(@Param("moduleID") Integer moduleID, @Param("userID") Integer userID,
			@Param("startTime") String startTime, @Param("endTime") String endTime);

	// 根据模块ID和时间查询报警处理
	List<DevAlarmHandleByTimeVO> selectDevAlarmHandleByTime(@Param("moduleID") Integer moduleID,
			@Param("userID") Integer userID, @Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("queryType") Integer queryType);

	// 根据模块ID和用户id以及输入输入的数据查询
	List<TDeviceYg> selectYgList(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid,
			@Param("code") String code);

	// 查询设备信息
	selectDev selectDev(@Param("devid") Integer devid, @Param("moduleid") Integer moduleid);

	// 查询设备报警数量
	DevState AlarmList(@Param("Time") Integer Time, @Param("moduleID") Integer moduleID,
			@Param("userID") Integer userID, @Param("devid") Integer devid);

	// 根据模块ID查询设备报警类型统计(水位设备不适用)
	List<DevAlarmHandleTypeVO> selectDevAlarmHandleType(@Param("Time") Integer Time,
			@Param("moduleID") Integer moduleID, @Param("userID") Integer userID);

	// 地图标注所有的项目组
	List<TDeviceYg> selectDevList(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid,
			@Param("code") String code);

	// 根据模块ID查询不同状态的设备数量(地磁不适用)
	DevStateCountVO selectDevStateCount(@Param("moduleID") Integer moduleID, @Param("userID") Integer userID,
			@Param("startTime") String startTime, @Param("endTime") String endTime);

}
