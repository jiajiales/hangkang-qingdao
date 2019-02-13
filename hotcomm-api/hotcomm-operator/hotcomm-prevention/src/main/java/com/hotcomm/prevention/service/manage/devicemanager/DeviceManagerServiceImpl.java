package com.hotcomm.prevention.service.manage.devicemanager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.DevList;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.GroupList;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.GroupListDev;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.LogDc;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.LogHw;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.LogJg;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.LogLjt;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.LogPm;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.LogSj;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.LogSxdl;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.LogSy;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.LogSydl;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.MCLog;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.SXDYDevLogList;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.SelectKrqLog;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.SelectOnId;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.SeleteDevMap;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.YGLog;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.ChangeOwn;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.DevListVaule;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.MCLogParam;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.SXDYDevLogListVaule;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.SelectKrqLogParam;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.T_device_all;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.YGLogParam;
import com.hotcomm.prevention.db.mysql.manage.devicemanager.DeviceMapper;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.utils.ConverUtil;
import com.hotcomm.prevention.utils.TextUtils;

@Service
public class DeviceManagerServiceImpl implements DeviceManagerService {

	@Autowired
	private DeviceMapper devicemapper;

	/**
	 * 查询项目楼层图片
	 */
	@Override
	public List<SeleteDevMap> seleteDevMap(Integer groupid, Integer userid, Integer moduleid) throws MyException {
		return devicemapper.seleteDevMap(groupid, userid, moduleid);
	}

	/**
	 * 项目终端设备数查询
	 */
	@Override
	public Integer seleteGroupListDevnum(Integer moduleid, Integer userid, Integer groupid) throws MyException {
		return devicemapper.seleteGroupListDevnum(moduleid, userid, groupid);
	}

	/**
	 * 项目组平面图设备查看
	 */
	@Override
	public List<GroupListDev> GroupListDev(Integer moduleid, Integer groupid, String site) throws MyException {
		return devicemapper.GroupListDev(moduleid, groupid, site);
	}

	/**
	 * 查询设备列表
	 */
	@Override
	public Page<DevList> DevList(DevListVaule devListVaule) throws MyException {
		return devicemapper.DevList(devListVaule);
	}

	/**
	 * 删除设备
	 */
	@Transactional
	@Override
	public Integer DeleteDev(Integer moduleid, Integer devid) throws MyException {
		// 删除设备，项目组关联
		Integer code = devicemapper.DeleteDev(moduleid, devid);
		if (code <= 0) {
			throw new MyException("0", "删除失败");
		}
		// 删除设备与楼层关联
		devicemapper.DeleteDevPic(moduleid, devid);
		// 删除设备与视频关联
		devicemapper.cutDevVideoRelation(moduleid, devid);
		return code;
	}

	/**
	 * 新增设备
	 */
	@Transactional
	@Override
	public Integer AddDev(T_device_all t_device_all, Integer groupid, Integer itempicid, Integer[] videoid)
			throws MyException {
		// 查询编号或者mac是否唯一
		SelectOnId devnum = devicemapper.SelectOnId(t_device_all.getModuleid(), t_device_all.getUserid(), null,
				t_device_all.getDevnum(), null);
		if (devnum != null) {
			// 设备编号有重复
			return 201;
		}
		SelectOnId mac = devicemapper.SelectOnId(t_device_all.getModuleid(), t_device_all.getUserid(), null, null,
				t_device_all.getMac());
		if (mac != null) {
			// 设备mac有重复
			return 202;
		}
		// 插入设备表
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		t_device_all.setAddtime(df.format(day));
		Integer code = devicemapper.insertSelective(t_device_all);
		if (code <= 0) {
			throw new MyException("0", "新增失败");
		}
		// 新建设备与项目关联
		code = devicemapper.AddDevGroup(t_device_all.getId(), groupid, t_device_all.getModuleid(), df.format(day),
				t_device_all.getUserid());
		if (code <= 0) {
			throw new MyException("0", "新增失败");
		}
		// 新建设备与楼层关联
		if (itempicid != null) {
			code = devicemapper.AddDevGroupPic(itempicid, t_device_all.getId(), t_device_all.getModuleid(),
					df.format(day));
			if (code <= 0) {
				throw new MyException("0", "新增失败");
			}
		}
		// 新建设备与视频关联
		if (videoid != null && videoid.length > 0) {
			for (Integer vInteger : videoid) {
				code = devicemapper.AddDevVideo(t_device_all.getId(), vInteger, t_device_all.getModuleid());
			}
			if (code <= 0) {
				throw new MyException("0", "新增失败");
			}
		}
		return code;
	}

