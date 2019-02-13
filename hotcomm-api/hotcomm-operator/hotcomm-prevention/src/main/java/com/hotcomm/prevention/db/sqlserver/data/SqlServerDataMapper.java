package com.hotcomm.prevention.db.sqlserver.data;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hotcomm.prevention.bean.mysql.common.entity.TStPptnR;
import com.hotcomm.prevention.bean.mysql.common.entity.TStRiverR;
import com.hotcomm.prevention.bean.mysql.common.entity.TStStbprpB;
import com.hotcomm.prevention.datasource.DBSource;

public interface SqlServerDataMapper{
	
	@DBSource(name="sqlserverDataSource")
	List<TStStbprpB> selectTStStbprpBAll();
	
	@DBSource(name="sqlserverDataSource")
	List<TStPptnR> selectTStPptnRByTime(@Param("time") String time);
	
	@DBSource(name="sqlserverDataSource")
	List<TStRiverR> selectTStRiverRByTime(@Param("time") String time);
	
}
