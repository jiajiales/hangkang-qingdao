package com.hot.manage.service.common.dev;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hot.manage.db.common.DeviceMapper;
import com.hot.manage.db.common.DevresrelationMapper;
import com.hot.manage.db.common.alarm.AlarmMapper;
import com.hot.manage.entity.common.AlarmHandleNums;
import com.hot.manage.entity.common.AlarmNums;
import com.hot.manage.entity.common.AllDevAndGroupByUserId;
import com.hot.manage.entity.common.AllDevByUserid;
import com.hot.manage.entity.common.AllGroupByUserId;
import com.hot.manage.entity.common.DevOptionalUser;
import com.hot.manage.entity.common.DeviceForDevnum;
import com.hot.manage.entity.common.DeviceHandleTime;
import com.hot.manage.entity.common.DeviceInfo;
import com.hot.manage.entity.common.DeviceInsertParam;
import com.hot.manage.entity.common.DeviceType;
import com.hot.manage.entity.common.GroupInfo;
import com.hot.manage.entity.common.OptionalGroup;
import com.hot.manage.entity.common.T_devresrelation;
import com.hot.manage.entity.common.alarm.AlarmDev;
import com.hot.manage.exception.MyException;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

	@Autowired
	private DeviceMapper deviceMapper;

	@Autowired
	private AlarmMapper alarmMapper;

	@Autowired
	private DevresrelationMapper devresrelationMapper;

	@Override
	public DeviceInfo selectAPPDeviceInfo(Integer deviceid, Integer moduleid) throws MyException {
		// TODO Auto-generated method stub
		DeviceInfo deviceInfo = deviceMapper.AppDeviceInfo(deviceid, moduleid);
		if (deviceInfo == null) {
			return null;
		}
		List<DeviceHandleTime> handleTime = deviceMapper.getDeviceAlarmHandleTime(deviceid, moduleid);
		if (handleTime.size() == 0) {
			handleTime = null;
		}
		deviceInfo.setHandleTime(handleTime);
		return deviceInfo;
	}

	@Override
	public Integer insertAppDevice(Integer moduleid, Integer userid, DeviceInsertParam deviceInsertParam)
			throws MyException {

		DeviceForDevnum devnum = this.selectAppDeviceForDevnum(deviceInsertParam.getDevnum());
		if (devnum != null) {
			getresult(0, "该设备已存在,请勿重复添加");
		}
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		deviceInsertParam.setAddtime(sdf.format(d));
		Integer i = deviceMapper.insertAPPDevice(moduleid, userid, deviceInsertParam);
		getresult(i, "添加设备失败");
		Integer devid = deviceInsertParam.getInsertId();
		if (deviceInsertParam.getPictureUrl() != null) {
			for (String picture : deviceInsertParam.getPictureUrl()) {
				T_devresrelation record = new T_devresrelation();
				record.setModuleid(moduleid);
				record.setAddtime(sdf.format(d));
				record.setDevid(deviceInsertParam.getInsertId());
				record.setDevid(devid);
				record.setUrl(picture);
				getresult(devresrelationMapper.insertSelective(record), "添加图片失败");
			}
		}
		i = deviceMapper.insertDevReGroup(devid, deviceInsertParam.getGroupid(), moduleid,
				deviceInsertParam.getAddtime(), userid);
		getresult(i, "添加设备与组的关联失败");
		if (deviceInsertParam.getVideoid() != null) {
			for (Integer videoid : deviceInsertParam.getVideoid()) {
				i = deviceMapper.insertDevReVideo(devid, videoid, moduleid);
				getresult(i, "添加摄像头失败");
			}
		}
		if (deviceInsertParam.getItempicid() != null) {
			i = deviceMapper.insertDevItemPic(deviceInsertParam.getItempicid(), devid, moduleid,
					deviceInsertParam.getAddtime());
		}
		return i;
	}

	/**
	 * 判断是否成功
	 * 
	 * @param i
	 * @param msg
	 * @return
	 * @throws MyException
	 */
	private Integer getresult(Integer i, String msg) throws MyException {
		if (i <= 0) {
			throw new MyException("0", msg);
		} else {
			return i;
		}
	}

	@Override
	public List<OptionalGroup> selectOpaitonalGroup(Integer userid) throws MyException {
		List<OptionalGroup> selectOptionalGroup = deviceMapper.selectOptionalGroup(userid);
		return selectOptionalGroup;
	}

	@Override
	public List<DevOptionalUser> selectOpationalUserid(Integer userid) throws MyException {
		List<DevOptionalUser> selectOpationalUser = deviceMapper.selectDevOptionalUser(userid);
		return selectOpationalUser;
	}

	/**
	 * 查询设备责任人，用来推送报警信息
	 */
	@Override
	public AlarmDev selectDevForUser(Integer deviceid, Integer moduleid) throws MyException {
		return deviceMapper.selectDevForUser(deviceid, moduleid);
	}

	@Override
	public DeviceForDevnum selectAppDeviceForDevnum(String devnum) throws MyException {
		// TODO Auto-generated method stub
		alarmMapper.dropTemporaryTable();
		alarmMapper.createTemporaryTable();
		DeviceForDevnum deviceForDevnum = deviceMapper.selectAppDeviceforDevnum(devnum);
		if (deviceForDevnum == null) {
			deviceForDevnum = null;
		} else {
			deviceForDevnum.setDevtype("智能" + deviceForDevnum.getName());
		}
		return deviceForDevnum;
	}

	@Override
	public List<DeviceType> selectAppDeviceType(Integer moduleid) throws MyException {
		// TODO Auto-generated method stub
		return deviceMapper.selectAppDeviceType(moduleid);
	}

	@Override
	public List<AlarmNums> selectAlarmNums(Integer moduleID, Integer userid,Integer queryType) {
		List<AlarmNums> list=deviceMapper.selectAlarmNums(moduleID,userid,queryType);
		return list;
	}

	@Override
	public List<AlarmHandleNums> selectAlarmHandleNums(Integer moduleID, Integer userid,Integer queryType) {
		List<AlarmHandleNums> list=deviceMapper.selectAlarmHandleNums(moduleID,userid,queryType);
		
		return list;
	}

	@Override
	public List<AlarmHandleNums> selectGroupAlarmHandleNums(Integer moduleID, Integer userid, Integer queryType,
			Integer groupid) {
		
		return deviceMapper.selectGroupAlarmHandleNums(moduleID,userid,queryType,groupid);
	}

	@Override
	public List<AlarmNums> selectGroupAlarmNums(Integer moduleID, Integer userid, Integer queryType, Integer groupid) {
		return deviceMapper.selectGroupAlarmNums(moduleID,userid,queryType,groupid);
	}

	@Override
	public GroupInfo selectGroupInfo(Integer moduleid, Integer groupid) {
		return deviceMapper.selectGroupInfo(moduleid,groupid);
	}
	
	@Override
	public List<AllGroupByUserId> selectAllGroupByUserId(Integer moduleid, Integer userid) {
		return deviceMapper.selectAllGroupByUserId(moduleid, userid);
	}

	@Override
	public List<AllDevByUserid> selectAllDevByUserid(Integer moduleid, Integer userid) {
		return deviceMapper.selectAllDevByUserid(moduleid, userid);
	}

	@Override
	public List<AllDevAndGroupByUserId> selectAllDevAndGroupByUserId(Integer moduleid, Integer userid) {
		List<AllDevAndGroupByUserId> test = new ArrayList<>();
		List<AllGroupByUserId> group = deviceMapper.selectAllGroupByUserId(moduleid, userid);
		List<AllDevByUserid> dev = deviceMapper.selectAllDevByUserid(moduleid, userid);
		AllDevAndGroupByUserId all = new AllDevAndGroupByUserId();
		all.setAllDevByUserid(dev);
		all.setAllGroupByUserId(group);
		test.add(all);
		return test;
	}

	@Override
	public DeviceForDevnum selectAppDeviceForMac(String mac) throws MyException {
		// TODO Auto-generated method stub
		alarmMapper.dropTemporaryTable();
		alarmMapper.createTemporaryTable();
		DeviceForDevnum deviceForDevnum = deviceMapper.selectAppDeviceforMac(mac);
		if (deviceForDevnum == null) {
			deviceForDevnum = null;
		} else {
			deviceForDevnum.setDevtype("智能" + deviceForDevnum.getName());
		}
		return deviceForDevnum;
	}
}
