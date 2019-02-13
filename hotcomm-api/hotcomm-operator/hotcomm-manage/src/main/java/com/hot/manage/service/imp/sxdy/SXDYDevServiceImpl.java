package com.hot.manage.service.imp.sxdy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.hot.manage.db.sxdy.SXDYDevMapper;
import com.hot.manage.entity.sxdy.SXDYAlarmingTrendVO;
import com.hot.manage.entity.sxdy.SXDYDevList;
import com.hot.manage.entity.sxdy.SXDYSelectOnId;
import com.hot.manage.entity.sxdy.value.SXDYAddDevVaule;
import com.hot.manage.entity.sxdy.value.SXDYChangeUser;
import com.hot.manage.entity.sxdy.value.SXDYDevListVaule;
import com.hot.manage.entity.sxdy.value.SXDYUpdateDevVaule;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.sxdy.SXDYDevService;

@Transactional
@Service
public class SXDYDevServiceImpl implements SXDYDevService {

	@Autowired
	private SXDYDevMapper SXDYDevMapper;

	@Override
	public Page<SXDYDevList> SXDYDevList(SXDYDevListVaule SXDYDevListVaule) throws MyException {
		Page<SXDYDevList> page = SXDYDevMapper.SXDYDevList(SXDYDevListVaule);
		return page;
	}

	@Override
	public Integer SXDYUpdateDev(SXDYUpdateDevVaule SXDYUpdateDevVaule) throws MyException {
		if (SXDYUpdateDevVaule.getDevnum() != null) {
			List<SXDYSelectOnId> devnum = SXDYDevMapper.SXDYSelectOnId(SXDYUpdateDevVaule.getModuleid(),
					SXDYUpdateDevVaule.getUserid(), null, SXDYUpdateDevVaule.getDevnum(), null);
			if (devnum.size() > 0 && devnum.get(0).getId()!=SXDYUpdateDevVaule.getDevid()) {
					return 201;
			}
		}
		Integer code = SXDYDevMapper.SXDYUpdateDev(SXDYUpdateDevVaule);
		if (code >= 1&&SXDYUpdateDevVaule.getItempicid()!=null) {
			
			code = SXDYDevMapper.SXDYUpdateDevAddPic(SXDYUpdateDevVaule);
		}
		if (SXDYUpdateDevVaule.getVideoid() != null) {
			String[] v = SXDYUpdateDevVaule.getVideoid().split(",");
			// 删除所有关联
			code = SXDYDevMapper.SXDYUpdateDevVideoDel(SXDYUpdateDevVaule.getModuleid(),
					SXDYUpdateDevVaule.getDevid());
			for (int i = 0; i < v.length; i++) {
				// 如果还需要该关联，修改回来
				code = SXDYDevMapper.SXDYUpdateDevVideo(SXDYUpdateDevVaule.getModuleid(),
						SXDYUpdateDevVaule.getDevid(), Integer.parseInt(v[i]));
				// 如果需要新增的关联不存在，会修改行数为0,code=0
				if (code <= 0) {
					code = SXDYDevMapper.SXDYUpdateDevVideoAdd(SXDYUpdateDevVaule.getModuleid(),
							SXDYUpdateDevVaule.getDevid(), Integer.parseInt(v[i]));
				}
			}
		}
		return code;
	}

	@Override
	public Integer SXDYDeleteDev(Integer moduleid, Integer devid) throws MyException {
		SXDYDevMapper.SXDYDeleteDevVideo(moduleid, devid);
		SXDYDevMapper.SXDYDeleteDevPic(moduleid, devid);
		return SXDYDevMapper.SXDYDeleteDev(moduleid, devid);
	}

	@Override
	public List<SXDYSelectOnId> SXDYSelectOnId(Integer moduleid, Integer userid, Integer devid) throws MyException {
		return SXDYDevMapper.SXDYSelectOnId(moduleid, userid, devid, null, null);
	}

	@Override
	public Integer SXDYAddDev(SXDYAddDevVaule SXDYAddDevVaule) {
		List<SXDYSelectOnId> devnum = SXDYDevMapper.SXDYSelectOnId(SXDYAddDevVaule.getModuleid(),
				SXDYAddDevVaule.getUserid(), null, SXDYAddDevVaule.getDevnum(), null);
		if (devnum.size() != 0) {
			return 201;
		}
		List<SXDYSelectOnId> macAddr = SXDYDevMapper.SXDYSelectOnId(SXDYAddDevVaule.getModuleid(),
				SXDYAddDevVaule.getUserid(), null, null, SXDYAddDevVaule.getMacAddr());
		if (macAddr.size() != 0) {
			return 202;
		}
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SXDYAddDevVaule.setAddtime(df.format(day));
		Integer code = SXDYDevMapper.SXDYAddDev(SXDYAddDevVaule);
		if (code > 0) {
			code = SXDYDevMapper.SXDYAddDevGroup(SXDYAddDevVaule);
		}
		if (code > 0 & SXDYAddDevVaule.getItempicid() != null) {
			code = SXDYDevMapper.SXDYAddDevGroupPic(SXDYAddDevVaule);
		}
		if (SXDYAddDevVaule.getVideoid() != null) {
			String[] v = SXDYAddDevVaule.getVideoid().split(",");
			for (int i = 0; i < v.length; i++) {
				code = SXDYDevMapper.SXDYAddDevVideo(SXDYAddDevVaule.getModuleid(), SXDYAddDevVaule.getId(),
						Integer.parseInt(v[i]));
			}
		}
		return code;
	}

	@Override
	public Integer SXDYChangeDev(SXDYUpdateDevVaule SXDYUpdateDevVaule) {
		SXDYDevList list = SXDYDevMapper.SXDYChangeDevMac(SXDYUpdateDevVaule.getMac());
		if (list != null && SXDYUpdateDevVaule.getDevid()!=list.getDevid()) {
			return 201;
		}
		return SXDYDevMapper.SXDYChangeDev(SXDYUpdateDevVaule);
	}

	@Override
	public Integer changeDevOwn(SXDYChangeUser SXDYChangeUser) {

		return SXDYDevMapper.changeDevOwn(SXDYChangeUser);
	}

	@Override
	public Integer LKTAddSignDevList(Integer moduleid, String devid, Integer patrolid) throws MyException {
		return SXDYDevMapper.LKTAddSignDevList(moduleid, devid, patrolid);
	}

	@Override
	public List<SXDYAlarmingTrendVO> AlarmingTrendForSXDY(Integer queryType, Integer userid, Integer groupid) {
		return SXDYDevMapper.AlarmingTrendForSXDY(queryType, userid, groupid);
	}

}
