package com.hot.manage.controller.sy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.annotation.Permissions;
import com.hot.manage.controller.PageController;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.sy.SYChangeUser;
import com.hot.manage.entity.sy.SYDevList;
import com.hot.manage.entity.sy.value.SYAddDevValue;
import com.hot.manage.entity.sy.value.SYDevListValue;
import com.hot.manage.entity.sy.value.SYUpdateDevVaule;
import com.hot.manage.entity.video.DevRelationVideoParam;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.sy.SYService;
import com.hot.manage.service.video.VideoService;
import com.hot.manage.utils.ApiResult;
import com.hot.manage.utils.ConverUtil;

@RestController
public class SYDeviceController implements PageController<SYDevListValue, SYDevList> {

	@Autowired
	private VideoService videoService;

	@Autowired
	private SYService sYService;

	// 设备列表数据
	@PostMapping("/sy/SYDevList")
	@Permissions("sy:SYDevList:query")
	@Override
	public ApiResult page(SYDevListValue p) throws MyException {
		if (p.getModuleid() == null || p.getUserid() == null || p.getPageNum() == null || p.getPageNum() == null) {
			throw new MyException("0", "必填字段请勿留空！！");
		}
		PageHelper.startPage(p.getPageNum(), p.getPageSize());
		Page<SYDevList> page = sYService.SYDevList(p);
		List<SYDevList> list = ConverUtil.converPage(page, SYDevList.class);
		PageInfo<SYDevList> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		return ApiResult.resultInfo("1", "成功!!", pageinfo);
	}

