package com.hot.manage.controller.ywj;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hot.manage.annotation.Permissions;
import com.hot.manage.controller.PageController;
import com.hot.manage.entity.ywj.LKTYWJDevNum;
import com.hot.manage.entity.ywj.LKTYWJGroupList;
import com.hot.manage.entity.ywj.LKTYWJItemList;
import com.hot.manage.entity.ywj.LKTYWJItemListMap;
import com.hot.manage.entity.ywj.vaule.LKTYWJAddGroupVaule;
import com.hot.manage.entity.ywj.vaule.LKTYWJAddGroupVauleSite;
import com.hot.manage.entity.ywj.vaule.LKTYWJDevListVaule;
import com.hot.manage.entity.ywj.vaule.LKTYWJUpdateItemVaule;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.ywj.LKTYWJItemService;
import com.hot.manage.utils.ApiResult;

@RestController
public class LKTYWJItemController implements PageController<LKTYWJDevListVaule, LKTYWJItemList> {

	@Autowired
	private LKTYWJItemService lktywjItemService;

	@PostMapping("/ywj/LKTYWJDevNum")
	@Permissions("item:ywj:read:LKTYWJDevNum")
	// vaule=液位计终端设备数查询
	public LKTYWJDevNum LKTYWJDevNum(Integer moduleid, Integer userid) {
		return lktywjItemService.LKTYWJDevNum(moduleid, userid);
	}

	@PostMapping("/ywj/LKTYWJItemList")
	@Permissions("item:ywj:read:LKTYWJItemList")
	// vaule=项目列表
	@Override
	public ApiResult page(LKTYWJDevListVaule p) throws MyException {
		return ApiResult.resultInfo("1", "成功!!", lktywjItemService.LKTYWJItemList(p));
	}

	@PostMapping("/ywj/LKTYWJGroupListOnid")
	@Permissions("item:ywj:read:LKTYWJGroupListOnid")
	// vaule=根据项目id查询项目
	public List<LKTYWJGroupList> LKTYWJGroupListOnid(Integer userid, Integer id, Integer moduleid) {
		return lktywjItemService.LKTYWJGroupListOnid(userid, id, moduleid);
	}

	@PostMapping("/ywj/LKTYWJDeleteItem")
	@Permissions("item:ywj:del:LKTYWJDeleteItem")
	// vaule=删除项目组
	public ApiResult LKTYWJDeleteItem(Integer id) {
		ApiResult resultInfo = null;
		Integer insertObject = lktywjItemService.LKTYWJDeleteItem(id);
		if (insertObject == 201) {
			resultInfo = ApiResult.resultInfo("0", "该项目下存在绑定设备！！", null);
		} else if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

	@PostMapping("/ywj/LKTYWJItemListMap")
	@Permissions("item:ywj:read:LKTYWJItemListMap")
	// vaule=我的项目查询
	public List<LKTYWJItemListMap> LKTYWJItemListMap(Integer moduleid, Integer userid) {
		return lktywjItemService.LKTYWJItemListMap(moduleid, userid);
	}

	@PostMapping("/ywj/LKTYWJAddGroup")
	@Permissions("item:ywj:add:LKTYWJAddGroup")
	// vaule==新增项目
	public ApiResult LKTYWJAddGroup(LKTYWJAddGroupVaule lktywjAddGroupVaule, String listsite) {
		ApiResult resultInfo = null;
		List<LKTYWJAddGroupVauleSite> sitelist = new Gson().fromJson(listsite,
				new TypeToken<List<LKTYWJAddGroupVauleSite>>() {
				}.getType());
		lktywjAddGroupVaule.setSitelist(sitelist);
		Integer insertObject = lktywjItemService.LKTYWJAddGroup(lktywjAddGroupVaule);
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

	@PostMapping("/ywj/LKTYWJUpdateItem")
	@Permissions("item:ywj:update:LKTYWJUpdateItem")
	// vaule=项目修改
	public ApiResult LKTYWJUpdateItem(LKTYWJUpdateItemVaule lktywjUpdateItemVaulet, String listsite) {
		ApiResult resultInfo = null;
		List<LKTYWJAddGroupVauleSite> sitelist = new Gson().fromJson(listsite,
				new TypeToken<List<LKTYWJAddGroupVauleSite>>() {
				}.getType());
		lktywjUpdateItemVaulet.setSitelist(sitelist);
		Integer insertObject = lktywjItemService.LKTYWJUpdateItem(lktywjUpdateItemVaulet);
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
}
