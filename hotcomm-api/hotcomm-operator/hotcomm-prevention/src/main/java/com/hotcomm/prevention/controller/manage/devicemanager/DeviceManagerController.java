package com.hotcomm.prevention.controller.manage.devicemanager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.DevList;
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
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.YGLog;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.ChangeOwn;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.DevListVaule;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.MCLogParam;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.SXDYDevLogListVaule;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.SelectKrqLogParam;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.T_device_all;
import com.hotcomm.prevention.bean.mysql.manage.devicemanager.vaule.YGLogParam;
import com.hotcomm.prevention.exception.MyException;
import com.hotcomm.prevention.service.manage.devicemanager.DeviceManagerService;
import com.hotcomm.prevention.utils.ApiResult;
import com.hotcomm.prevention.utils.ConverUtil;

@RestController
@RequestMapping("devicemanager")
public class DeviceManagerController {

	@Autowired
	private DeviceManagerService deviceManagerService;

	/*
	 * 查询项目组楼层图片
	 */
	@PostMapping("/seleteDevMap")
	public ApiResult seleteDevMap(Integer groupid, Integer userid, Integer moduleid) throws MyException {
		return ApiResult.resultInfo("1", "成功", deviceManagerService.seleteDevMap(groupid, userid, moduleid));
	}

	/*
	 * 项目终端设备数查询
	 */
	@PostMapping("/seleteGroupListDevnum")
	public ApiResult seleteGroupListDevnum(Integer moduleid, Integer userid, Integer groupid) throws MyException {
		Integer devnum = deviceManagerService.seleteGroupListDevnum(moduleid, userid, groupid);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("devnum", devnum);
		return ApiResult.resultInfo("1", "成功", map);
	}

	/*
	 * 项目组平面图设备查看
	 */
	@PostMapping("/groupListDev")
	public ApiResult GroupListDev(Integer moduleid, Integer groupid, String site) throws MyException {
		return ApiResult.resultInfo("1", "成功", deviceManagerService.GroupListDev(moduleid, groupid, site));
	}

	/*
	 * 查询设备列表
	 */
	@PostMapping("/devList")
	public ApiResult DevList(DevListVaule devListVaule) throws MyException {
		PageHelper.startPage(devListVaule.getPageNum(), devListVaule.getPageSize());
		Page<DevList> page = deviceManagerService.DevList(devListVaule);
		List<DevList> list = ConverUtil.converPage(page, DevList.class);
		PageInfo<DevList> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		return ApiResult.resultInfo("1", "成功", pageinfo);
	}

	/*
	 * 删除设备
	 */
	@PostMapping("/deleteDev")
	public ApiResult DeleteDev(Integer moduleid, Integer devid) throws MyException {
		return ApiResult.resultInfo("1", "成功", deviceManagerService.DeleteDev(moduleid, devid));
	}

