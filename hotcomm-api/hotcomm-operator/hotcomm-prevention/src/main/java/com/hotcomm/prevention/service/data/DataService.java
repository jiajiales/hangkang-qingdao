package com.hotcomm.prevention.service.data;


import java.util.List;

import com.hotcomm.prevention.bean.mysql.common.vo.AlarmStationInTwoMinutes;
import com.hotcomm.prevention.exception.MyException;

public interface DataService {

	Integer updateMySqlData(String info) throws MyException;
	
	String checkAlarmStationInTwoMinutes() throws MyException;
	
}
