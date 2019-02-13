package com.hot.manage.controller.mc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hot.manage.annotation.Permissions;
import com.hot.manage.entity.GroupParam;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.item.ItemParam;
import com.hot.manage.entity.item.TDeviceGroupVo;
import com.hot.manage.entity.mc.ItemData;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.mc.McGroupService;
import com.hot.manage.utils.ApiResult;

@RestController
public class McItemController {
	@Autowired
	private McGroupService mcGroupService;

	@PostMapping("/McItem/InsertOne")
	@Permissions("mc:item:add")
	public ApiResult InsertOne(Integer userid, GroupParam params, String pics) throws MyException {
		ApiResult resultInfo = null;
		Integer add = mcGroupService.insert(userid, params, pics);
		if (add <= 0) {
			resultInfo = ApiResult.resultInfo("0", "添加失败！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "添加成功！",null);
		}
		return resultInfo;
	}

	@PostMapping("/McItem/update")
	@Permissions("mc:item:update")
	public ApiResult update(GroupParam params, String pics) throws MyException {
		ApiResult resultInfo = null;
		Integer update = mcGroupService.update(params, pics);
		if (update <= 0) {
			resultInfo = ApiResult.resultInfo("0", "修改失败！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "修改成功！",null);
		}
		return resultInfo;
	}

	@PostMapping("/McItem/del")
	@Permissions("mc:item:del")
	public ApiResult del(Integer groupid, Integer moduleid, Integer userid) throws MyException {
		ApiResult resultInfo = null;
		Integer del = mcGroupService.del(groupid, moduleid, userid);
		if (del <= 0) {
			resultInfo = ApiResult.resultInfo("0", "删除失败！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "删除成功！",null);
		}
		return resultInfo;
	}

	// 根据用户ID，模块ID，查询当前用户所有的项目(项目分布地图展示数据)
	@PostMapping("/McItem/selectMcGroupInfo")
	@Permissions("mc:item:list:read")
	public ApiResult selectMcGroupInfo(Integer userid, Integer moduleid) throws MyException {
		List<TDeviceGroupVo> selectMcGroupInfo = mcGroupService.selectMcGroupInfo(userid, moduleid);
		return ApiResult.resultInfo("1", "成功", selectMcGroupInfo);
	}

	// 项目列表分页
	@PostMapping("/McItem/selectMcGroupPage")
	@Permissions("mc:item:page:read")
	public ApiResult selectMcGroupPage(ItemParam param) throws MyException {
		PageInfo<TDeviceGroupVo> selectMcGroupPage = mcGroupService.selectMcGroupPage(param);
		return ApiResult.resultInfo("1", "成功", selectMcGroupPage);
	}

	// 单个项目查询
	@PostMapping("/McItem/selectMcGroup")
	@Permissions("mc:item:page:read")
	public ApiResult selectMcGroup(Integer groupid, Integer moduleid) throws MyException {
		TDeviceGroupVo selectMcGroup = mcGroupService.selectMcGroup(groupid, moduleid);
		return ApiResult.resultInfo("1", "成功", selectMcGroup);
	}

	// 当前用户门磁模块下的所有终端设备
	@PostMapping("/McItem/selectMcDevNum")
	@Permissions("mc:item:devnum:read")
	public ApiResult selectMcDevNum(Integer userid, Integer moduleid) throws MyException {
		Integer selectMcDevNum = mcGroupService.selectMcDevNum(userid, moduleid);
		return ApiResult.resultInfo("1", "成功", selectMcDevNum);
	}

	// 当前用户指定项目下设备终端数量
	@PostMapping("/McItem/selectMcDevNumByGroupId")
	@Permissions("mc:item:devnumbygroupid:read")
	public ApiResult selectMcDevNumByGroupId(Integer groupid, Integer moduleid) throws MyException {
		Integer selectMcDevNumByGroupId = mcGroupService.selectMcDevNumByGroupId(groupid, moduleid);
		return ApiResult.resultInfo("1", "成功", selectMcDevNumByGroupId);
	}

	// 我的项目数据
	@PostMapping("/McItem/selectMcItemData")
	@Permissions("mc:item:myitem:read")
	public ApiResult selectMcItemData(Integer userid, Integer moduleid) throws MyException {
		List<ItemData> selectMcItemData = mcGroupService.selectMcItemData(userid, moduleid);
		return ApiResult.resultInfo("1", "成功", selectMcItemData);
	}
}
