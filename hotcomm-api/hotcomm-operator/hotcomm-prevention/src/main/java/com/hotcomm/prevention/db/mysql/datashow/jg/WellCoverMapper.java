package com.hotcomm.prevention.db.mysql.datashow.jg;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hotcomm.prevention.bean.mysql.datashow.jg.AreaVO;
import com.hotcomm.prevention.bean.mysql.datashow.jg.FlipNum;
import com.hotcomm.prevention.bean.mysql.datashow.jg.GroupInfoVO;
import com.hotcomm.prevention.bean.mysql.datashow.jg.JGTimeAlarmNum;
import com.hotcomm.prevention.bean.mysql.datashow.jg.ReasonNum;
import com.hotcomm.prevention.bean.mysql.datashow.jg.WellCoverSpreadVO;
import com.hotcomm.prevention.bean.mysql.datashow.jg.WellCoverType;
import com.hotcomm.prevention.bean.mysql.datashow.sj.GroupListDev;

public interface WellCoverMapper {

	List<FlipNum> selectFlipNum(@Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("moduleid") Integer moduleid, @Param("querytype") Integer querytype,
			@Param("userid") Integer userid);

	List<WellCoverType> selectJgType(@Param("moduleid") Integer moduleid, @Param("queryType") Integer queryType,
			@Param("userid") Integer userid);

	List<JGTimeAlarmNum> selectJGTimeAlarmNum(@Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("moduleid") Integer moduleid, @Param("alarmtype") Integer alarmtype,
			@Param("userid") Integer userid);

	List<ReasonNum> selectReasonNum(@Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("moduleid") Integer moduleid, @Param("pepairType") Integer pepairType,
			@Param("userid") Integer userid);

	List<WellCoverSpreadVO> GroupListDev(@Param("moduleid") Integer moduleid, @Param("groupid") Integer groupid,
			@Param("site") String site, @Param("purpose") String[] purpose, @Param("loadbear") String[] loadbear,@Param("code")  String code,@Param("areaid") Integer areaid);

	List<WellCoverSpreadVO>  wellCoverSpread(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid, @Param("groupid")  Integer groupid,@Param("code") String code);

	List<AreaVO> selectArea(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid);

	List<GroupInfoVO> selectGroup(@Param("moduleid") Integer moduleid,@Param("areaid")  Integer areaid,@Param("userid")  Integer userid);

}
