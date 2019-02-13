package com.hot.manage.controller.jg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hot.manage.annotation.Permissions;
import com.hot.manage.controller.PageController;
import com.hot.manage.entity.jg.JGChangeUser;
import com.hot.manage.entity.jg.LKTDevList;
import com.hot.manage.entity.jg.LKTGroupListDev;
import com.hot.manage.entity.jg.vaule.LKTDevListVaule;
import com.hot.manage.entity.jg.vaule.LKTJgAddDevVaule;
import com.hot.manage.entity.jg.vaule.LKTJgAddGroupVaule;
import com.hot.manage.entity.jg.vaule.LKTJgAddGroupVauleSite;
import com.hot.manage.entity.jg.vaule.LKTUpdateDevVaule;
import com.hot.manage.entity.jg.vaule.LKTUpdateItemVaule;
import com.hot.manage.entity.video.DevRelationVideoParam;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.jg.LKTJGService;
import com.hot.manage.service.video.VideoService;
import com.hot.manage.utils.ApiResult;

@RestController
public class LJGController implements PageController<LKTDevListVaule, LKTDevList> {

	@Autowired
	private LKTJGService lktjgService;

	@Autowired
	private VideoService videoService;

	@PostMapping("/jg/LKTDevList")
	@Permissions("jg:LKTDevList:query")
	// vaule=设备列表数据
	@Override
	public ApiResult page(LKTDevListVaule p) throws MyException {
		return ApiResult.resultInfo("1", "成功!!", lktjgService.LKTDevList(p));
	}
	
	@PostMapping("/jg/selectCountBypurpose")
	@Permissions("jg:read:selectCountBypurpose")
	// 井盖设备分类统计(类型)
	public ApiResult selectCountBypurpose(Integer userid) throws MyException {
		return ApiResult.resultInfo("1", "成功!!", lktjgService.selectCountBypurpose(userid));
	}
	
	@PostMapping("/jg/selectCountByloadbear")
	@Permissions("jg:read:selectCountByloadbear")
	// 井盖设备分类统计(承重)
	public ApiResult selectCountByloadbear(Integer userid) throws MyException {
		return ApiResult.resultInfo("1", "成功!!", lktjgService.selectCountByloadbear(userid));
	}
	
	@PostMapping("/jg/selectDevRate")
	@Permissions("jg:read:selectDevRate")
	// 设备地图报警频率
	public ApiResult selectDevRate(Integer queryType, String startTime, String endTime,Integer userid) throws MyException {
		return ApiResult.resultInfo("1", "成功!!", lktjgService.selectDevRate(queryType, startTime, endTime, userid));
	}
	
	@PostMapping("/jg/JGselectDevAlarmHandleByTime")
	@Permissions("jg:read:JGselectDevAlarmHandleByTime")
	// 报警处理统计
	public ApiResult JGselectDevAlarmHandleByTime(Integer queryType, String startTime, String endTime,Integer userid) throws MyException {
		return ApiResult.resultInfo("1", "成功!!", lktjgService.JGselectDevAlarmHandleByTime(queryType, startTime, endTime,userid));
	}

