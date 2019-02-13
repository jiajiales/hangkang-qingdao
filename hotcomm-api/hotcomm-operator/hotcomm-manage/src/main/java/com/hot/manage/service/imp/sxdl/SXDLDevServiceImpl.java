package com.hot.manage.service.imp.sxdl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.hot.manage.db.sxdl.SXDLDevMapper;
import com.hot.manage.entity.sxdl.SXDLAlarmingTrendVO;
import com.hot.manage.entity.sxdl.SXDLDevList;
import com.hot.manage.entity.sxdl.SXDLSelectOnId;
import com.hot.manage.entity.sxdl.value.SXDLAddDevVaule;
import com.hot.manage.entity.sxdl.value.SXDLChangeUser;
import com.hot.manage.entity.sxdl.value.SXDLDevListVaule;
import com.hot.manage.entity.sxdl.value.SXDLUpdateDevVaule;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.sxdl.SXDLDevService;

@Transactional
@Service
public class SXDLDevServiceImpl implements SXDLDevService {

	@Autowired
	private SXDLDevMapper SXDLDevMapper;

	@Override
	public Page<SXDLDevList> SXDLDevList(SXDLDevListVaule SXDLDevListVaule) throws MyException {
		Page<SXDLDevList> page = SXDLDevMapper.SXDLDevList(SXDLDevListVaule);
		return page;
	}

	@Override
	public Integer SXDLUpdateDev(SXDLUpdateDevVaule SXDLUpdateDevVaule) throws MyException {
		if (SXDLUpdateDevVaule.getDevnum() != null) {
			List<SXDLSelectOnId> devnum = SXDLDevMapper.SXDLSelectOnId(SXDLUpdateDevVaule.getModuleid(),
					SXDLUpdateDevVaule.getUserid(), null, SXDLUpdateDevVaule.getDevnum(), null);
			if (devnum.size() > 0 && devnum.get(0).getId()!=SXDLUpdateDevVaule.getDevid()) {
					return 201;
			}
		}
		Integer code = SXDLDevMapper.SXDLUpdateDev(SXDLUpdateDevVaule);
		if (code >= 1) {
			code = SXDLDevMapper.SXDLUpdateDevAddPic(SXDLUpdateDevVaule);
		}
		if (SXDLUpdateDevVaule.getVideoid() != null) {
			String[] v = SXDLUpdateDevVaule.getVideoid().split(",");
			// 删除所有关联
			code = SXDLDevMapper.SXDLUpdateDevVideoDel(SXDLUpdateDevVaule.getModuleid(),
					SXDLUpdateDevVaule.getDevid());
			for (int i = 0; i < v.length; i++) {
				// 如果还需要该关联，修改回来
				code = SXDLDevMapper.SXDLUpdateDevVideo(SXDLUpdateDevVaule.getModuleid(),
						SXDLUpdateDevVaule.getDevid(), Integer.parseInt(v[i]));
				// 如果需要新增的关联不存在，会修改行数为0,code=0
				if (code <= 0) {
					code = SXDLDevMapper.SXDLUpdateDevVideoAdd(SXDLUpdateDevVaule.getModuleid(),
							SXDLUpdateDevVaule.getDevid(), Integer.parseInt(v[i]));
				}
			}
		}
		return code;
	}

	@Override
	public Integer SXDLDeleteDev(Integer moduleid, Integer devid) throws MyException {
		SXDLDevMapper.SXDLDeleteDevVideo(moduleid, devid);
		SXDLDevMapper.SXDLDeleteDevPic(moduleid, devid);
		return SXDLDevMapper.SXDLDeleteDev(moduleid, devid);
	}

	@Override
	public List<SXDLSelectOnId> SXDLSelectOnId(Integer moduleid, Integer userid, Integer devid) throws MyException {
		return SXDLDevMapper.SXDLSelectOnId(moduleid, userid, devid, null, null);
	}

	@Override
	public Integer SXDLAddDev(SXDLAddDevVaule SXDLAddDevVaule) {
		List<SXDLSelectOnId> devnum = SXDLDevMapper.SXDLSelectOnId(SXDLAddDevVaule.getModuleid(),
				SXDLAddDevVaule.getUserid(), null, SXDLAddDevVaule.getDevnum(), null);
		if (devnum.size() != 0) {
			return 201;
		}
		List<SXDLSelectOnId> macAddr = SXDLDevMapper.SXDLSelectOnId(SXDLAddDevVaule.getModuleid(),
				SXDLAddDevVaule.getUserid(), null, null, SXDLAddDevVaule.getMacAddr());
		if (macAddr.size() != 0) {
			return 202;
		}
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SXDLAddDevVaule.setAddtime(df.format(day));
		Integer code = SXDLDevMapper.SXDLAddDev(SXDLAddDevVaule);
		if (code > 0) {
			code = SXDLDevMapper.SXDLAddDevGroup(SXDLAddDevVaule);
		}
		if (code > 0 & SXDLAddDevVaule.getItempicid() != null) {
			code = SXDLDevMapper.SXDLAddDevGroupPic(SXDLAddDevVaule);
		}
		if (SXDLAddDevVaule.getVideoid() != null) {
			String[] v = SXDLAddDevVaule.getVideoid().split(",");
			for (int i = 0; i < v.length; i++) {
				code = SXDLDevMapper.SXDLAddDevVideo(SXDLAddDevVaule.getModuleid(), SXDLAddDevVaule.getId(),
						Integer.parseInt(v[i]));
			}
		}
		return code;
	}

	@Override
	public Integer SXDLChangeDev(SXDLUpdateDevVaule SXDLUpdateDevVaule) {
		SXDLDevList list = SXDLDevMapper.SXDLChangeDevMac(SXDLUpdateDevVaule.getMac());
		if (list != null  && SXDLUpdateDevVaule.getDevid()!=list.getDevid()) {
			return 201;
		}
		return SXDLDevMapper.SXDLChangeDev(SXDLUpdateDevVaule);
	}

	@Override
	public Integer changeDevOwn(SXDLChangeUser SXDLChangeUser) {

		return SXDLDevMapper.changeDevOwn(SXDLChangeUser);
	}

	@Override
	public Integer LKTAddSignDevList(Integer moduleid, String devid, Integer patrolid) throws MyException {
		return SXDLDevMapper.LKTAddSignDevList(moduleid, devid, patrolid);
	}

	@Override
	public List<SXDLAlarmingTrendVO> AlarmingTrendForSXDL(Integer queryType, Integer userid, Integer groupid) {
		return SXDLDevMapper.AlarmingTrendForSXDL(queryType, userid, groupid);
	}

}
