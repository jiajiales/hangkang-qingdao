package com.hot.manage.controller.hw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hot.manage.annotation.Permissions;
import com.hot.manage.controller.PageController;
import com.hot.manage.entity.hw.LKTHWDevList;
import com.hot.manage.entity.hw.vaule.HWChangeUser;
import com.hot.manage.entity.hw.vaule.LKTHWAddDevVaule;
import com.hot.manage.entity.hw.vaule.LKTHWDevListVaule;
import com.hot.manage.entity.hw.vaule.LKTHWUpdateDevVaule;
import com.hot.manage.entity.video.DevRelationVideoParam;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.hw.LKTHWService;
import com.hot.manage.service.video.VideoService;
import com.hot.manage.utils.ApiResult;

@RestController
public class LKTDevController implements PageController<LKTHWDevListVaule, LKTHWDevList> {

	@Autowired
	private VideoService videoService;
	@Autowired
	private LKTHWService lktService;
	
	@PostMapping("/hw/LKTHWDevList")
	@Permissions("hw:LKTHWDevList:query")
	// vaule=设备列表数据
	@Override
	public ApiResult page(LKTHWDevListVaule p) throws MyException {
		if (p.getModuleid() == null || p.getUserid() == null || p.getPageNum() == null || p.getPageNum() == null) {
			throw new MyException("0", "必填字段请勿留空！！");
		}
		return ApiResult.resultInfo("1", "成功!!", lktService.LKTHWDevList(p));
	}

	@PostMapping("/hw/LKTHWAddSignDevList")
	@Permissions("hw:LKTHWAddSignDevList:add")
	// vaule=加入设备签到列表
	public ApiResult LKTHWAddSignDevList(Integer moduleid, String devid, Integer userid, Integer patrolid) {
		if (moduleid == null || devid == null || devid == null || userid == null) {
			throw new MyException("0", "必填字段请勿留空！！");
		}
		ApiResult resultInfo = null;
		Integer insertObject = lktService.LKTHWAddSignDevList(moduleid, devid, userid, patrolid);
		System.out.println(insertObject.toString());
		if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	@PostMapping("/hw/LKTHWUpdateDev")
	@Permissions("hw:LKTHWUpdateDev:update")
	// vaule=修改设备信息
	public ApiResult LKTHWUpdateDev(LKTHWUpdateDevVaule lkthwUpdateDevVaule) {
		if (lkthwUpdateDevVaule.getDevid() == null || lkthwUpdateDevVaule.getModuleid() == null
				|| lkthwUpdateDevVaule.getUserid() == null) {
			throw new MyException("0", "必填字段请勿留空！！");
		}
		ApiResult resultInfo = null;
		Integer insertObject = lktService.LKTHWUpdateDev(lkthwUpdateDevVaule);
		if (insertObject == 201) {
			return resultInfo = ApiResult.resultInfo("0", "失败！！项目名已经存在", null);
		} else if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	// vaule=更换设备
	@PostMapping("/hw/LKTHWChangeDev")
	@Permissions("hw:LKTHWChangeDev:update")
	public ApiResult LKTHWChangeDev(Integer devid, String mac) {
		if (mac == null) {
			throw new MyException("0", "必填字段请勿留空！！");
		}
		ApiResult resultInfo = null;
		Integer insertObject = lktService.LKTHWChangeDev(devid,mac);
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
	@PostMapping("/hw/LKTHWChangeDevOwn")
	@Permissions("hw:LKTHWChangeDevOwn:update")
	public ApiResult LKTHWChangeUsersDev(HWChangeUser hWChangeUser) throws MyException {

		return ApiResult.resultInfo("1", "成功", lktService.changeDevOwn(hWChangeUser));

	}

	@PostMapping("/hw/LKTHWGroupList")
	@Permissions("hw:LKTHWGroupList:query")
	// vaule=查询可更换的项目组
	public ApiResult LKTHWGroupList(Integer userid, Integer id, Integer moduleid, String groupname, String itemnum) {
		if (moduleid == null || userid == null) {
			throw new MyException("0", "必填字段请勿留空！！");
		}
		return ApiResult.resultInfo("1", "成功！！", lktService.LKTHWGroupList(userid, id, moduleid, groupname, itemnum));
	}

	@PostMapping("/hw/LKTHWDeleteDev")
	@Permissions("hw:LKTHWDeleteDev:del")
	// vaule=删除设备
	public ApiResult LKTHWDeleteDev(Integer moduleid, Integer devid) {
		if (moduleid == null || devid == null) {
			throw new MyException("0", "必填字段请勿留空！！");
		}
		ApiResult resultInfo = null;
		Integer insertObject = lktService.LKTHWDeleteDev(moduleid, devid);
		if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	@PostMapping("/hw/LKTHWGroupListDev")
	@Permissions("hw:LKTHWGroupListDev:query")
	// vaule=项目组平面图设备查看
	public ApiResult LKTHWGroupListDev(Integer moduleid, Integer groupid, String site) {
		if (moduleid == null || groupid == null || site == null) {
			throw new MyException("0", "必填字段请勿留空！！");
		}
		return ApiResult.resultInfo("1", "成功！！", lktService.LKTHWGroupListDev(moduleid, groupid, site));
	}

	@PostMapping("/hw/LKTHWGroupListDevnum")
	@Permissions("hw:LKTHWGroupListDevnum:query")
	// vaule=红外项目终端设备数查询
	public ApiResult LKTHWGroupListDevnum(Integer groupid, Integer moduleid, Integer userid) {
		return ApiResult.resultInfo("1", "成功！！", lktService.LKTHWGroupListDevnum(groupid, moduleid, userid));
	}

	@PostMapping("/hw/LKTSelectOnId")
	@Permissions("hw:LKTSelectOnId:query")
	// vaule=根据设备id查询设备
	public ApiResult LKTSelectOnIdpic(Integer moduleid, Integer userid, Integer devid, String devnum, String macAddr) {
		return ApiResult.resultInfo("1", "成功！！", lktService.LKTHWSelectOnId(moduleid, userid, devid, devnum, macAddr));
	}

	@PostMapping("/hw/LKTHWAddDev")
	@Permissions("hw:LKTHWAddDev:add")
	// vaule=新增红外设备
	public ApiResult LKTHWAddDev(LKTHWAddDevVaule lkthwAddDevVaule) {
		if (lkthwAddDevVaule.getMac() == null || lkthwAddDevVaule.getCode() == null || lkthwAddDevVaule.getLng() == null
				|| lkthwAddDevVaule.getLat() == null || lkthwAddDevVaule.getDevnum() == null
				|| lkthwAddDevVaule.getGroupid() == null || lkthwAddDevVaule.getModuleid() == null) {
			throw new MyException("0", "必填字段请勿留空！！");
		}
		ApiResult resultInfo = null;
		Integer insertObject = lktService.LKTHWAddDev(lkthwAddDevVaule);
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

	@PostMapping("/hw/LKTHWSeleteMap")
	@Permissions("hw:LKTHWSeleteMap:query")
	// vaule=项目组楼层查询
	public ApiResult LKTHWSeleteMap(Integer groupid, Integer userid) {
		return ApiResult.resultInfo("1", "成功！！", lktService.LKTHWSeleteMap(groupid, userid));
	}

	// 设备关联摄像头
	@PostMapping("/hw/devConectVideo")
	@Permissions("hw:devConectVideo:add")
	public ApiResult devConectVideo(DevRelationVideoParam param) throws MyException {
		Integer result = videoService.devConectVideo(param);
		return ApiResult.resultInfo("1", "成功", result);
	}
	


}
