package com.hot.manage.db.yg;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hot.manage.entity.yg.vo.YGAlarmCount;
import com.hot.manage.entity.yg.vo.YGAlarmPartic;
import com.hot.manage.entity.yg.vo.YGAlarmingTrend;
import com.hot.manage.entity.yg.vo.YGDevAlarmHandleByTimeVO;
import com.hot.manage.entity.yg.vo.YGHistoricalDate;
import com.hot.manage.entity.yg.vo.YGHistoricalStateDate;
import com.hot.manage.exception.MyException;

public interface YGAlarmParticMapper {

	/**
	 * 
	 * @param handlestate
	 * @param stateid
	 * @return
	 * @throws MyException
	 */
	Page<YGAlarmPartic> selectForState(@Param("handlestate") Integer handlestate, @Param("stateid") Integer stateid)
			throws MyException;

	/**
	 * 
	 * @param startTime
	 * @param endTime
	 * @param message
	 * @return
	 * @throws MyException
	 */
	Page<YGAlarmPartic> selectForTime(@Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("message") String message) throws MyException;

	/**
	 * 
	 * @param deviceid
	 * @param theyear
	 * @return
	 * @throws MyException
	 */
	List<YGHistoricalDate> selectHistoricalDate(@Param("deviceid") Integer deviceid, @Param("theyear") Integer theyear)
			throws MyException;

	/**
	 * 
	 * @param deviceid
	 * @param theyear
	 * @return
	 * @throws MyException
	 */
	List<YGHistoricalStateDate> selectHistoricalStateDate(@Param("deviceid") Integer deviceid,
			@Param("theyear") Integer theyear) throws MyException;

	/**
	 * 关闭报警
	 * 
	 * @param alarmid
	 * @return
	 */
	Integer closeYGalarmById(@Param("alarmid") Integer alarmid);

	/**
	 * 
	 * @param userid
	 * @param year
	 * @return
	 * @throws MyException
	 */
	List<YGAlarmingTrend> smokeAlarmingTrend(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid,
			@Param("queryType") Integer queryType,@Param("groupid") Integer groupid) throws MyException;
	
	List<YGAlarmingTrend> smokeAlarmingTrendByGroup(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid,
			@Param("queryType") Integer queryType,@Param("groupid") Integer groupid) throws MyException;

	/**
	 * 
	 * @param queryType
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws MyException
	 */
	List<YGDevAlarmHandleByTimeVO> YGselectDevAlarmHandleByTime(@Param("queryType") Integer queryType,
			@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("userid") Integer userid)
					throws MyException;

	/**
	 * 
	 * @param userid
	 * @param year
	 * @return
	 * @throws MyException
	 */
	List<YGAlarmCount> YGAlarmTypeCount(@Param("userid") Integer userid, @Param("year") Integer year)
			throws MyException;

}
