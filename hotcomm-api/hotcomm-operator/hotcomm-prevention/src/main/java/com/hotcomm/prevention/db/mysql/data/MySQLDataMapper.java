package com.hotcomm.prevention.db.mysql.data;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hotcomm.prevention.bean.mysql.common.entity.TStPptnR;
import com.hotcomm.prevention.bean.mysql.common.entity.TStRiverR;
import com.hotcomm.prevention.bean.mysql.common.entity.TStStbprpB;
import com.hotcomm.prevention.bean.mysql.common.vo.AlarmStationInTwoMinutes;
import com.hotcomm.prevention.bean.mysql.common.vo.LatestUpDateMySqlTimeVo;

public interface MySQLDataMapper {

	LatestUpDateMySqlTimeVo getLatestTime();

	Integer deleteTStPptnRByTime(@Param("time") String time);

	Integer insertStPptnRBatch(@Param("info") List<TStPptnR> info);
	
	Integer deleteStRiverRByTime(@Param("time") String time);

	Integer insertStRiverRBatch(@Param("info") List<TStRiverR> info);
	
	Integer updateStStbprpB(@Param("info") TStStbprpB info);
	
	List<TStStbprpB> selectTStStbprpB();
	
	Integer insertStStbprpBBatch(@Param("info") TStStbprpB info);
	
	Integer insertStPptnRBatchJava(@Param("info") TStPptnR info);
	
	Integer insertStRiverRBatchJava(@Param("info") TStRiverR info);
	
	Integer deleteSt();
	
	List<AlarmStationInTwoMinutes> checkAlarmStationInTwoMinutes();

}
