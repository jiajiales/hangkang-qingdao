package com.hot.analysis.service.ywj;

import java.util.List;
import com.hot.analysis.bean.sj.LKTSJAlarmList;
import com.hot.analysis.bean.ywj.LKTYWJWeatherInfo;
import com.hot.analysis.bean.ywj.LKTYWJselectFlipNum;

public interface LKTYWJService {

	List<LKTYWJselectFlipNum> LKTYWJselectFlipNum(String startTime, String endTime, Integer moduleid, Integer userid);

	List<LKTYWJWeatherInfo> selectWeatherInfoByCity(String city);

	List<LKTYWJWeatherInfo> selectWeatherInfoByIP(String ip);
	
	List<LKTSJAlarmList> YWJAlarmType(Integer Time, Integer moduleID, Integer userID);
}
