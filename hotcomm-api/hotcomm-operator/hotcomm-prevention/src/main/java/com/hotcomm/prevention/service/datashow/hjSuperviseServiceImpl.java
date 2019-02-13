package com.hotcomm.prevention.service.datashow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotcomm.prevention.bean.mysql.datashow.vo.EnvironmentalMonitoringChart;
import com.hotcomm.prevention.bean.mysql.datashow.vo.OperationalDataOverview;
import com.hotcomm.prevention.bean.mysql.datashow.vo.PMAvgByCityVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.PMDevInfoVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.PMFloor;
import com.hotcomm.prevention.bean.mysql.datashow.vo.PMGroupDataVo;
import com.hotcomm.prevention.bean.mysql.datashow.vo.PMMonitoringPoint;
import com.hotcomm.prevention.bean.mysql.datashow.vo.TemperatureAndHumidityTrendChart;
import com.hotcomm.prevention.bean.mysql.datashow.vo.WeatherVo;
import com.hotcomm.prevention.db.mysql.datashow.hjSuperviseMapper;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.utils.CloneTools;

@Service
public class hjSuperviseServiceImpl implements hjSuperviseService {

	@Autowired
	private hjSuperviseMapper hjmapper;

	@Autowired
	private weatherService weather;

	@Override
	public PMGroupDataVo selectPMGroupData(Integer type, Integer userid) {
		// TODO Auto-generated method stub
		if (type != 0) {
			return hjmapper.selectPMGroupData(userid);
		} else {
			PMGroupDataVo data = hjmapper.selectPMGroupData(userid);
			data.setAlarmdevcount(data.getAlarmdevcount() * 3);
			data.setAlldevcount(data.getAlldevcount() * 3);
			data.setFaultdevcount(data.getFaultdevcount() * 3);
			data.setNormaldevcount(data.getNormaldevcount() * 3);
			return data;
		}
	}

	@Override
	public List<PMAvgByCityVo> selectPMByCity(Integer areaid, Integer userid) throws MyException {
		// TODO Auto-generated method stub
		List<PMAvgByCityVo> pmVo = hjmapper.selectPMAvgByCity(areaid);
		if (pmVo.size() == 0) {
			return null;
		}
		PMAvgByCityVo cityVo = hjmapper.selectPMAvgByAllCity(userid);
		if (cityVo == null) {
			cityVo = new PMAvgByCityVo();
			cityVo.setPm(0.0);
			cityVo.setPmall(0.0);
			cityVo.setNoiseval(0.0);
			cityVo.setNoisevalall(0.0);
		}
		for (PMAvgByCityVo result : pmVo) {
			WeatherVo weathervo = weather.selectTheWeatherByCityName(result.getAddvb());
			if (weathervo != null) {
				result = CloneTools.combineSydwCore(cityVo, result);
				result.setTemp(weathervo.getTemp());
				result.setWeather(weathervo.getWeather());
				result.setHumidity(weathervo.getHumidity());
				result.setQuality(weathervo.getQuality());
			}
		}
		return pmVo;
	}

	@Override
	public List<TemperatureAndHumidityTrendChart> selectTemperatureAndHumidityTrendChart(Integer type)
			throws MyException {
		// TODO Auto-generated method stub
		return hjmapper.selectTemperatureAndHumidityTrendChart(type);
	}

	@Override
	public List<EnvironmentalMonitoringChart> selectEnvironmentalMonitoringChart(Integer type) throws MyException {
		// TODO Auto-generated method stub
		return hjmapper.selectEnvironmentalMonitoringChart(type);
	}

	@Override
	public List<PMMonitoringPoint> selectPMMonitoringPoint() throws MyException {
		// TODO Auto-generated method stub
		return hjmapper.selectPMMonitoringPoint();
	}

	@Override
	public OperationalDataOverview selectOperationalDataOverview() throws MyException {
		// TODO Auto-generated method stub
		return hjmapper.selectOperationalDataOverview();
	}

	@Override
	public List<PMFloor> selectPMFloor(Integer groupid) throws MyException {
		// TODO Auto-generated method stub
		List<PMFloor> selectPMGroupFloor = hjmapper.selectPMGroupFloor(groupid);
		if (selectPMGroupFloor.size() == 0) {
			return null;
		}
		return hjmapper.selectPMGroupFloor(groupid);
	}

	@Override
	public List<PMDevInfoVo> selectPMDevInfo(Integer tipid) throws MyException {
		// TODO Auto-generated method stub
		List<PMDevInfoVo> selectPMDevInfo = hjmapper.selectPMDevInfo(tipid);
		if (selectPMDevInfo.size() == 0) {
			return null;
		}
		return hjmapper.selectPMDevInfo(tipid);
	}

}
