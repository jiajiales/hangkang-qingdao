package com.hotcomm.prevention.db.mysql.datashow;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hotcomm.prevention.bean.mysql.datashow.vo.EnvironmentalMonitoringChart;
import com.hotcomm.prevention.bean.mysql.datashow.vo.OperationalDataOverview;
import com.hotcomm.prevention.bean.mysql.datashow.vo.PMAvgByCityVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.PMDevInfoVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.PMFloor;
import com.hotcomm.prevention.bean.mysql.datashow.vo.PMGroupDataVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.PMMonitoringPoint;
import com.hotcomm.prevention.bean.mysql.datashow.vo.TemperatureAndHumidityTrendChart;
import com.hotcomm.prevention.exception.MyException;

public interface hjSuperviseMapper {

	/**
	 * PM设备数据
	 * 
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	PMGroupDataVo selectPMGroupData(@Param("userid") Integer userid) throws MyException;

	/**
	 * 城市Pm值统计
	 * 
	 * @return
	 * @throws MyException
	 */
	List<PMAvgByCityVo> selectPMAvgByCity(@Param("areaid") Integer areaid) throws MyException;

	/**
	 * 全市PM平均值
	 * 
	 * @return
	 * @throws MyException
	 */
	PMAvgByCityVo selectPMAvgByAllCity(@Param("userid") Integer userid) throws MyException;

	/**
	 * 温湿度趋势图
	 * 
	 * @param type
	 *            1/天 2/月
	 * @return
	 * @throws MyException
	 */
	List<TemperatureAndHumidityTrendChart> selectTemperatureAndHumidityTrendChart(@Param("type") Integer type)
			throws MyException;

	/**
	 * 环境检测图
	 * 
	 * @param type
	 *            1/天 2/月
	 * @return
	 * @throws MyException
	 */
	List<EnvironmentalMonitoringChart> selectEnvironmentalMonitoringChart(@Param("type") Integer type)
			throws MyException;

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
	List<PMFloor> selectPMGroupFloor(@Param("groupid") Integer groupid) throws MyException;

	/**
	 * PM设备信息
	 * 
	 * @param tipid
	 *            楼层id
	 * @return
	 * @throws MyException
	 */
	List<PMDevInfoVo> selectPMDevInfo(@Param("tipid") Integer tipid) throws MyException;
}
