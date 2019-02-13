package com.hot.analysis.db.jg;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hot.analysis.bean.common.GroupListDev;
import com.hot.analysis.bean.jg.FlipNum;
import com.hot.analysis.bean.jg.JGTimeAlarmNum;
import com.hot.analysis.bean.jg.JGType;
import com.hot.analysis.bean.jg.JGTypequery;
import com.hot.analysis.bean.jg.ReasonNum;

public interface JgMapper {
	//翻盖频率统计
		
	List<FlipNum> selectFlipNum(@Param("startTime") Date startTime,@Param("endTime")  Date endTime, @Param("moduleid") Integer moduleid, 
			@Param("querytype")  Integer querytype,@Param("userid")  Integer userid);
	
	//井盖分类统计
	List<JGType> selectJgType(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("moduleid") Integer moduleid, @Param("userid") Integer userid);

	//报警时段统计
	List<JGTimeAlarmNum> selectJGTimeAlarmNum(@Param("startTime") Date startTime,@Param("endTime") Date endTime,@Param("moduleid") Integer moduleid,@Param("alarmtype") Integer alarmtype,
			@Param("userid") Integer userid);
     
	//维修原因统计
	List<ReasonNum> selectReasonNum(@Param("startTime") Date startTime,@Param("endTime") Date endTime,@Param("moduleid") Integer moduleid, @Param("pepairType") Integer pepairtype, @Param("userid")Integer userid);

	// 分类统计
	List<GroupListDev> GroupListDev(@Param("moduleid") Integer moduleid,@Param("groupid")  Integer groupid,@Param("site")  String  site,
			@Param("purpose") String[] purpose, @Param("loadbear") String[] loadbear);
	
	// 分类统计
//	List<GroupListDev> GroupListDev(@Param("jGTypequery") JGTypequery jGTypequery);

}
