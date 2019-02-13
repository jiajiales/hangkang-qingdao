package com.hot.manage.db.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hot.manage.entity.common.home.HomeAlarmTrend;
import com.hot.manage.entity.common.home.HomeDataForDay;
import com.hot.manage.entity.common.home.HomeTotalData;
import com.hot.manage.entity.common.home.HomeWoTrend;

public interface HomeMapper {

	/**
	 * 首页，设备数据，报警数据，工单统计，巡检等数据
	 * 
	 * @param userid
	 * @return
	 */
	HomeTotalData selectTotalData(@Param("userid") Integer userid);

	/**
	 * 报警趋势
	 * 
	 * @param userid
	 * @param type
	 *            查询类型，1：最近7天，2：最近一个月
	 * @return
	 */
	List<HomeAlarmTrend> selectAlarmTrend(@Param("userid") Integer userid, @Param("type") Integer type);

	/**
	 * 工单趋势
	 * 
	 * @param userid
	 * @param type
	 *            查询类型，1：最近7天，2：最近一个月
	 * @return
	 */
	List<HomeWoTrend> selectWoTrend(@Param("userid") Integer userid, @Param("type") Integer type);
	
	/**
	 * 查询当天的报警、工单数据
	 * @param userid
	 * @return
	 */
	List<HomeDataForDay> selectDataForDay(@Param("userid") Integer userid);
}
