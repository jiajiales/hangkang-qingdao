package com.hot.manage.service.sxdy;

import java.util.List;


import com.github.pagehelper.Page;
import com.hot.manage.entity.sxdy.value.SXDYAddDevVaule;
import com.hot.manage.entity.sxdy.value.SXDYChangeUser;
import com.hot.manage.entity.sxdy.value.SXDYDevListVaule;
import com.hot.manage.entity.sxdy.value.SXDYUpdateDevVaule;
import com.hot.manage.entity.sxdy.SXDYAlarmingTrendVO;
import com.hot.manage.entity.sxdy.SXDYDevList;
import com.hot.manage.exception.MyException;
import com.hot.manage.entity.sxdy.SXDYSelectOnId;

public interface SXDYDevService {

	Page<SXDYDevList> SXDYDevList(SXDYDevListVaule SXDYDevListVaule) throws MyException;

	Integer SXDYUpdateDev(SXDYUpdateDevVaule SXDYUpdateDevVaule) throws MyException;

	Integer SXDYDeleteDev(Integer moduleid, Integer devid) throws MyException;

	List<SXDYSelectOnId> SXDYSelectOnId(Integer moduleid, Integer userid, Integer devid) throws MyException;

	Integer SXDYAddDev(SXDYAddDevVaule SXDYAddDevVaule);

	Integer SXDYChangeDev(SXDYUpdateDevVaule SXDYUpdateDevVaule);

	Integer changeDevOwn(SXDYChangeUser SXDYChangeUser);

	Integer LKTAddSignDevList(Integer moduleid, String devid, Integer patrolid) throws MyException;

	List<SXDYAlarmingTrendVO> AlarmingTrendForSXDY(Integer queryType, Integer userid, Integer groupid);
}
