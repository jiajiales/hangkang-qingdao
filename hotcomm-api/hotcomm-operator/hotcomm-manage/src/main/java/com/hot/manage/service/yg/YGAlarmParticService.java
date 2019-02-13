package com.hot.manage.service.yg;

import java.util.List;
import com.github.pagehelper.Page;
import com.hot.manage.entity.yg.vo.YGAlarmCount;
import com.hot.manage.entity.yg.vo.YGAlarmPartic;
import com.hot.manage.entity.yg.vo.YGAlarmingTrend;
import com.hot.manage.entity.yg.vo.YGAlarmingTrendTest;
import com.hot.manage.entity.yg.vo.YGDevAlarmHandleByTimeVO;
import com.hot.manage.entity.yg.vo.YGHistoricalDate;
import com.hot.manage.entity.yg.vo.YGHistoricalStateDate;
import com.hot.manage.exception.MyException;

public interface YGAlarmParticService {

	/**
	 * 根据处理状态,报警类型查询报警详情
	 * 
	 * @param handlestate
	 * @param stateid
	 * @return
	 * @throws MyException
	 */
	Page<YGAlarmPartic> selectForState(Integer handlestate, Integer stateid) throws MyException;

	/**
	 * 根据时间,设备编号,负责人等查询报警详情
	 * 
	 * @param startTime
	 * @param endTime
	 * @param message
	 * @return
	 * @throws MyException
	 */
	Page<YGAlarmPartic> selectForTime(String startTime, String endTime, String message) throws MyException;

	/**
	 * 查询报警历史数据
	 * 
	 * @param deviceid
	 * @param theyear
	 * @return
	 * @throws MyException
	 */
	List<YGHistoricalDate> selectHistoricalDate(Integer deviceid, Integer theyear) throws MyException;

	/**
	 * 查询报警历史类型数据
	 * 
	 * @param deviceid
	 * @param theyear
	 * @return
	 * @throws MyException
	 */
	List<YGHistoricalStateDate> selectHistoricalStateDate(Integer deviceid, Integer theyear) throws MyException;

	/**
	 * 关闭报警
	 * 
	 * @param alarmid
	 * @return
	 * @throws MyException
	 */
	Integer closeYGAlarm(Integer alarmid) throws MyException;

	/**
	 * 
	 * @param userid
	 * @param year
	 * @return
	 * @throws MyException
	 */
	List<YGAlarmingTrendTest> smokeAlarmingTrendThreeYear(Integer userid, Integer moduleid,Integer queryType,Integer groupid) throws MyException;
	
	List<YGAlarmingTrend> smokeAlarmingTrendThreeYear2(Integer userid, Integer moduleid,Integer queryType,Integer groupid) throws MyException;
	
	List<YGAlarmingTrend> smokeAlarmingTrendDay(Integer userid, Integer moduleid,Integer queryType,Integer groupid) throws MyException;
	
	List<YGAlarmingTrend> smokeAlarmingTrendHalfYear(Integer userid, Integer moduleid,Integer queryType,Integer groupid) throws MyException;
	
	List<YGAlarmingTrend> smokeAlarmingTrendByGroup(Integer userid, Integer moduleid,Integer queryType,Integer groupid) throws MyException;

	/**
	 * 
	 * @param queryType
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws MyException
	 */
	List<YGDevAlarmHandleByTimeVO> YGselectDevAlarmHandleByTime(Integer queryType, String startTime, String endTime,
			Integer userid) throws MyException;

	/**
	 * 
	 * @param userid
	 * @param year
	 * @return
	 * @throws MyException
	 */
	List<YGAlarmCount> YGAlarmTypeCount(Integer userid, Integer year) throws MyException;

}
