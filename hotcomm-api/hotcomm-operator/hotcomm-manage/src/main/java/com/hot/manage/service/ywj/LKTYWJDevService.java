package com.hot.manage.service.ywj;

import java.util.List;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.ywj.AlarmingTrendVO;
import com.hot.manage.entity.ywj.LKTYWJDevList;
import com.hot.manage.entity.ywj.LKTYWJDevNum;
import com.hot.manage.entity.ywj.LKTYWJGroupList;
import com.hot.manage.entity.ywj.LKTYWJGroupListDev;
import com.hot.manage.entity.ywj.LKTYWJSelectOnId;
import com.hot.manage.entity.ywj.LKTYWJSeleteMap;
import com.hot.manage.entity.ywj.vaule.LKTYWJAddDevVaule;
import com.hot.manage.entity.ywj.vaule.LKTYWJDevListVaule;
import com.hot.manage.entity.ywj.vaule.LKTYWJUpdateDevVaule;
import com.hot.manage.exception.MyException;

public interface LKTYWJDevService {

	PageInfo<LKTYWJDevList> LKTYWJDevList(LKTYWJDevListVaule lktywjDevListVaule) throws MyException;

	Integer LKTYWJAddSignDevList(Integer moduleid, String devid, Integer patrolid) throws MyException;

	Integer LKTYWJUpdateDev(LKTYWJUpdateDevVaule lktywjUpdateDevVaule) throws MyException;

	List<LKTYWJGroupList> LKTYWJGroupList(Integer userid, Integer moduleid) throws MyException;

	Integer LKTYWJDeleteDev(Integer moduleid, Integer devid) throws MyException;

	List<LKTYWJGroupListDev> LKTYWJGroupListDev(Integer moduleid, Integer groupid, String site) throws MyException;

	LKTYWJDevNum LKTYWJGroupListDevnum(Integer moduleid, Integer userid, Integer groupid) throws MyException;

	List<LKTYWJSelectOnId> LKTYWJSelectOnId(Integer moduleid, Integer userid, Integer devid) throws MyException;

	List<LKTYWJSeleteMap> LKTYWJSeleteMap(Integer groupid, Integer userid) throws MyException;

	Integer LKTYWJAddDev(LKTYWJAddDevVaule lktywjAddDevVaule) throws MyException;

	Integer changeOwn(String devid, Integer ownId) throws MyException;

	Integer LKTAddSignDevList(Integer moduleid, String devid, Integer patrolid);

	// 修改mac
	Integer changeMac(Integer devid, String mac) throws MyException;
	
	List<AlarmingTrendVO> AlarmingTrendForWhichHasBoundaryValue(Integer queryType,Integer userid,Integer groupid);
}
