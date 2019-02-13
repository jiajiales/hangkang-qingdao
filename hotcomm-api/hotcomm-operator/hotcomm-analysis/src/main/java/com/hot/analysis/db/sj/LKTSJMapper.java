package com.hot.analysis.db.sj;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hot.analysis.bean.sj.LKTSJAlarmList;
import com.hot.analysis.bean.sj.LKTSJDevState;
import com.hot.analysis.exception.MyException;

public interface LKTSJMapper {

	List<LKTSJDevState> LKTSJDevState(@Param("Time") Integer Time, @Param("moduleID") Integer moduleID,
			@Param("userID") Integer userID) throws MyException;;

	List<LKTSJAlarmList> LKTSJAlarmList(@Param("Time") Integer Time, @Param("moduleID") Integer moduleID,
			@Param("userID") Integer userID);
}
