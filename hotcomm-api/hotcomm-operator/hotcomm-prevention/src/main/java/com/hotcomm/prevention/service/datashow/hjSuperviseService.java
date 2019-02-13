package com.hotcomm.prevention.service.datashow;

import java.util.List;

import com.hotcomm.prevention.bean.mysql.datashow.vo.EnvironmentalMonitoringChart;
import com.hotcomm.prevention.bean.mysql.datashow.vo.OperationalDataOverview;
import com.hotcomm.prevention.bean.mysql.datashow.vo.PMAvgByCityVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.PMDevInfoVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.PMFloor;
import com.hotcomm.prevention.bean.mysql.datashow.vo.PMGroupDataVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.PMMonitoringPoint;
import com.hotcomm.prevention.bean.mysql.datashow.vo.TemperatureAndHumidityTrendChart;
import com.hotcomm.prevention.exception.MyException;

public interface hjSuperviseService {

	/**
	 * PM设备统计
	 * 
	 * @param type
	 * @param userid
	 * @return
	 */
	PMGroupDataVo selectPMGroupData(Integer type, Integer userid);

	/**
	 * 城市PM值统计
	 * 
	 * @return
	 * @throws MyException
	 */
	List<PMAvgByCityVo> selectPMByCity(Integer areaid, Integer userid) throws MyException;

	/**
	 * 温湿度趋势图
	 * 
	 * @param type
	 *            1 /天 2/月
	 * @return
	 * @throws MyException
	 */
	List<TemperatureAndHumidityTrendChart> selectTemperatureAndHumidityTrendChart(Integer type) throws MyException;

	/**
	 * 环境检测图
	 * 
	 * @param type
	 *            1 /天 2/月
	 * @return
	 * @throws MyException
	 */
	List<EnvironmentalMonitoringChart> selectEnvironmentalMonitoringChart(Integer type) throws MyException;

	/**
	 * pm监测点
	 * 
	 * @return
	 * @throws MyException
	 */
	List<PMMonitoringPoint> selectPMMonitoringPoint() throws MyException;

	/**
	 * 运行数据概况
	 * 
	 * @return
	 * @throws MyException
	 */
	OperationalDataOverview selectOperationalDataOverview() throws MyException;

	/**
	 * PM楼层信息
	 * 
	 * @param groupid
	 *            项目组
	 * @return
	 * @throws MyException
	 */
	List<PMFloor> selectPMFloor(Integer groupid) throws MyException;

	/**
	 * PM设备信息
	 * 
	 * @param tipid
	 *            楼层id
	 * @return
	 * @throws MyException
	 */
	List<PMDevInfoVo> selectPMDevInfo(Integer tipid) throws MyException;
}
