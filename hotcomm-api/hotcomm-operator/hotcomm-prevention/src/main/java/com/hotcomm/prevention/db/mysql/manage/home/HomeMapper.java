package com.hotcomm.prevention.db.mysql.manage.home;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hotcomm.prevention.bean.mysql.manage.home.HomeAlarmTrend;
import com.hotcomm.prevention.bean.mysql.manage.home.HomeDataForDay;
import com.hotcomm.prevention.bean.mysql.manage.home.HomeTotalData;
import com.hotcomm.prevention.bean.mysql.manage.home.HomeWoTrend;

public interface HomeMapper {

	/**
	 * 首页，设备数据，报警数据，工单统计，巡检等数据
	 * 
	 * @param userid
	 * @return
	 */
	HomeTotalData selectTotalData(@Param("userid") Integer userid, @Param("module") Integer module);

	/**
	 * 报警趋势
	 * 
	 * @param userid
	 * @param type
	 *            查询类型，1：最近7天，2：最近一个月
	 * @return
	 */
	List<HomeAlarmTrend> selectAlarmTrend(@Param("userid") Integer userid, @Param("type") Integer type,
			@Param("module") Integer module);

	/**
	 * 工单趋势
	 * 
	 * @param userid
	 * @param type
	 *            查询类型，1：最近7天，2：最近一个月
	 * @return
	 */
	List<HomeWoTrend> selectWoTrend(@Param("userid") Integer userid, @Param("type") Integer type,
			@Param("module") Integer module);

	/**
	 * 查询当天的报警、工单数据
	 * 
	 * @param userid
	 * @return
	 */
	List<HomeDataForDay> selectDataForDay(@Param("userid") Integer userid, @Param("module") Integer module);
}
