package com.hot.manage.service.jg;

import java.util.List;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.jg.JGChangeUser;
import com.hot.manage.entity.jg.JGDevAlarmHandleByTimeVO;
import com.hot.manage.entity.jg.JGDevCount;
import com.hot.manage.entity.jg.JGDevRate;
import com.hot.manage.entity.jg.LKTDevList;
import com.hot.manage.entity.jg.LKTGroupList;
import com.hot.manage.entity.jg.LKTGroupListDev;
import com.hot.manage.entity.jg.LKTJgDevNum;
import com.hot.manage.entity.jg.LKTJgItemList;
import com.hot.manage.entity.jg.LKTJgItemListMap;
import com.hot.manage.entity.jg.LKTSelectOnId;
import com.hot.manage.entity.jg.LKTSeleteMap;
import com.hot.manage.entity.jg.vaule.LKTDevListVaule;
import com.hot.manage.entity.jg.vaule.LKTJgAddDevVaule;
import com.hot.manage.entity.jg.vaule.LKTJgAddGroupVaule;
import com.hot.manage.entity.jg.vaule.LKTUpdateDevVaule;
import com.hot.manage.entity.jg.vaule.LKTUpdateItemVaule;
import com.hot.manage.entity.jg.vaule.Optionaluser;
import com.hot.manage.exception.MyException;

/**
 * @author Lktao
 *
 */
public interface LKTJGService {

	PageInfo<LKTDevList> LKTDevList(LKTDevListVaule lktDevListVaule) throws MyException;

	Integer LKTAddSignDevList(Integer moduleid, String devid, Integer patrolid) throws MyException;

	Integer LKTUpdateDev(LKTUpdateDevVaule lktUpdateDevVaule) throws MyException;

	List<LKTGroupList> LKTGroupList(Integer userid, Integer id, Integer moduleid, String groupname, String itemnum)
			throws MyException;

	Integer LKTDeleteDev(Integer moduleid, Integer devid) throws MyException;

	LKTJgDevNum LKTJgDevNum(Integer moduleid, Integer userid) throws MyException;

	PageInfo<LKTJgItemList> LKTJgItemList(LKTDevListVaule lktDevListVaule) throws MyException;

	Integer LKTDeleteItem(Integer id) throws MyException;

	Integer LKTUpdateItem(LKTUpdateItemVaule lktUpdateItemVaule) throws MyException;

	List<LKTGroupListDev> LKTGroupListDev(Integer moduleid, Integer groupid, String site) throws MyException;

	List<LKTJgItemListMap> LKTJgItemListMap(Integer moduleid, Integer userid) throws MyException;

	LKTJgDevNum LKTGroupListDevnum(Integer moduleid, Integer userid, Integer groupid) throws MyException;

	LKTSelectOnId LKTSelectOnIdpic(Integer moduleid, Integer userid, Integer devid, String devnum, String macAddr)
			throws MyException;

	Integer LKTJgAddDev(LKTJgAddDevVaule lktJgAddDevVaule) throws MyException;

	Integer LKTJgAddGroup(LKTJgAddGroupVaule lktJgAddGroupVaule) throws MyException;

	List<LKTSeleteMap> LKTSeleteMap(Integer groupid, Integer userid) throws MyException;

	List<Optionaluser> getUser(Integer userid) throws MyException;

	Integer updateDeviceOwn(JGChangeUser jgChangeUser);

	Integer LKTJGChangeDev(Integer devid, String mac) throws MyException;

	List<JGDevCount> selectCountBypurpose(Integer userid);

	List<JGDevCount> selectCountByloadbear(Integer userid);

	List<JGDevAlarmHandleByTimeVO> JGselectDevAlarmHandleByTime(Integer queryType, String startTime, String endTime,
			Integer userid);

	List<JGDevRate> selectDevRate(Integer queryType, String startTime, String endTime, Integer userid);
}
