package com.hotcomm.prevention.service.datashow.sj;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotcomm.prevention.bean.mysql.datashow.sj.AlarmTypeList;
import com.hotcomm.prevention.bean.mysql.datashow.sj.DevInfo;
import com.hotcomm.prevention.bean.mysql.datashow.sj.DevState;
import com.hotcomm.prevention.bean.mysql.datashow.sj.GroupListDev;
import com.hotcomm.prevention.bean.mysql.datashow.sj.SeleteMap;
import com.hotcomm.prevention.bean.mysql.datashow.sj.selectDev;
import com.hotcomm.prevention.bean.mysql.datashow.sj.vo.BatteryDiagramVO;
import com.hotcomm.prevention.bean.mysql.datashow.sj.vo.DevAgingRateVO;
import com.hotcomm.prevention.bean.mysql.datashow.sj.vo.DevAlarmHandleByTimeVO;
import com.hotcomm.prevention.bean.mysql.datashow.sj.vo.DevAlarmHandleTypeVO;
import com.hotcomm.prevention.bean.mysql.datashow.sj.vo.DevFailureRateVO;
import com.hotcomm.prevention.bean.mysql.datashow.sj.vo.DevOpenTimesVO;
import com.hotcomm.prevention.bean.mysql.datashow.sj.vo.DevStateCountVO;
import com.hotcomm.prevention.bean.mysql.datashow.sj.vo.DevStateVO;
import com.hotcomm.prevention.db.mysql.datashow.sj.WaterImmersionMapper;
import com.hotcomm.prevention.exception.MyException;

@Service
public class WaterImmersionServiceImpl implements WaterImmersionService {
	@Autowired
	private WaterImmersionMapper waterImmersionMapper;

	@Override
	public List<DevStateVO> selectDevState(Integer Time, Integer moduleID, Integer userID) throws MyException {
		List<DevStateVO> sonlist = waterImmersionMapper.selectDevState(Time, moduleID, userID);
		List<DevStateVO> father = new ArrayList<>();
		int[] i = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		for (int j = 0; j < i.length; j++) {
			DevStateVO son = new DevStateVO();
			son.setYaer(Time);
			son.setMth(i[j]);
			son.setAdd_devcount(0);
			son.setAlarmcount(0);
			son.setFalseCount(0);
			son.setModuleID(10);
			father.add(j, son);
			if (sonlist != null && sonlist.size() > 0) {
				for (int k = 0; k < sonlist.size(); k++) {
					if (sonlist.get(k).getMth() == i[j]) {
						father.set(j, sonlist.get(k));
					}
				}
			}
		}
		return father;
	}

	@Override
	public List<AlarmTypeList> AlarmHandleForType(Integer Time, Integer moduleID, Integer userID) throws MyException {

		return waterImmersionMapper.AlarmHandleForType(Time, moduleID, userID);

	}

	@Override
	public BatteryDiagramVO selectBattery(String starttime, String endtime, Integer moduleid, Integer userid)
			throws MyException {

		return waterImmersionMapper.selectBattery(starttime, endtime, moduleid, userid);

	}

	@Override
	public List<DevOpenTimesVO> selectDevOpenTimes(Integer moduleid, Integer querytype, Integer userid)
			throws MyException {

		return waterImmersionMapper.selectDevOpenTimes(moduleid, querytype, userid);
	}

	@Override
	public List<DevAgingRateVO> selectDevAgingRate(Integer moduleID, Integer userID) throws MyException {

		return waterImmersionMapper.selectDevAgingRate(moduleID, userID);
	}

	@Override
	public List<DevFailureRateVO> selectFailureRate(Integer moduleID, Integer userID, String startTime, String endTime)
			throws MyException {

		List<DevFailureRateVO> sonlist = waterImmersionMapper.selectFailureRate(moduleID, userID, startTime, endTime);
		List<DevFailureRateVO> father = new ArrayList<>();
		int[] i = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		for (int j = 0; j < i.length; j++) {
			DevFailureRateVO son = new DevFailureRateVO();
			son.setModuleID(moduleID);
			son.setCurrentMonthBrokenCount(0);
			son.setDate(i[j]);
			son.setRepairCount(0);
			son.setCumulativeBrokenCountAfterRepair(0);
			son.setDevCount(0);
			father.add(j, son);
			if (sonlist != null && sonlist.size() > 0) {
				for (int k = 0; k < sonlist.size(); k++) {
					if (sonlist.get(k).getDate() == i[j]) {
						father.set(j, sonlist.get(k));
					}
				}
			}
		}
		return father;

	}

