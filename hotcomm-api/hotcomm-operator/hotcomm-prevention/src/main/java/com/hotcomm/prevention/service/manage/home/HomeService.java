package com.hotcomm.prevention.service.manage.home;

import java.util.List;
import com.hotcomm.prevention.bean.mysql.manage.home.HomeAlarmTrend;
import com.hotcomm.prevention.bean.mysql.manage.home.HomeDataForDay;
import com.hotcomm.prevention.bean.mysql.manage.home.HomeTotalData;
import com.hotcomm.prevention.bean.mysql.manage.home.HomeWoTrend;
import com.hotcomm.prevention.exception.MyException;

public interface HomeService {
	HomeTotalData selectTotalData(Integer userid, Integer module) throws MyException;

	List<HomeAlarmTrend> selectAlarmTrend(Integer userid, Integer type, Integer module) throws MyException;

	List<HomeWoTrend> selectWoTrend(Integer userid, Integer type, Integer module) throws MyException;

	List<HomeDataForDay> selectDataForDay(Integer userid, Integer module) throws MyException;
}