	/**
	 * 根据设备id查询设备
	 */
	@Override
	public SelectOnId SelectOnId(Integer moduleid, Integer userid, Integer devid) throws MyException {
		return devicemapper.SelectOnId(moduleid, userid, devid, null, null);
	}

	/**
	 * 查询可更换的项目组
	 */
	@Override
	public List<GroupList> GroupList(Integer userid, Integer moduleid) throws MyException {
		return devicemapper.GroupList(userid, moduleid);
	}

	/**
	 * 更换mac
	 */
	@Transactional
	@Override
	public Integer ChangeMac(String mac, Integer devid, Integer moduleid) throws MyException {
		// mac是否唯一
		SelectOnId mace = devicemapper.SelectOnId(moduleid, null, null, null, mac);
		if (mace != null && mace.getId() != devid) {
			// 设备mac有重复
			return 202;
		}
		return devicemapper.ChangeMac(mac, devid, moduleid);
	}

	/**
	 * 批量修改责任人
	 */
	@Transactional
	@Override
	public Integer ChangeOwn(ChangeOwn changeOwn) throws MyException {
		return devicemapper.ChangeOwn(changeOwn);
	}

	/**
	 * 修改设备数据
	 */
	@Transactional
	@Override
	public Integer UpdateDev(T_device_all t_device_all, Integer groupid, Integer itempicid, Integer[] videoid) {
		if (!TextUtils.isBlank(t_device_all.getDevnum())) {
			// 查询编号或者mac是否唯一
			SelectOnId devnum = devicemapper.SelectOnId(t_device_all.getModuleid(), t_device_all.getUserid(), null,
					t_device_all.getDevnum(), null);
			if (devnum != null && (int) devnum.getId() != (int) t_device_all.getId()) {
				// 设备编号有重复
				return 201;
			}
		}
		if (!TextUtils.isBlank(t_device_all.getMac())) {
			SelectOnId mac = devicemapper.SelectOnId(t_device_all.getModuleid(), t_device_all.getUserid(), null, null,
					t_device_all.getMac());
			if (mac != null && (int) mac.getId() != (int) t_device_all.getId()) {
				// 设备mac有重复
				return 202;
			}
		}
		// 修改设备数据
		Integer code = devicemapper.updateByPrimaryKeySelective(t_device_all);
		if (code <= 0) {
			throw new MyException("0", "修改失败");
		}
		// 修改设备与项目组关联
		if (groupid != null) {
			code = devicemapper.UpdateDevGroup(groupid, t_device_all.getId(), t_device_all.getModuleid());
			if (code <= 0) {
				throw new MyException("0", "修改失败");
			}
		}
		// 修改设备绑定楼层
		if (itempicid != null) {
			// 直接修改楼层
			code = devicemapper.UpdateDevGroupPic(itempicid, t_device_all.getId(), t_device_all.getModuleid());
			if (code <= 0) {
				// 说明该设备未绑定楼层，插入一条设备绑定楼层数据
				Date day = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				code = devicemapper.AddDevGroupPic(itempicid, t_device_all.getId(), t_device_all.getModuleid(),
						df.format(day));
			}
			if (code <= 0) {
				throw new MyException("0", "修改失败");
			}
		}
		// 修改设备关联视频
		if (videoid != null) {
			// 删除该设备与视频的所有关联
			code = devicemapper.UpdateDevGroupVideo(1, t_device_all.getId(), t_device_all.getModuleid(), null);
			for (Integer vInteger : videoid) {
				// 恢复该设备与视频存在被删除的关联
				code = devicemapper.UpdateDevGroupVideo(0, t_device_all.getId(), t_device_all.getModuleid(), vInteger);
				if (code <= 0) {
					// 该设备与视频不存在被删除的关联，新增关联
					code = devicemapper.AddDevVideo(t_device_all.getId(), vInteger, t_device_all.getModuleid());
				}
			}
			if (code <= 0) {
				throw new MyException("0", "修改失败");
			}
		}
		return code;
	}