	@Override
	public List<SeleteMap> SeleteMap(Integer groupid, Integer userid) throws MyException {
		return waterImmersionMapper.SeleteMap(groupid, userid);
	}

	@Override
	public DevInfo selectOneDevInfo(Integer year, Integer deviceid, Integer mouduleid, Integer userid)
			throws MyException {
		return waterImmersionMapper.selectOneDevInfo(year, deviceid, mouduleid, userid);
	}

	@Override
	public List<GroupListDev> GroupListDev(Integer moduleid, Integer groupid, String site) throws MyException {

		return waterImmersionMapper.GroupListDev(moduleid, groupid, site);
	}

	@Override
	public List<DevAlarmHandleByTimeVO> selectDevAlarmHandleByTime(Integer moduleID, Integer userID, String startTime,
			String endTime, Integer queryType) throws MyException {
		// if(queryType==3){
		// List<DevAlarmHandleByTimeVO> sonlist =
		// waterImmersionMapper.selectDevAlarmHandleByTime(moduleID, userID,
		// startTime, endTime, queryType);
		// List<DevAlarmHandleByTimeVO> father = new ArrayList<>();
		// String[] i = {
		// "01","02","03","04","05","06","07","08","09","10","11","12"};
		// for (int j = 0; j < i.length; j++) {
		// DevAlarmHandleByTimeVO son = new DevAlarmHandleByTimeVO();
		// son.setDate(i[j]);
		// son.setAlarmcount(0);
		// son.setHandlecount(0);
		// son.setModuleid(2);
		// son.setFalsecount(0);
		// father.add(j, son);
		// if (sonlist != null && sonlist.size() > 0) {
		// for (int k = 0; k < sonlist.size(); k++) {
		// if (sonlist.get(k).getDate().equals(i[j])) {
		// father.set(j, sonlist.get(k));
		// }
		// }
		// }
		// }
		// return father;}
		// else if(queryType==2){
		// List<DevAlarmHandleByTimeVO> list =
		// waterImmersionMapper.selectDevAlarmHandleByTime(moduleID, userID,
		// startTime, endTime, queryType);
		// if (list != null && list.size() > 0) {
		// for (int k = 0; k < list.size(); k++) {
		//
		//
		// if (list.get(k).getDate().equals(i[j])) {
		// father.set(j, sonlist.get(k));
		// }
		// }
		// }
		// }
		//
		return waterImmersionMapper.selectDevAlarmHandleByTime(moduleID, userID, startTime, endTime, queryType);

	}

	@Override
	public selectDev selectDev(Integer devid, Integer moduleid) throws MyException {
		return waterImmersionMapper.selectDev(devid, moduleid);
	}

	@Override
	public DevState AlarmList(Integer Time, Integer moduleID, Integer userID, Integer devid) throws MyException {
		return waterImmersionMapper.AlarmList(Time, moduleID, userID, devid);
	}

	@Override
	public List<DevAlarmHandleTypeVO> selectDevAlarmHandleType(Integer Time, Integer moduleID, Integer userID)
			throws MyException {
		return waterImmersionMapper.selectDevAlarmHandleType(Time, moduleID, userID);
	}

	@Override
	public DevStateCountVO selectDevStateCount(Integer moduleID, Integer userID, String startTime, String endTime)
			throws MyException {
		return waterImmersionMapper.selectDevStateCount(moduleID, userID, startTime, endTime);
	}

	@Override
	public List<Integer> selectDevList(Integer userid, Integer moduleid, String code) throws MyException {

		return waterImmersionMapper.selectDevList(userid, moduleid, code);
	}

}
