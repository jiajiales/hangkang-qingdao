package com.hot.analysis.service.yg;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hot.analysis.bean.common.TDeviceYg;
import com.hot.analysis.bean.yg.AlarmHandle;
import com.hot.analysis.bean.yg.GroupInfo;
import com.hot.analysis.bean.yg.SomkeAlarmNum;
import com.hot.analysis.db.yg.SomkeAlarmNumMapper;

@Transactional
@Service
public class SomkeDataAnalysisServiceImpl implements ISomkeDataAnalysisService {

	@Autowired
	private SomkeAlarmNumMapper somkeAlarmNumMapper;

	/**
	 * 报警记录、报警类型统计
	 */
	@Override
	public List<SomkeAlarmNum> selectSomkeAlarmNums(Integer Time, Integer moduleID, Integer userID) {
		List<SomkeAlarmNum> list = somkeAlarmNumMapper.selectSomkeAlarmNums(Time, moduleID, userID);
		return list;
	}

	/**
	 * 报警趋势图
	 */
	@Override
	public List<AlarmHandle> selectAlarmHandle(Date startTime, Date endTime, Integer moduleid, Integer querytype,
			Integer userid) {
		List<AlarmHandle> list = somkeAlarmNumMapper.selectAlarmHandle(startTime, endTime, moduleid, querytype, userid);
		return list;
	}

	/**
	 * 单个设备查询
	 */
	@Override
	public TDeviceYg selectDevById(Integer Id) {
		return somkeAlarmNumMapper.selectDevById(Id);
	}

	/**
	 * 当前组下的所有设备的列表
	 */
	@Override
	public List<TDeviceYg> selectYgListByGroupId(Integer groupid, Integer moduleid) {
		return somkeAlarmNumMapper.selectYgListByGroupId(groupid, moduleid);
	}

	/**
	 * 设备信息列表
	 */
	@Override
	public List<TDeviceYg> selectYgList(Integer userid, Integer moduleid, String code) {
		return somkeAlarmNumMapper.selectYgList(userid,moduleid,code);
	}

	/**
	 * 全部组的信息列表
	 */
	@Override
	public List<GroupInfo> selectGroupInfoList(Integer userid, Integer moduleid) {
		return somkeAlarmNumMapper.selectGroupInfoList(userid, moduleid);
	}

	/**
	 * 按组名字查询组信息
	 */
	@Override
	public List<GroupInfo> selectGroupInfoByName(Integer userid, Integer moduleid, String name) {
		String groupName = "%" + name + "%";
		System.out.println(groupName);
		return somkeAlarmNumMapper.selectGroupInfoByName(userid, moduleid, groupName);
	}
}