	/**
	 * 可燃气运行日志
	 */
	@Override
	public PageInfo<SelectKrqLog> SelectKrqLog(SelectKrqLogParam selectKrqLogParam) throws MyException {
		PageHelper.startPage(selectKrqLogParam.getPageNum(), selectKrqLogParam.getPageSize());
		Page<SelectKrqLog> page = devicemapper.SelectKrqLog(selectKrqLogParam);
		List<SelectKrqLog> list = ConverUtil.converPage(page, SelectKrqLog.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	/**
	 * 烟感运行日志
	 */
	@Override
	public PageInfo<YGLog> selectYGLog(YGLogParam yGLogParam) throws MyException {
		PageHelper.startPage(yGLogParam.getPageNum(), yGLogParam.getPageSize());
		Page<YGLog> page = devicemapper.SelectYGLog(yGLogParam);
		List<YGLog> list = ConverUtil.converPage(page, YGLog.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	/**
	 * 门磁运行日志
	 */
	@Override
	public PageInfo<MCLog> SelectMCLog(MCLogParam mCLogParam) throws MyException {
		PageHelper.startPage(mCLogParam.getPageNum(), mCLogParam.getPageSize());
		Page<MCLog> page = devicemapper.SelectMCLog(mCLogParam);
		List<MCLog> list = ConverUtil.converPage(page, MCLog.class);
		return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	/**
	 * 三相电压运行日志
	 */
	@Override
	public Page<SXDYDevLogList> SelectSxdyDevLogPage(SXDYDevLogListVaule SXDYDevLogListVaule) throws MyException {
		return devicemapper.SelectSxdyDevLogPage(SXDYDevLogListVaule);
	}

	/**
	 * 三相电流运行日志
	 */
	@Override
	public Page<LogSxdl> SelectSxdlLog(String mac) throws MyException {
		return devicemapper.SelectSxdlLog(mac);
	}

	/**
	 * 剩余电流运行日志
	 */
	@Override
	public Page<LogSydl> SelectSydlLog(String mac) throws MyException {
		return devicemapper.SelectSydlLog(mac);
	}

	/**
	 * 水压运行日志
	 */
	@Override
	public Page<LogSy> SelectSyLog(String mac) throws MyException {
		return devicemapper.SelectSyLog(mac);
	}

	/**
	 * 井盖运行日志
	 */
	@Override
	public Page<LogJg> SelectJgLog(String mac) throws MyException {
		return devicemapper.SelectJgLog(mac);
	}

	/**
	 * 地磁运行日志
	 */
	@Override
	public Page<LogDc> SelectDcLog(String mac) throws MyException {
		return devicemapper.SelectDcLog(mac);
	}

	/**
	 * 红外运行日志
	 */
	@Override
	public Page<LogHw> SelectHwLog(String mac) throws MyException {
		Page<LogHw> a = devicemapper.SelectHwLog(mac);
		return a;
	}

	/**
	 * 垃圾桶运行日志
	 */
	@Override
	public Page<LogLjt> SelectLjtLog(String mac) throws MyException {
		return devicemapper.SelectLjtLog(mac);
	}

	/**
	 * 水浸运行日志
	 */
	@Override
	public Page<LogSj> SelectSjLog(String mac) throws MyException {
		return devicemapper.SelectSjLog(mac);
	}

	/**
	 * pm运行日志
	 */
	@Override
	public Page<LogPm> SelectPmLog(String mac) throws MyException {
		return devicemapper.SelectPmLog(mac);
	}
}
