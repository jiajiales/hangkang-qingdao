package com.hotcomm.prevention.db.sqlserver;

import java.util.List;

import com.hotcomm.prevention.bean.sqlserver.StPptnR;
import com.hotcomm.prevention.datasource.DBSource;

public interface StPptnRMapper{
	@DBSource(name="sqlserverDataSource")
	List<StPptnR> selectAll();

}
