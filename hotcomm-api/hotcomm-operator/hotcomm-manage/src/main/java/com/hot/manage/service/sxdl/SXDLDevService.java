package com.hot.manage.service.sxdl;

import java.util.List;
import com.github.pagehelper.Page;
import com.hot.manage.entity.sxdl.value.SXDLAddDevVaule;
import com.hot.manage.entity.sxdl.value.SXDLChangeUser;
import com.hot.manage.entity.sxdl.value.SXDLDevListVaule;
import com.hot.manage.entity.sxdl.value.SXDLUpdateDevVaule;
import com.hot.manage.entity.sxdl.SXDLAlarmingTrendVO;
import com.hot.manage.entity.sxdl.SXDLDevList;
import com.hot.manage.exception.MyException;
import com.hot.manage.entity.sxdl.SXDLSelectOnId;

public interface SXDLDevService {

	Page<SXDLDevList> SXDLDevList(SXDLDevListVaule SXDLDevListVaule) throws MyException;

	Integer SXDLUpdateDev(SXDLUpdateDevVaule SXDLUpdateDevVaule) throws MyException;

	Integer SXDLDeleteDev(Integer moduleid, Integer devid) throws MyException;

	List<SXDLSelectOnId> SXDLSelectOnId(Integer moduleid, Integer userid, Integer devid) throws MyException;

	Integer SXDLAddDev(SXDLAddDevVaule SXDLAddDevVaule);

	Integer SXDLChangeDev(SXDLUpdateDevVaule SXDLUpdateDevVaule);

	Integer changeDevOwn(SXDLChangeUser SXDLChangeUser);

	Integer LKTAddSignDevList(Integer moduleid, String devid, Integer patrolid) throws MyException;

	List<SXDLAlarmingTrendVO> AlarmingTrendForSXDL(Integer queryType, Integer userid, Integer groupid);

}
