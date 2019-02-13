package com.hotcomm.prevention.service.manage.projectoverview;

import java.util.List;

import com.github.pagehelper.Page;
import com.hotcomm.prevention.bean.mysql.manage.group.AlarmHandleNums;
import com.hotcomm.prevention.bean.mysql.manage.group.AlarmNums;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.AlarmHandleStatistics;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.AlarmStateStatistics;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.AlarmTendecyVo;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.AlarmingTrendVO;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.Groupgkcount;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.SXDLAlarmingTrendVO;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.SXDYAlarmingTrendVO;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.SYDLAlarmingTrendVO;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.YGAlarmCount;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.YGAlarmPartic;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.YGAlarmingTrend;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.YGAlarmingTrendTest;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.YGDevAlarmHandleByTimeVO;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.alarmcount_class;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.alarmcount_day;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.alarmcount_month;
import com.hotcomm.prevention.exception.MyException;

public interface GroupgkService {
	public Groupgkcount groupgkcount(Integer userid, Integer moduleid) throws MyException;

	public List<alarmcount_month> select_alarm_count_month(Integer userid, Integer moduleid, Integer yyyy)
			throws MyException;

	public List<alarmcount_day> select_alarm_count_day(Integer userid, Integer moduleid) throws MyException;

	public List<alarmcount_class> select_alarm_count_class(Integer userid, Integer moduleid, Integer yyyy)
			throws MyException;

	// 可燃气
	AlarmTendecyVo selectKRQAlarmForDay(Integer groupid) throws MyException;

	AlarmTendecyVo selectKRQAlarmForMonth(Integer groupid) throws MyException;

	AlarmTendecyVo selectKRQAlarmForThreeYear(Integer groupid) throws MyException;

	List<AlarmHandleStatistics> selectKRQAlarmForWeeken(Integer groupid) throws MyException;

	List<AlarmStateStatistics> selectKRQAlarmForState(Integer groupid, Integer TheType) throws MyException;

	// 垃圾桶
	public AlarmTendecyVo selectAlarmForDay(Integer groupid) throws MyException;

	public AlarmTendecyVo selectAlarmForMonth(Integer groupid) throws MyException;

	public AlarmTendecyVo selectAlarmForThreeYear(Integer groupid) throws MyException;

	List<AlarmHandleStatistics> selectAlarmForWeeken(Integer groupid) throws MyException;

	List<AlarmStateStatistics> selectAlarmForState(Integer groupid, Integer TheType) throws MyException;

	// 三项电流
	List<SXDLAlarmingTrendVO> AlarmingTrendForSXDL(Integer queryType, Integer userid, Integer groupid);

	// 三项电压
	List<SXDYAlarmingTrendVO> AlarmingTrendForSXDY(Integer queryType, Integer userid, Integer groupid);

	// 水压
	AlarmTendecyVo selectSYAlarmForDay(Integer groupid) throws MyException;

	AlarmTendecyVo selectSYAlarmForMonth(Integer groupid) throws MyException;

	AlarmTendecyVo selectSYAlarmForThreeYear(Integer groupid) throws MyException;

	List<AlarmHandleStatistics> selectSYAlarmForWeeken(Integer groupid) throws MyException;

	List<AlarmStateStatistics> selectSYAlarmForState(Integer groupid, Integer TheType) throws MyException;

	// 剩余电流
	List<SYDLAlarmingTrendVO> AlarmingTrendForSYDL(Integer queryType, Integer userid, Integer groupid);

	// 烟感
	Page<YGAlarmPartic> selectForState(Integer handlestate, Integer stateid) throws MyException;

	Page<YGAlarmPartic> selectForTime(String startTime, String endTime, String message) throws MyException;

	Integer closeYGAlarm(Integer alarmid) throws MyException;

	List<YGAlarmingTrendTest> smokeAlarmingTrendThreeYear(Integer userid, Integer moduleid, Integer queryType,
			Integer groupid) throws MyException;

	List<YGAlarmingTrend> smokeAlarmingTrendThreeYear2(Integer userid, Integer moduleid, Integer queryType,
			Integer groupid) throws MyException;

	List<YGAlarmingTrend> smokeAlarmingTrendDay(Integer userid, Integer moduleid, Integer queryType, Integer groupid)
			throws MyException;

	List<YGAlarmingTrend> smokeAlarmingTrendHalfYear(Integer userid, Integer moduleid, Integer queryType,
			Integer groupid) throws MyException;

	List<YGAlarmingTrend> smokeAlarmingTrendByGroup(Integer userid, Integer moduleid, Integer queryType,
			Integer groupid) throws MyException;

	List<YGDevAlarmHandleByTimeVO> YGselectDevAlarmHandleByTime(Integer queryType, String startTime, String endTime,
			Integer userid) throws MyException;

	List<YGAlarmCount> YGAlarmTypeCount(Integer userid, Integer year) throws MyException;

	// 液位计
	List<AlarmingTrendVO> AlarmingTrendForWhichHasBoundaryValue(Integer queryType, Integer userid, Integer groupid);

	// 公共模块, 报警处理类型,报警处理率
	List<AlarmNums> selectAlarmNums(Integer moduleID, Integer userid, Integer queryType);

	List<AlarmHandleNums> selectAlarmHandleNums(Integer moduleID, Integer userid, Integer queryType);

}
