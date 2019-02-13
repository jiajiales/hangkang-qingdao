package com.hotcomm.prevention.service.datashow.sj;

import java.util.List;

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

/**
 * @author Administrator
 *
 */
public interface WaterImmersionService {

	/**
	 * @param Time
	 * @param moduleID
	 * @param userID
	 * @return
	 * @throws MyException
	 */
	List<DevStateVO> selectDevState(Integer Time, Integer moduleID, Integer userID) throws MyException;

	/**
	 * @param time
	 * @param moduleID
	 * @param userID
	 * @return
	 * @throws MyException
	 */
	List<AlarmTypeList> AlarmHandleForType(Integer time, Integer moduleID, Integer userID) throws MyException;

	/**
	 * @param starttime
	 * @param endtime
	 * @param moduleid
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	BatteryDiagramVO selectBattery(String starttime, String endtime, Integer moduleid, Integer userid)
			throws MyException;

	/**
	 * @param moduleid
	 * @param querytype
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	List<DevOpenTimesVO> selectDevOpenTimes(Integer moduleid, Integer querytype, Integer userid) throws MyException;

	/**
	 * @param moduleID
	 * @param userID
	 * @return
	 * @throws MyException
	 */
	List<DevAgingRateVO> selectDevAgingRate(Integer moduleID, Integer userID) throws MyException;

	/**
	 * @param moduleID
	 * @param userID
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws MyException
	 */
	List<DevFailureRateVO> selectFailureRate(Integer moduleID, Integer userID, String startTime, String endTime)
			throws MyException;

	/**
	 * @param groupid
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	List<SeleteMap> SeleteMap(Integer groupid, Integer userid) throws MyException;

	/**
	 * @param year
	 * @param deviceid
	 * @param mouduleid
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	DevInfo selectOneDevInfo(Integer year, Integer deviceid, Integer mouduleid, Integer userid) throws MyException;

	/**
	 * @param moduleid
	 * @param groupid
	 * @param site
	 * @return
	 * @throws MyException
	 */
	List<GroupListDev> GroupListDev(Integer moduleid, Integer groupid, String site) throws MyException;

	/**
	 * @param moduleID
	 * @param userID
	 * @param startTime
	 * @param endTime
	 * @param queryType
	 * @return
	 * @throws MyException
	 */
	List<DevAlarmHandleByTimeVO> selectDevAlarmHandleByTime(Integer moduleID, Integer userID, String startTime,
			String endTime, Integer queryType) throws MyException;

	/**
	 * @param devid
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	selectDev selectDev(Integer devid, Integer moduleid) throws MyException;

	/**
	 * @param time
	 * @param moduleID
	 * @param userID
	 * @param devid
	 * @return
	 * @throws MyException
	 */
	DevState AlarmList(Integer time, Integer moduleID, Integer userID, Integer devid) throws MyException;

	/**
	 * @param time
	 * @param moduleID
	 * @param userID
	 * @return
	 * @throws MyException
	 */
	List<DevAlarmHandleTypeVO> selectDevAlarmHandleType(Integer time, Integer moduleID, Integer userID)
			throws MyException;

	/**
	 * @param moduleID
	 * @param userID
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws MyException
	 */
	DevStateCountVO selectDevStateCount(Integer moduleID, Integer userID, String startTime, String endTime)
			throws MyException;

	/**
	 * @param userid
	 * @param moduleid
	 * @param code
	 * @return
	 * @throws MyException
	 */
	List<Integer> selectDevList(Integer userid, Integer moduleid, String code) throws MyException;

}
