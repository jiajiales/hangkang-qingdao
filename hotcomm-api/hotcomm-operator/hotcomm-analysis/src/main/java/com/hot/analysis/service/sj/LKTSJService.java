package com.hot.analysis.service.sj;

import java.util.List;
import com.hot.analysis.bean.sj.LKTSJAlarmHandleForType;
import com.hot.analysis.bean.sj.LKTSJAlarmList;
import com.hot.analysis.bean.sj.LKTSJDevState;

public interface LKTSJService {

	List<LKTSJDevState> LKTSJDevState(Integer Time, Integer moduleID, Integer userID);

	List<LKTSJAlarmList> LKTSJAlarmList(Integer Time, Integer moduleID, Integer userID);
}