	@PostMapping("/jg/LKTAddSignDevList")
	@Permissions("jg:LKTAddSignDevList:add")
	// vaule=加入设备签到列表
	public ApiResult LKTAddSignDevList(Integer moduleid, String devid, Integer patrolid) {
		ApiResult resultInfo = null;
		Integer insertObject = lktjgService.LKTAddSignDevList(moduleid, devid, patrolid);
		if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	@PostMapping("/jg/LKTUpdateDev")
	@Permissions("jg:LKTUpdateDev:update")
	// vaule=修改设备信息
	public ApiResult LKTUpdateDev(LKTUpdateDevVaule lktUpdateDevVaule) {
		ApiResult resultInfo = null;
		Integer insertObject = lktjgService.LKTUpdateDev(lktUpdateDevVaule);
		if (insertObject == 201) {
			resultInfo = ApiResult.resultInfo("0", "设备编号已存在！！", null);
		} else if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	@PostMapping("/jg/LKTGroupList")
	@Permissions("jg:LKTGroupList:query")
	// vaule=查询可更换的项目组
	public ApiResult LKTGroupList(Integer userid, Integer id, Integer moduleid, String groupname, String itemnum) {
		ApiResult resultInfo = ApiResult.resultInfo("1", "成功",
				lktjgService.LKTGroupList(userid, id, moduleid, groupname, itemnum));
		return resultInfo;
	}

	@PostMapping("/jg/LKTDeleteDev")
	@Permissions("jg:LKTDeleteDev:del")
	// vaule=删除设备
	public ApiResult LKTDeleteDev(Integer moduleid, Integer devid) {
		ApiResult resultInfo = null;
		Integer insertObject = lktjgService.LKTDeleteDev(moduleid, devid);
		if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	@PostMapping("/jg/LKTJgDevNum")
	@Permissions("jg:LKTJgDevNum:query")
	// vaule=查询终端设备总数
	public ApiResult LKTJgDevNum(Integer moduleid, Integer userid) {
		ApiResult resultInfo = ApiResult.resultInfo("1", "成功", lktjgService.LKTJgDevNum(moduleid, userid));
		return resultInfo;
	}

	@PostMapping("/jg/LKTJgItemList")
	@Permissions("jg:LKTJgItemList:query")
	// vaule=查询项目列表
	public ApiResult LKTJgItemList(LKTDevListVaule lktDevListVaule) throws MyException {
		// TODO Auto-generated method stub
		ApiResult resultInfo = ApiResult.resultInfo("1", "成功", lktjgService.LKTJgItemList(lktDevListVaule));
		return resultInfo;
	}

	@PostMapping("/jg/LKTDeleteItem")
	@Permissions("jg:LKTDeleteItem:del")
	// vaule=项目删除
	public ApiResult LKTDeleteItem(Integer id) throws MyException {
		ApiResult resultInfo = null;
		Integer insertObject = lktjgService.LKTDeleteItem(id);
		if (insertObject == 201) {
			resultInfo = ApiResult.resultInfo("0", "该项目下存在绑定设备！！", null);
		} else if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		System.out.println(insertObject);
		return resultInfo;
	}

	@PostMapping("/jg/LKTUpdateItem")
	@Permissions("jg:LKTUpdateItem:update")
	// vaule=项目修改
	public ApiResult LKTUpdateItem(LKTUpdateItemVaule lktUpdateItemVaule, String listsite) {
		ApiResult resultInfo = null;
		List<LKTJgAddGroupVauleSite> sitelist = new Gson().fromJson(listsite,
				new TypeToken<List<LKTJgAddGroupVauleSite>>() {
				}.getType());
		lktUpdateItemVaule.setSitelist(sitelist);
		Integer insertObject = lktjgService.LKTUpdateItem(lktUpdateItemVaule);
		if (insertObject == 201) {
			return resultInfo = ApiResult.resultInfo("0", "失败！！项目名已经存在", null);
		} else if (insertObject == 202) {
			return resultInfo = ApiResult.resultInfo("0", "失败！！项目编号已经存在", null);
		} else if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	@PostMapping("/jg/LKTGroupListDev")
	@Permissions("jg:LKTGroupListDev:query")
	// vaule=项目组平面图设备查看
	public List<LKTGroupListDev> LKTGroupListDev(Integer moduleid, Integer groupid, String site) {
		return lktjgService.LKTGroupListDev(moduleid, groupid, site);
	}

	@PostMapping("/jg/LKTJgItemListMap")
	@Permissions("jg:LKTJgItemListMap:query")
	// vaule=我的项目查询
	public ApiResult LKTJgItemListMap(Integer moduleid, Integer userid) {
		ApiResult resultInfo = ApiResult.resultInfo("1", "成功", lktjgService.LKTJgItemListMap(moduleid, userid));
		return resultInfo;
	}

	@PostMapping("/jg/LKTGroupListDevnum")
	@Permissions("jg:LKTGroupListDevnum:query")
	// vaule=井盖项目终端设备数查询
	public ApiResult LKTGroupListDevnum(Integer moduleid, Integer userid, Integer groupid) {
		ApiResult resultInfo = ApiResult.resultInfo("1", "成功",
				lktjgService.LKTGroupListDevnum(moduleid, userid, groupid));
		return resultInfo;
	}

	@PostMapping("/jg/LKTSelectOnId")
	@Permissions("jg:LKTSelectOnId:query")
	// vaule=根据设备id查询设备
	public ApiResult LKTSelectOnIdpic(Integer moduleid, Integer userid, Integer devid, String devnum, String macAddr) {
		ApiResult resultInfo = ApiResult.resultInfo("1", "成功",
				lktjgService.LKTSelectOnIdpic(moduleid, userid, devid, devnum, macAddr));
		return resultInfo;
	}

	@PostMapping("/jg/LKTJgAddDev")
	@Permissions("jg:LKTJgAddDev:add")
	// vaule==新增井盖设备
	public ApiResult LKTJgAddDev(LKTJgAddDevVaule lktJgAddDevVaule) {
		ApiResult resultInfo = null;
		Integer insertObject = lktjgService.LKTJgAddDev(lktJgAddDevVaule);
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

	@PostMapping("/jg/LKTJgAddGroup")
	@Permissions("jg:LKTJgAddGroup:add")
	// vaule==新增项目
	public ApiResult LKTJgAddGroup(LKTJgAddGroupVaule lktJgAddDevVaule, String listsite) {
		ApiResult resultInfo = null;
		List<LKTJgAddGroupVauleSite> sitelist = new Gson().fromJson(listsite,
				new TypeToken<List<LKTJgAddGroupVauleSite>>() {
				}.getType());
		lktJgAddDevVaule.setSitelist(sitelist);
		Integer insertObject = lktjgService.LKTJgAddGroup(lktJgAddDevVaule);
		if (insertObject == 201) {
			return resultInfo = ApiResult.resultInfo("0", "失败！！项目名已经存在", null);
		} else if (insertObject == 202) {
			return resultInfo = ApiResult.resultInfo("0", "失败！！项目编号已经存在", null);
		} else if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	@PostMapping("/jg/LKTSeleteMap")
	@Permissions("jg:LKTSeleteMap:query")
	// vaule=查询项目楼层图片
	public ApiResult LKTSeleteMap(Integer groupid, Integer userid) {
		ApiResult resultInfo = ApiResult.resultInfo("1", "成功", lktjgService.LKTSeleteMap(groupid, userid));
		return resultInfo;
	}

	@PostMapping("/jg/getUser")
	@Permissions("jg:getUser:query")
	// vaule=查询可选责任人员
	public ApiResult getUser(Integer userid) {
		ApiResult resultInfo = ApiResult.resultInfo("1", "成功", lktjgService.getUser(userid));
		return resultInfo;
	}

	@PostMapping("/jg/updateDeviceOwn")
	@Permissions("jg:updateDeviceOwn:update")
	// vaule=批量修改责任人
	public ApiResult updateDeviceOwn(JGChangeUser jgChangeUser) {
		ApiResult resultInfo = ApiResult.resultInfo("1", "成功", lktjgService.updateDeviceOwn(jgChangeUser));
		return resultInfo;
	}

	@PostMapping("/jg/jgdevConectVideo")
	@Permissions("jg:jgdevConectVideo:add")
	// vaule=联动视频
	public ApiResult jgdevConectVideo(DevRelationVideoParam param) {
		ApiResult resultInfo = ApiResult.resultInfo("1", "成功", videoService.devConectVideo(param));
		return resultInfo;
	}
	
	// vaule=更换设备
		@PostMapping("/jg/LKTJGChangeDev")
		@Permissions("jg:LKTJGChangeDev:update")
		public ApiResult LKTHWChangeDev(Integer devid, String mac) {
			if (mac == null) {
				throw new MyException("0", "必填字段请勿留空！！");
			}
			ApiResult resultInfo = null;
			Integer insertObject = lktjgService.LKTJGChangeDev(devid,mac);
			if (insertObject == 201) {
				resultInfo = ApiResult.resultInfo("0", "设备mac已存在，失败！！", null);
			} else if (insertObject <= 0) {
				resultInfo = ApiResult.resultInfo("0", "失败！！", null);
			} else {
				resultInfo = ApiResult.resultInfo("1", "成功！！", null);
			}
			return resultInfo;

		}
}
