package com.hot.manage.service.sydl;

import java.util.List;
import com.github.pagehelper.Page;
import com.hot.manage.entity.sydl.SYDLAlarmingTrendVO;
import com.hot.manage.entity.sydl.SYDLChangeUser;
import com.hot.manage.entity.sydl.SYDLDevList;
import com.hot.manage.entity.sydl.SYDLGroupList;
import com.hot.manage.entity.sydl.SYDLSelectOnId;
import com.hot.manage.entity.sydl.value.SYDLAddDevValue;
import com.hot.manage.entity.sydl.value.SYDLDevListValue;
import com.hot.manage.entity.sydl.value.SYDLUpdateDevVaule;
import com.hot.manage.exception.MyException;

public interface SYDLService {
	// 查询设备列表
	Page<SYDLDevList> SYDLDevList(SYDLDevListValue p);

	// 新增设备
	Integer SYDLAddDev(SYDLAddDevValue sYDLAddDevValue) throws MyException;

	// 删除设备
	Integer SYDLDeleteDev(Integer moduleid, Integer devid) throws MyException;

	// 修改设备
	Integer SYDLUpdateDev(SYDLUpdateDevVaule sYDLUpdateDevVaule);

	// 加入到签到列表
	Integer SYDLAddSignDevList(Integer moduleid, String devid, Integer userid, Integer patrolid);

	// 查询可更换的项目组
	List<SYDLGroupList> SYDLGroupList(Integer userid, Integer id, Integer moduleid, String groupname, String itemnum)
			throws MyException;

	// 根据设备id查询设备
	List<SYDLSelectOnId> SYDLSelectOnId(Integer moduleid, Integer userid, Integer devid, String devnum, String macAddr);

	// 更换设备
	Integer SYDLChangeDev(SYDLUpdateDevVaule sYDLUpdateDevVaule);

	// 批量修改责任人
	Integer SYDLchangeDevOwn(SYDLChangeUser sYDLChangeUser);

	List<SYDLAlarmingTrendVO> AlarmingTrendForSYDL(Integer queryType, Integer userid, Integer groupid);

}
