package com.hotcomm.prevention.service.data;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hotcomm.prevention.bean.mysql.common.entity.TStPptnR;
import com.hotcomm.prevention.bean.mysql.common.entity.TStRiverR;
import com.hotcomm.prevention.bean.mysql.common.entity.TStStbprpB;
import com.hotcomm.prevention.bean.mysql.common.vo.AlarmStationInTwoMinutes;
import com.hotcomm.prevention.db.mysql.data.MySQLDataMapper;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.utils.JSONUtil;
import com.hotcomm.prevention.websocket.MyWebSocket;
import com.hotcomm.prevention.websocket.MyWebSocket2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DataServiceImpl implements DataService {
	
	private Logger station_log = LoggerFactory.getLogger("station_log");
	private Logger rainfall_log = LoggerFactory.getLogger("rainfall_log");
	private Logger water_log = LoggerFactory.getLogger("water_log");
	

	@Autowired
	private MySQLDataMapper getDataMapper;


	@Override
	public Integer updateMySqlData(String info){
		if (info.contains("STNM")) {
			List<TStStbprpB> tStStbprpB =JSONUtil.forList(info, TStStbprpB.class);
			station_log.info("站点数据：");
			
			for (int i = 0; i < tStStbprpB.size(); i++) {
				getDataMapper.updateStStbprpB(tStStbprpB.get(i));
			}
			List<TStStbprpB> tStStbprpBMySQL = getDataMapper.selectTStStbprpB();
			for (int i = 0; i < tStStbprpB.size(); i++) {
				for (int j = 0; j < tStStbprpBMySQL.size(); j++) {
					if (tStStbprpB.get(i).getSTCD().equals(tStStbprpBMySQL.get(j).getSTCD())) {
						tStStbprpB.remove(i);
					}
				}
			}
			if (tStStbprpB.size()>0) {
				for (int i = 0; i < tStStbprpB.size(); i++) {
					getDataMapper.insertStStbprpBBatch(tStStbprpB.get(i));
				}
			}
			
		}
		if (info.contains("INTV")) {
			List<TStPptnR> tStPptnR =JSONUtil.forList(info, TStPptnR.class);
			rainfall_log.info("雨量数据：");
			getDataMapper.insertStPptnRBatch(tStPptnR);
		}
		if (info.contains("WPTN")&&info.contains("STCD")&&info.contains("XSMXV")) {
			List<TStRiverR> tStRiverR=JSONUtil.forList(info, TStRiverR.class);
			water_log.info("水位数据：");
			getDataMapper.insertStRiverRBatch(tStRiverR);
		}
		return 0;
	}


	@Override
	public String checkAlarmStationInTwoMinutes() throws MyException {
		List<AlarmStationInTwoMinutes> info=getDataMapper.checkAlarmStationInTwoMinutes();
		if (info!=null) {
			String sss = "{\"systate\":0,\"message\":" + JSONUtil.toJson(info) + "}";
			MyWebSocket.sendMessageToAll(sss);
			MyWebSocket2.sendMessageToAll(sss);
			System.out.println(sss);
			return sss;
		}
		return null;
	}

}