	/*
	 * 新增设备
	 */
	@PostMapping("/AddDev")
	public ApiResult AddDev(T_device_all t_device_all, Integer groupid, Integer itempicid, Integer[] videoid)
			throws MyException {
		ApiResult resultInfo = null;
		Integer insertObject = deviceManagerService.AddDev(t_device_all, groupid, itempicid, videoid);
		if (insertObject == 201) {
			resultInfo = ApiResult.resultInfo("0", "设备编号已存在！！", null);
		} else if (insertObject == 202) {
			resultInfo = ApiResult.resultInfo("0", "设备mac已存在！！", null);
		} else if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "新增失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "新增成功！！", null);
		}
		return resultInfo;
	}

	/*
	 * 根据设备id查询设备
	 */
	@PostMapping("/SelectOnId")
	public ApiResult SelectOnId(Integer moduleid, Integer userid, Integer devid) throws MyException {
		return ApiResult.resultInfo("1", "成功！！", deviceManagerService.SelectOnId(moduleid, userid, devid));
	}

	/*
	 * 查询可更换的项目组
	 */
	@PostMapping("/GroupList")
	public ApiResult GroupList(Integer userid, Integer moduleid) throws MyException {
		return ApiResult.resultInfo("1", "成功！！", deviceManagerService.GroupList(userid, moduleid));
	}

	/*
	 * 更换设备mac
	 */
	@PostMapping("/ChangeMac")
	public ApiResult ChangeMac(String mac, Integer devid, Integer moduleid) throws MyException {
		ApiResult resultInfo = null;
		Integer insertObject = deviceManagerService.ChangeMac(mac, devid, moduleid);
		if (insertObject == 202) {
			resultInfo = ApiResult.resultInfo("0", "设备mac已存在！！", null);
		} else if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "修改失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "修改成功！！", null);
		}
		return resultInfo;
	}

	/*
	 * 批量修改责任人
	 */
	@PostMapping("/ChangeOwn")
	public ApiResult ChangeOwn(ChangeOwn changeOwn) throws MyException {
		Integer code = deviceManagerService.ChangeOwn(changeOwn);
		if (code <= 0) {
			return ApiResult.resultInfo("1", "失败！！", null);
		}
		return ApiResult.resultInfo("1", "成功！！", null);
	}

	/*
	 * 修改设备
	 */
	@PostMapping("/UpdateDev")
	public ApiResult UpdateDev(T_device_all t_device_all, Integer groupid, Integer itempicid, Integer[] videoid)
			throws MyException {
		ApiResult resultInfo = null;
		Integer insertObject = deviceManagerService.UpdateDev(t_device_all, groupid, itempicid, videoid);
		if (insertObject == 201) {
			resultInfo = ApiResult.resultInfo("0", "设备编号已存在！！", null);
		} else if (insertObject == 202) {
			resultInfo = ApiResult.resultInfo("0", "设备mac已存在！！", null);
		} else if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "修改失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "修改成功！！", null);
		}
		return resultInfo;
	}

	/*
	 * 可燃气运行日志
	 */
	@PostMapping("/SelectKrqLog")
	public ApiResult SelectKrqLog(SelectKrqLogParam p) throws MyException {
		return ApiResult.resultInfo("1", "成功!!", deviceManagerService.SelectKrqLog(p));
	}

	/*
	 * 烟感运行日志
	 */
	@PostMapping("/SelectYGLog")
	public ApiResult SelectYGLog(YGLogParam yGLogParam) {
		PageInfo<YGLog> list = deviceManagerService.selectYGLog(yGLogParam);
		return ApiResult.resultInfo("1", "成功", list);
	}

	/*
	 * 门磁运行日志
	 */
	@PostMapping("/SelectMCLog")
	public ApiResult SelectMCLog(MCLogParam mCLogParam) {
		PageInfo<MCLog> list = deviceManagerService.SelectMCLog(mCLogParam);
		return ApiResult.resultInfo("1", "成功", list);
	}

	/*
	 * 三相电压运行日志
	 */
	@PostMapping("/SelectSxdyDevLogPage")
	public ApiResult SelectSxdyDevLogPage(SXDYDevLogListVaule p) throws MyException {
		PageHelper.startPage(p.getPageNum(), p.getPageSize());
		Page<SXDYDevLogList> page = deviceManagerService.SelectSxdyDevLogPage(p);
		List<SXDYDevLogList> list = ConverUtil.converPage(page, SXDYDevLogList.class);
		PageInfo<SXDYDevLogList> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(),
				list);
		return ApiResult.resultInfo("1", "成功", pageinfo);
	}

	/*
	 * 三相电流运行日志
	 */
	@PostMapping("/SelectSxdlLog")
	public ApiResult SelectSxdlLog(String mac, Integer pageNum, Integer pageSize) throws MyException {
		PageHelper.startPage(pageNum, pageSize);
		Page<LogSxdl> page = deviceManagerService.SelectSxdlLog(mac);
		if (page.getResult() == null) {
			return ApiResult.resultInfo("1", "成功", null);
		}
		List<LogSxdl> list = ConverUtil.converPage(page, LogSxdl.class);
		PageInfo<LogSxdl> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		return ApiResult.resultInfo("1", "成功!!", pageinfo);
	}

	/*
	 * 剩余电流运行日志
	 */
	@PostMapping("/SelectSydlLog")
	public ApiResult SelectSydlLog(String mac, Integer pageNum, Integer pageSize) throws MyException {
		PageHelper.startPage(pageNum, pageSize);
		Page<LogSydl> page = deviceManagerService.SelectSydlLog(mac);
		if (page.getResult() == null) {
			return ApiResult.resultInfo("1", "成功", null);
		}
		List<LogSydl> list = ConverUtil.converPage(page, LogSydl.class);
		PageInfo<LogSydl> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		return ApiResult.resultInfo("1", "成功!!", pageinfo);
	}

	/*
	 * 水压运行日志
	 */
	@PostMapping("/SelectSyLog")
	public ApiResult SelectSyLog(String mac, Integer pageNum, Integer pageSize) throws MyException {
		PageHelper.startPage(pageNum, pageSize);
		Page<LogSy> page = deviceManagerService.SelectSyLog(mac);
		if (page.getResult() == null) {
			return ApiResult.resultInfo("1", "成功", null);
		}
		List<LogSy> list = ConverUtil.converPage(page, LogSy.class);
		PageInfo<LogSy> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		return ApiResult.resultInfo("1", "成功!!", pageinfo);
	}

	/*
	 * 井盖运行日志
	 */
	@PostMapping("/SelectJgLog")
	public ApiResult SelectJgLog(String mac, Integer pageNum, Integer pageSize) throws MyException {
		PageHelper.startPage(pageNum, pageSize);
		Page<LogJg> page = deviceManagerService.SelectJgLog(mac);
		if (page.getResult() == null) {
			return ApiResult.resultInfo("1", "成功", null);
		}
		List<LogJg> list = ConverUtil.converPage(page, LogJg.class);
		PageInfo<LogJg> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		return ApiResult.resultInfo("1", "成功!!", pageinfo);
	}

	/*
	 * 地磁运行日志
	 */
	@PostMapping("/SelectDcLog")
	public ApiResult SelectDcLog(String mac, Integer pageNum, Integer pageSize) throws MyException {
		PageHelper.startPage(pageNum, pageSize);
		Page<LogDc> page = deviceManagerService.SelectDcLog(mac);
		if (page.getResult() == null) {
			return ApiResult.resultInfo("1", "成功", null);
		}
		List<LogDc> list = ConverUtil.converPage(page, LogDc.class);
		PageInfo<LogDc> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		return ApiResult.resultInfo("1", "成功!!", pageinfo);
	}

	/*
	 * 红外运行日志
	 */
	@PostMapping("/SelectHwLog")
	public ApiResult SelectHwLog(String mac, Integer pageNum, Integer pageSize) throws MyException {
		PageHelper.startPage(pageNum, pageSize);
		Page<LogHw> page = deviceManagerService.SelectHwLog(mac);
		if (page.getResult() == null) {
			return ApiResult.resultInfo("1", "成功", null);
		}
		List<LogHw> list = ConverUtil.converPage(page, LogHw.class);
		PageInfo<LogHw> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		return ApiResult.resultInfo("1", "成功!!", pageinfo);
	}

	/*
	 * 垃圾桶运行日志
	 */
	@PostMapping("/SelectLjtLog")
	public ApiResult SelectLjtLog(String mac, Integer pageNum, Integer pageSize) throws MyException {
		PageHelper.startPage(pageNum, pageSize);
		Page<LogLjt> page = deviceManagerService.SelectLjtLog(mac);
		if (page.getResult() == null) {
			return ApiResult.resultInfo("1", "成功", null);
		}
		List<LogLjt> list = ConverUtil.converPage(page, LogLjt.class);
		PageInfo<LogLjt> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		return ApiResult.resultInfo("1", "成功!!", pageinfo);
	}

	/*
	 * 水浸运行日志
	 */
	@PostMapping("/SelectSjLog")
	public ApiResult SelectSjLog(String mac, Integer pageNum, Integer pageSize) throws MyException {
		PageHelper.startPage(pageNum, pageSize);
		Page<LogSj> page = deviceManagerService.SelectSjLog(mac);
		if (page.getResult() == null) {
			return ApiResult.resultInfo("1", "成功", null);
		}
		List<LogSj> list = ConverUtil.converPage(page, LogSj.class);
		PageInfo<LogSj> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		return ApiResult.resultInfo("1", "成功!!", pageinfo);
	}

	/*
	 * pm运行日志
	 */
	@PostMapping("/SelectPmLog")
	public ApiResult SelectPmLog(String mac, Integer pageNum, Integer pageSize) throws MyException {
		PageHelper.startPage(pageNum, pageSize);
		Page<LogPm> page = deviceManagerService.SelectPmLog(mac);
		if (page.getResult() == null) {
			return ApiResult.resultInfo("1", "成功", null);
		}
		List<LogPm> list = ConverUtil.converPage(page, LogPm.class);
		PageInfo<LogPm> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		return ApiResult.resultInfo("1", "成功!!", pageinfo);
	}
}
