package com.hotcomm.prevention.db.mysql.manage.projectoverview;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hotcomm.prevention.bean.mysql.manage.group.AlarmHandleNums;
import com.hotcomm.prevention.bean.mysql.manage.group.AlarmNums;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.AlarmHandleStatistics;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.AlarmStateStatistics;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.AlarmTendency;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.AlarmingTrendVO;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.Groupgkcount;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.SXDLAlarmingTrendVO;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.SXDYAlarmingTrendVO;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.SYAlarmTendency;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.SYDLAlarmingTrendVO;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.YGAlarmCount;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.YGAlarmPartic;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.YGAlarmingTrend;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.YGDevAlarmHandleByTimeVO;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.alarmcount_class;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.alarmcount_day;
import com.hotcomm.prevention.bean.mysql.manage.projectoverview.alarmcount_month;
import com.hotcomm.prevention.exception.MyException;

public interface GroupgkMapper {
	public Groupgkcount groupgkcount(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid);

	public List<alarmcount_month> select_alarm_count_month(@Param("userid") Integer userid,
			@Param("moduleid") Integer moduleid, @Param("yyyy") Integer yyyy);

	public List<alarmcount_day> select_alarm_count_day(@Param("userid") Integer userid,
			@Param("moduleid") Integer moduleid);

	public List<alarmcount_class> select_alarm_count_class(@Param("userid") Integer userid,
			@Param("moduleid") Integer moduleid, @Param("yyyy") Integer yyyy);

	// 可燃气
	List<AlarmTendency> selectKRQAlarmForDay(@Param("groupid") Integer groupid) throws MyException;

	List<AlarmTendency> selectKRQAlarmForMonth(@Param("groupid") Integer groupid) throws MyException;

	List<AlarmTendency> selectKRQAlarmForThreeYear(@Param("groupid") Integer groupid) throws MyException;

	List<AlarmHandleStatistics> selectKRQAlarmForWeeken(@Param("groupid") Integer groupid) throws MyException;

	List<AlarmStateStatistics> selectKRQAlarmForState(@Param("groupid") Integer groupid,
			@Param("TheType") Integer TheType) throws MyException;

	// 垃圾桶
	List<AlarmTendency> selectAlarmForDay(@Param("groupid") Integer groupid) throws MyException;

	List<AlarmTendency> selectAlarmForMonth(@Param("groupid") Integer groupid) throws MyException;

	List<AlarmTendency> selectAlarmForThreeYear(@Param("groupid") Integer groupid) throws MyException;

	List<AlarmHandleStatistics> selectAlarmForWeeken(@Param("groupid") Integer groupid) throws MyException;

	List<AlarmStateStatistics> selectAlarmForState(@Param("groupid") Integer groupid, @Param("TheType") Integer TheType)
			throws MyException;

	// 三项电流
	List<SXDLAlarmingTrendVO> AlarmingTrendForSXDL(@Param("queryType") Integer queryType,
			@Param("userid") Integer userid, @Param("groupid") Integer groupid);

	// 三相电压
	List<SXDYAlarmingTrendVO> AlarmingTrendForSXDY(@Param("queryType") Integer queryType,
			@Param("userid") Integer userid, @Param("groupid") Integer groupid);

	// 水压
	List<SYAlarmTendency> selectSYAlarmForDay(@Param("groupid") Integer groupid) throws MyException;

	List<SYAlarmTendency> selectSYAlarmForMonth(@Param("groupid") Integer groupid) throws MyException;

	List<SYAlarmTendency> selectSYAlarmForThreeYear(@Param("groupid") Integer groupid) throws MyException;

	List<AlarmHandleStatistics> selectSYAlarmForWeeken(@Param("groupid") Integer groupid) throws MyException;

	List<AlarmStateStatistics> selectSYAlarmForState(@Param("groupid") Integer groupid,
			@Param("TheType") Integer TheType) throws MyException;

	// 剩余电压
	List<SYDLAlarmingTrendVO> AlarmingTrendForSYDL(@Param("queryType") Integer queryType,
			@Param("userid") Integer userid, @Param("groupid") Integer groupid);

	// 烟感
	Page<YGAlarmPartic> selectForState(@Param("handlestate") Integer handlestate, @Param("stateid") Integer stateid)
			throws MyException;

	Page<YGAlarmPartic> selectForTime(@Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("message") String message) throws MyException;

	Integer closeYGalarmById(@Param("alarmid") Integer alarmid);

	List<YGAlarmingTrend> smokeAlarmingTrend(@Param("userid") Integer userid, @Param("moduleid") Integer moduleid,
			@Param("queryType") Integer queryType, @Param("groupid") Integer groupid) throws MyException;

	List<YGAlarmingTrend> smokeAlarmingTrendByGroup(@Param("userid") Integer userid,
			@Param("moduleid") Integer moduleid, @Param("queryType") Integer queryType,
			@Param("groupid") Integer groupid) throws MyException;

	List<YGDevAlarmHandleByTimeVO> YGselectDevAlarmHandleByTime(@Param("queryType") Integer queryType,
			@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("userid") Integer userid)
			throws MyException;

	List<YGAlarmCount> YGAlarmTypeCount(@Param("userid") Integer userid, @Param("year") Integer year)
			throws MyException;

	// 液位计
	List<AlarmingTrendVO> AlarmingTrendForWhichHasBoundaryValue(@Param("queryType") Integer queryType,
			@Param("userid") Integer userid, @Param("groupid") Integer groupid);
	
	//公共模快,报警处理类型,报警处理率
	List<AlarmNums> selectAlarmNums(@Param("moduleID") Integer moduleID, @Param("userid") Integer userid,
			@Param("queryType") Integer queryType);
	
	List<AlarmHandleNums> selectAlarmHandleNums(@Param("moduleID") Integer moduleID, @Param("userid") Integer userid,
			@Param("queryType") Integer queryType);

}
