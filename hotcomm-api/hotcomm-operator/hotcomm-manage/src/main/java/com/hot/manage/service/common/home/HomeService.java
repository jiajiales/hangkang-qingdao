package com.hot.manage.service.common.home;

import java.util.List;
import com.hot.manage.entity.common.home.HomeAlarmTrend;
import com.hot.manage.entity.common.home.HomeDataForDay;
import com.hot.manage.entity.common.home.HomeTotalData;
import com.hot.manage.entity.common.home.HomeWoTrend;
import com.hot.manage.exception.MyException;

public interface HomeService {
	HomeTotalData selectTotalData(Integer userid) throws MyException;

	List<HomeAlarmTrend> selectAlarmTrend(Integer userid, Integer type) throws MyException;

	List<HomeWoTrend> selectWoTrend(Integer userid, Integer type) throws MyException;

	List<HomeDataForDay> selectDataForDay(Integer userid) throws MyException;
}
