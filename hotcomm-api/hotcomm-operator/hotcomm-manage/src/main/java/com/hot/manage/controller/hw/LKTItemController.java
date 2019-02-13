package com.hot.manage.controller.hw;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hot.manage.annotation.Permissions;
import com.hot.manage.controller.PageController;
import com.hot.manage.entity.hw.vaule.LKTHWAddGroupVaule;
import com.hot.manage.entity.hw.vaule.LKTHWAddGroupVauleSite;
import com.hot.manage.entity.hw.vaule.LKTHWDevListVaule;
import com.hot.manage.entity.jg.LKTJgDevNum;
import com.hot.manage.entity.jg.LKTJgItemList;
import com.hot.manage.entity.jg.LKTJgItemListMap;
import com.hot.manage.entity.jg.vaule.LKTJgAddGroupVauleSite;
import com.hot.manage.entity.jg.vaule.LKTUpdateItemVaule;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.hw.LKTHWService;
import com.hot.manage.utils.ApiResult;

@RestController
public class LKTItemController implements PageController<LKTHWDevListVaule, LKTJgItemList> {

	@Autowired
	private LKTHWService lktService;

	@PostMapping("/hw/LKTHWDevNum")
	@Permissions("item:hw:read:LKTHWDevNum")
	// vaule=红外终端设备数查询
	public LKTJgDevNum LKTHWDevNum(Integer moduleid, Integer userid) {
		return lktService.LKTHWDevNum(moduleid, userid);
	}

	@PostMapping("/hw/LKTHWItemList")
	@Permissions("item:hw:read:LKTHWItemList")
	// vaule=红外查询项目列表
	@Override
	public ApiResult page(LKTHWDevListVaule lkthwDevListVaule) throws MyException {
		// TODO Auto-generated method stub
		return ApiResult.resultInfo("1", "成功!!", lktService.LKTHWItemList(lkthwDevListVaule));
	}

	@PostMapping("/hw/LKTHWDeleteItem")
	@Permissions("item:hw:del:LKTHWDeleteItem")
	// vaule=项目删除
	public ApiResult LKTHWDeleteItem(Integer id) throws MyException {
		ApiResult resultInfo = null;
		Integer insertObject = lktService.LKTHWDeleteItem(id);
		if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！",null);
		}
		System.out.println(insertObject);
		return resultInfo;
	}

	@PostMapping("/hw/LKTHWUpdateItem")
	@Permissions("item:hw:update:LKTHWUpdateItem")
	// vaule=修改项目数据
	public ApiResult LKTHWUpdateItem(LKTUpdateItemVaule lktUpdateItemVaule, String listsite) {
		if (lktUpdateItemVaule.getItemid() == null || listsite == null) {
			throw new MyException("0", "必填字段请勿留空！！");
		}
		ApiResult resultInfo = null;
		List<LKTJgAddGroupVauleSite> sitelist = new Gson().fromJson(listsite,
				new TypeToken<List<LKTJgAddGroupVauleSite>>() {
				}.getType());
		lktUpdateItemVaule.setSitelist(sitelist);
		Integer insertObject = lktService.LKTHWUpdateItem(lktUpdateItemVaule);
		if (insertObject == 201) {
			return resultInfo = ApiResult.resultInfo("0", "失败！！项目名已经存在",null);
		} else if (insertObject == 202) {
			return resultInfo = ApiResult.resultInfo("0", "失败！！项目编号已经存在",null);
		} else if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！",null);
		}
		System.out.println(insertObject);
		return resultInfo;
	}

	@PostMapping("/hw/LKTHWItemListMap")
	@Permissions("item:hw:read:LKTHWItemListMap")
	// vaule=我的项目查询
	public List<LKTJgItemListMap> LKTHWItemListMap(Integer moduleid, Integer userid) throws MyException {
		// TODO Auto-generated method stub
		return lktService.LKTHWItemListMap(moduleid, userid);
	}

	@PostMapping("/hw/LKTHWAddGroup")
	@Permissions("item:hw:add:LKTHWAddGroup")
	// vaule=新增项目
	public ApiResult LKTHWAddGroup(LKTHWAddGroupVaule lkthwAddDevVaule, String listsite) {
		ApiResult resultInfo = null;
		List<LKTHWAddGroupVauleSite> sitelist = new Gson().fromJson(listsite,
				new TypeToken<List<LKTHWAddGroupVauleSite>>() {
				}.getType());
		lkthwAddDevVaule.setSitelist(sitelist);
		Integer insertObject = lktService.LKTHWAddGroup(lkthwAddDevVaule);
		if (insertObject == 201) {
			return resultInfo = ApiResult.resultInfo("0", "失败！！项目名已经存在",null);
		} else if (insertObject == 202) {
			return resultInfo = ApiResult.resultInfo("0", "失败！！项目编号已经存在",null);
		} else if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！",null);
		}
		return resultInfo;
	}
}
