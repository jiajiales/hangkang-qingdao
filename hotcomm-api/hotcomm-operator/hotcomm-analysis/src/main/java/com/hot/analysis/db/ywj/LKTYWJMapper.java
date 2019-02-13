package com.hot.analysis.db.ywj;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.hot.analysis.bean.sj.LKTSJAlarmList;
import com.hot.analysis.bean.ywj.LKTYWJselectFlipNum;

public interface LKTYWJMapper {

	List<LKTYWJselectFlipNum> LKTYWJselectFlipNum(@Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("moduleid") Integer moduleid, @Param("userid") Integer userid);
	
	List<LKTSJAlarmList> YWJAlarmType(@Param("Time") Integer Time, @Param("moduleID") Integer moduleID,
			@Param("userID") Integer userID);
}
