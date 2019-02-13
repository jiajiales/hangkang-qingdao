package com.hot.analysis.service.jg;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hot.analysis.bean.common.GroupListDev;
import com.hot.analysis.bean.jg.FlipNum;
import com.hot.analysis.bean.jg.JGTimeAlarmNum;
import com.hot.analysis.bean.jg.JGType;
import com.hot.analysis.bean.jg.JGTypequery;
import com.hot.analysis.bean.jg.ReasonNum;

public interface JgService {

	//翻盖频率统计
	List<FlipNum> selectFlipNum(Date startTime, Date endTime, Integer moduleid, Integer querytype, Integer userid);
	
	//井盖分类统计
	Map<Integer, List<JGType>> selectJGType(Date startTime, Date endTime, Integer moduleid, Integer userid);
    
	//报警时段统计
	List<JGTimeAlarmNum> selectJGTimeAlarmNum(Date startTime, Date endTime, Integer moduleid, Integer alarmtype,
			Integer userid);
	
	//维修原因统计
	List<ReasonNum> selectReasonNum(Date startTime, Date endTime, Integer moduleid, Integer pepairtype,
			Integer userid);

	
	//分类统计
	List<GroupListDev> GroupListDev(Integer moduleid, Integer groupid, String site,
			String purpose, String loadbear);
	
	//分类统计
	List<GroupListDev> GroupListDev(JGTypequery jGTypequery);

}
