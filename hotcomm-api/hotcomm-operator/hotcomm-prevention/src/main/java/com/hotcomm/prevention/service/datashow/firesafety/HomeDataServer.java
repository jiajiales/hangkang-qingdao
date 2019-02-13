package com.hotcomm.prevention.service.datashow.firesafety;

import java.util.List;

import com.hotcomm.prevention.bean.mysql.datashow.vo.AlarmIndexVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.AlarmTrendVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.DevInformVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.GroupDataVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.GroupForMapVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.OperationalDataOverviewVo;
import com.hotcomm.prevention.exception.MyException;

public interface HomeDataServer {

	/**
	 * 终端数据
	 * @param userid
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	GroupDataVo selectGroupData(Integer userid,String moduleid)throws MyException;
	/**
	 * 地图数据
	 * @param userid
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	List<GroupForMapVo> selectGroupForMap(Integer userid,String moduleid)throws MyException;
	/**
	 * 设备实时报警通知
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	List<DevInformVo> selectDevInform(String moduleid)throws MyException;
	/**
	 * 运行数据概况
	 * @return
	 * @throws MyException
	 */
	OperationalDataOverviewVo selectOperationalDataOverview()throws MyException;
	/**
	 * 设备报警趋势图
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	List<AlarmTrendVo> selectAlarmTrend(Integer type,String moduleid)throws MyException;
	
	/**
	 * 警情实时动态指数
	 * @return
	 * @throws MyException
	 */
	AlarmIndexVo selectAalarmIndex()throws MyException;
}
