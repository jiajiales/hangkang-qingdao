package com.hotcomm.prevention.db.mysql.datashow.sj;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hotcomm.prevention.bean.mysql.datashow.sj.AlarmTypeList;
import com.hotcomm.prevention.bean.mysql.datashow.sj.DevInfo;
import com.hotcomm.prevention.bean.mysql.datashow.sj.DevState;
import com.hotcomm.prevention.bean.mysql.datashow.sj.GroupListDev;
import com.hotcomm.prevention.bean.mysql.datashow.sj.SeleteMap;
import com.hotcomm.prevention.bean.mysql.datashow.sj.selectDev;
import com.hotcomm.prevention.bean.mysql.datashow.sj.vo.BatteryDiagramVO;
import com.hotcomm.prevention.bean.mysql.datashow.sj.vo.DevAgingRateVO;
import com.hotcomm.prevention.bean.mysql.datashow.sj.vo.DevAlarmHandleByTimeVO;
import com.hotcomm.prevention.bean.mysql.datashow.sj.vo.DevAlarmHandleTypeVO;
import com.hotcomm.prevention.bean.mysql.datashow.sj.vo.DevFailureRateVO;
import com.hotcomm.prevention.bean.mysql.datashow.sj.vo.DevOpenTimesVO;
import com.hotcomm.prevention.bean.mysql.datashow.sj.vo.DevStateCountVO;
import com.hotcomm.prevention.bean.mysql.datashow.sj.vo.DevStateVO;
import com.hotcomm.prevention.exception.MyException;

public interface WaterImmersionMapper {

	/**
	 * @param time
	 * @param moduleID
	 * @param userID
	 * @return
	 * @throws MyException
	 */
	List<DevStateVO> selectDevState(@Param("Time") Integer time, @Param("moduleID") Integer moduleID,
			@Param("userID") Integer userID) throws MyException;

	/**
	 * @param time
	 * @param moduleID
	 * @param userID
	 * @return
	 * @throws MyException
	 */
	List<AlarmTypeList> AlarmHandleForType(@Param("Time") Integer time, @Param("moduleID") Integer moduleID,
			@Param("userID") Integer userID) throws MyException;

	/**
	 * @param starttime
	 * @param endtime
	 * @param moduleid
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	BatteryDiagramVO selectBattery(@Param("starttime") String starttime, @Param("endtime") String endtime,
			@Param("moduleid") Integer moduleid, @Param("userid") Integer userid) throws MyException;

	/**
	 * @param moduleid
	 * @param querytype
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	List<DevOpenTimesVO> selectDevOpenTimes(@Param("moduleid") Integer moduleid, @Param("querytype") Integer querytype,
			@Param("userid") Integer userid) throws MyException;

	/**
	 * @param moduleID
	 * @param userID
	 * @return
	 * @throws MyException
	 */
	List<DevAgingRateVO> selectDevAgingRate(@Param("moduleID") Integer moduleID, @Param("userID") Integer userID)
			throws MyException;

	/**
	 * @param moduleID
	 * @param userID
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws MyException
	 */
	List<DevFailureRateVO> selectFailureRate(@Param("moduleID") Integer moduleID, @Param("userID") Integer userID,
			@Param("startTime") String startTime, @Param("endTime") String endTime) throws MyException;

	/**
	 * @param groupid
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	List<SeleteMap> SeleteMap(@Param("groupid") Integer groupid, @Param("userid") Integer userid) throws MyException;

	/**
	 * @param year
	 * @param deviceid
	 * @param mouduleid
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	DevInfo selectOneDevInfo(@Param("year") Integer year, @Param("deviceid") Integer deviceid,
			@Param("mouduleid") Integer mouduleid, @Param("userid") Integer userid) throws MyException;

	/**
	 * @param moduleid
	 * @param groupid
	 * @param site
	 * @return
	 * @throws MyException
	 */
	List<GroupListDev> GroupListDev(@Param("moduleid") Integer moduleid, @Param("groupid") Integer groupid,@Param("site") String site)
			throws MyException;

	/**
	 * @param moduleID
	 * @param userID
	 * @param startTime
	 * @param endTime
	 * @param queryType
	 * @return
	 * @throws MyException
	 */
	List<DevAlarmHandleByTimeVO> selectDevAlarmHandleByTime(@Param("moduleID") Integer moduleID,
			@Param("userID") Integer userID, @Param("startTime") String startTime,  @Param("endTime") String endTime,  @Param("queryType") Integer queryType)
			throws MyException;

	/**
	 * @param devid
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	selectDev selectDev(@Param("devid") Integer devid, @Param("moduleid") Integer moduleid) throws MyException;

	/**
	 * @param Time
	 * @param moduleID
	 * @param userID
	 * @param devid
	 * @return
	 * @throws MyException
	 */
	DevState AlarmList(@Param("Time") Integer Time, @Param("moduleID") Integer moduleID,
			@Param("userID") Integer userID, @Param("devid") Integer devid) throws MyException;

	/**
	 * @param time
	 * @param moduleID
	 * @param userID
	 * @return
	 * @throws MyException
	 */
	List<DevAlarmHandleTypeVO> selectDevAlarmHandleType(@Param("Time") Integer Time,
			@Param("moduleID") Integer moduleID, @Param("userID") Integer userID) throws MyException;

	/**
	 * @param moduleID
	 * @param userID
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws MyException
	 */
	DevStateCountVO selectDevStateCount(@Param("moduleID") Integer moduleID, @Param("userID") Integer userID,
			@Param("startTime") String startTime, @Param("endTime") String endTime) throws MyException;

	/**
	 * @param userid
	 * @param moduleid
	 * @param code
	 * @return
	 * @throws MyException
	 */
	List<Integer> selectDevList(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid,
			@Param("code") String code) throws MyException;

}