	// 新增设备
	@PostMapping("/sy/SYAddDev")
	@Permissions("sy:SYAddDev:add")
	public ApiResult SYAddDev(SYAddDevValue sYAddDevValue) {

		if (sYAddDevValue.getMac() == null || sYAddDevValue.getCode() == null || sYAddDevValue.getLng() == null
				|| sYAddDevValue.getLat() == null || sYAddDevValue.getDevnum() == null
				|| sYAddDevValue.getGroupid() == null || sYAddDevValue.getModuleid() == null) {
			throw new MyException("0", "必填字段请勿留空！！");
		}
		ApiResult resultInfo = null;
		Integer insertObject = sYService.SYAddDev(sYAddDevValue);
		if (insertObject == 201) {
			resultInfo = ApiResult.resultInfo("0", "设备编号已存在！！", null);
		} else if (insertObject == 202) {
			resultInfo = ApiResult.resultInfo("0", "设备mac已存在！！", null);
		} else if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	// 删除设备
	@PostMapping("/sy/SYDeleteDev")
	@Permissions("sy:SYDeleteDev:del")
	public ApiResult SYDeleteDev(Integer moduleid, Integer devid) {
		if (moduleid == null || devid == null) {
			throw new MyException("0", "必填字段请勿留空！！");
		}
		ApiResult resultInfo = null;
		Integer insertObject = sYService.SYDeleteDev(moduleid, devid);
		if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	// 修改设备信息
	@PostMapping("/sy/SYUpdateDev")
	@Permissions("sy:SYUpdateDev:update")
	public ApiResult SYUpdateDev(SYUpdateDevVaule sYUpdateDevVaule) {
		if (sYUpdateDevVaule.getDevid() == null || sYUpdateDevVaule.getModuleid() == null
				|| sYUpdateDevVaule.getUserid() == null) {
			throw new MyException("0", "必填字段请勿留空！！");
		}
		ApiResult resultInfo = null;
		Integer insertObject = sYService.SYUpdateDev(sYUpdateDevVaule);
		if (insertObject == 201) {
			return resultInfo = ApiResult.resultInfo("0", "失败！！项目名已经存在", null);
		} else if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	// 加入设备签到列表
	@PostMapping("/sy/SYAddSignDevList")
	@Permissions("sy:SYAddSignDevList:add")
	public ApiResult SYAddSignDevList(Integer moduleid, String devid, Integer userid, Integer patrolid) {
		if (moduleid == null || devid == null || devid == null || userid == null) {
			throw new MyException("0", "必填字段请勿留空！！");
		}
		ApiResult resultInfo = null;
		Integer insertObject = sYService.SYAddSignDevList(moduleid, devid, userid, patrolid);
		System.out.println(insertObject.toString());
		if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	// 查询可更换的项目组
	@PostMapping("/sy/SYGroupList")
	@Permissions("sy:SYGroupList:query")
	public ApiResult SYGroupList(Integer userid, Integer id, Integer moduleid, String groupname, String itemnum) {
		if (moduleid == null || userid == null) {
			throw new MyException("0", "必填字段请勿留空！！");
		}
		return ApiResult.resultInfo("1", "成功！！", sYService.SYGroupList(userid, id, moduleid, groupname, itemnum));
	}

	// 根据设备id查询设备
	@PostMapping("/sy/SYSelectOnId")
	@Permissions("sy:SYSelectOnId:query")
	public ApiResult SYSelectOnId(Integer moduleid, Integer userid, Integer devid, String devnum, String macAddr) {
		return ApiResult.resultInfo("1", "成功！！", sYService.SYSelectOnId(moduleid, userid, devid, devnum, macAddr));
	}

	// 更换设备
	@PostMapping("/sy/SYChangeDev")
	@Permissions("sy:SYChangeDev:update")
	public ApiResult SYChangeDev(SYUpdateDevVaule sYUpdateDevVaule) {
		if (sYUpdateDevVaule.getDevid() == null || sYUpdateDevVaule.getMac() == null
				|| sYUpdateDevVaule.getUserid() == null) {
			throw new MyException("0", "必填字段请勿留空！！");
		}
		ApiResult resultInfo = null;
		Integer insertObject = sYService.SYChangeDev(sYUpdateDevVaule);
		if (insertObject == 201) {
			resultInfo = ApiResult.resultInfo("0", "设备mac已存在，失败！！", null);
		} else if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;

	}

	// 批量修改责任人
	@PostMapping("/sy/SYChangeDevOwn")
	@Permissions("sy:SYChangeDevOwn:update")
	public ApiResult SYChangeUsersDev(SYChangeUser SYChangeUser) throws MyException {

		return ApiResult.resultInfo("1", "成功", sYService.SYchangeDevOwn(SYChangeUser));

	}

	// 设备关联摄像头
	@PostMapping("/sy/SYdevConectVideo")
	@Permissions("sy:devConectVideo:add")
	public ApiResult devConectVideo(DevRelationVideoParam param) throws MyException {
		Integer result = videoService.devConectVideo(param);
		return ApiResult.resultInfo("1", "成功", result);
	}

	/**
	 * 水压报警趋势图
	 * 
	 * @return
	 * @throws MyException
	 */
	@PostMapping("sy/selectSYAlarmTendency")
	@Permissions("")
	public ApiResult selectSYAlarmTendency(Integer userid, Integer type, Integer groupid) throws MyException {
		if (type == 1) {
			return ApiResult.resultInfo("1", "执行成功", sYService.selectSYAlarmForDay(groupid));
		} else if (type == 3) {
			return ApiResult.resultInfo("1", "执行成功", sYService.selectSYAlarmForMonth(groupid));
		} else if (type == 2) {
			return ApiResult.resultInfo("1", "执行成功", sYService.selectSYAlarmForThreeYear(groupid));
		} else {
			return ApiResult.resultInfo("0", "执行失败,请选择筛选的单位", null);
		}
	}

	/**
	 * 水压报警处理统计
	 * 
	 * @return
	 * @throws MyException
	 */
	@PostMapping("sy/selectSYAlarmForWeeken")
	@Permissions("")
	public ApiResult selectSYAlarmForWeeken(Integer userid, Integer groupid) throws MyException {
		return ApiResult.resultInfo("0", "执行成功", sYService.selectSYAlarmForWeeken(groupid));
	}

	/**
	 * 水压报警类型统计
	 * 
	 * @return
	 * @throws MyException
	 */
	@PostMapping("sy/selectSYAlarmForState")
	@Permissions("")
	public ApiResult selectSYAlarmForState(Integer userid, Integer TheType, Integer groupid) throws MyException {
		return ApiResult.resultInfo("0", "执行成功", sYService.selectSYAlarmForState(groupid, TheType));
	}
}
