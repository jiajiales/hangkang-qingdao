package com.hotcomm.prevention.db.mysql.datashow.firesafety;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hotcomm.prevention.bean.mysql.datashow.vo.AlarmIndexVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.AlarmTrendVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.DevInformVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.GroupDataVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.GroupForMapVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.OperationalDataOverviewVo;
import com.hotcomm.prevention.exception.MyException;

public interface HomeDataMapper {

	/**
	 * 终端数据
	 * @param userid
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	GroupDataVo selectGroupData(@Param("userid")Integer userid,@Param("moduleid")String moduleid)throws MyException;
	
	/**
	 * 地图数据
	 * @param userid
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	List<GroupForMapVo> selectGroupForMap(@Param("userid")Integer userid,@Param("moduleid")String moduleid)throws MyException;
	
	/**
	 * 设备实时报警通知
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	List<DevInformVo> selectDevInform(@Param("moduleid")String moduleid)throws MyException;
	
	/**
	 * 运行数据概况1
	 * @return
	 * @throws MyException
	 */
	OperationalDataOverviewVo selectOperationalDataOverview()throws MyException;
	/**
	 * 运行数据概况2
	 * @return
	 * @throws MyException
	 */
	Double getAveHandleTime()throws MyException;
	
	/**
	 * 设备报警趋势图（1年）
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	List<AlarmTrendVo> selectAlarmTrendByYear(@Param("moduleid")String moduleid)throws MyException;
	/**
	 * 设备报警趋势图（1个月）
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	List<AlarmTrendVo> selectAlarmTrendByDay(@Param("moduleid")String moduleid)throws MyException;
	
	/**
	 * 警情实时动态指数
	 * @return
	 * @throws MyException
	 */
	AlarmIndexVo selectAalarmIndex()throws MyException;
}
