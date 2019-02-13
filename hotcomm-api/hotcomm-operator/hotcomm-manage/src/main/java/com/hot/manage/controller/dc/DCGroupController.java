package com.hot.manage.controller.dc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.dc.param.DCGroupByIdParam;
import com.hot.manage.entity.dc.param.TItemPicParam;
import com.hot.manage.entity.dc.vo.DCGroupList;
import com.hot.manage.entity.item.TDeviceGroup;
import com.hot.manage.entity.item.TItemPic;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.dc.DCGroupService;
import com.hot.manage.utils.ApiResult;
import com.hot.manage.utils.ConverUtil;

@RestController
public class DCGroupController {

	@Autowired
	private DCGroupService dcGroupService;

	@PostMapping("DCDevice/selectAllDevice")
	public ApiResult selectAllDevice(Integer userid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("终端设备总数为:", "" + dcGroupService.selectAllDevice(userid),null);
		return resultInfo;

	}

	@PostMapping("DCDevice/selectMyGroupList")
	public ApiResult selectMyGroupList(Integer userid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功",dcGroupService.selectMyGroupList(userid));
		return resultInfo;
	}

	@PostMapping("DCDevice/selectGroupAddress")
	public ApiResult selectGroupAddress(Integer userid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功",dcGroupService.selectGroupAddress(userid));
		return resultInfo;
	}

	@PostMapping("DCDevice/selectGroupList")
	public ApiResult selectGroupList(Integer userid, Integer pageNum, Integer pageSize, String startTime,
			String endTime, String message) {
		PageHelper.startPage(pageNum, pageSize);
		Page<DCGroupList> page = dcGroupService.selectGroupList(startTime, endTime, message, userid);
		List<DCGroupList> list = ConverUtil.converPage(page, DCGroupList.class);
		PageInfo<DCGroupList> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功",pageinfo);
		return resultInfo;

	};

	@PostMapping("DCDevice/selectGroupById")
	public ApiResult selectGroupById(Integer groupid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功",dcGroupService.selectById(groupid));
		return resultInfo;
	}

	@PostMapping("DCDevice/updateGroupById")
	public ApiResult updateGroupById(Integer userid, DCGroupByIdParam dcGroupByIdParam, String tItemParam)
			throws MyException {
		List<TItemPicParam> userList = new Gson().fromJson(tItemParam, new TypeToken<List<TItemPicParam>>() {
		}.getType());
		dcGroupByIdParam.setTItemPicParam(userList);
		System.out.println(userid + dcGroupByIdParam.toString() + userList.toString());
		int i = dcGroupService.updateById(dcGroupByIdParam);
		ApiResult resultInfo = null;
		if (i <= 0) {
			resultInfo = ApiResult.resultInfo("0", "修改项目失败！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "修改项目成功！！",null);
		}
		return resultInfo;
	};

	@PostMapping("DCDevice/deleteGroupById")
	public ApiResult deleteById(Integer groupid, Integer userid) {
		int i = dcGroupService.deleteById(groupid, userid);
		ApiResult resultInfo = null;
		if (i <= 0) {
			resultInfo = ApiResult.resultInfo("0", "删除项目失败！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "删除项目成功！！",null);
		}
		return resultInfo;
	}

	@PostMapping("DCDevice/insertGroup")
	public ApiResult insertGroup(Integer userid, TDeviceGroup deviceGroup, String tItemPicList) throws MyException {
		System.out.println(tItemPicList);
		List<TItemPic> userList =new ArrayList<>();
		if (tItemPicList != null) {
			userList = new Gson().fromJson(tItemPicList, new TypeToken<List<TItemPic>>() {
			}.getType());
			System.out.println(userList.toString());
		}
		int i = dcGroupService.insertGroup(userid, deviceGroup, userList);
		ApiResult resultInfo = null;
		if (i <= 0) {
			resultInfo = ApiResult.resultInfo("0", "添加项目失败！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "添加项目成功！！",null);
		}
		return resultInfo;

	};

	@PostMapping("DCDevice/SelectManager")
	public ApiResult SelectManager(Integer userid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功",dcGroupService.SelectManager(userid));
		return resultInfo;

	}

	@PostMapping("DCDevice/ItemPicIsExtsis")
	public ApiResult ItemPicIsExtsis(Integer itemid, String site) throws MyException {
		Integer i = dcGroupService.ItemPicIsExtsis(itemid, site);
		ApiResult resultInfo = null;
		if (i <= 0) {
			resultInfo = ApiResult.resultInfo("0", "新增楼层有重复！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "没有重复,可新增设备！！",null);
		}
		return resultInfo;
	}

	@PostMapping("DCDevice/PicisbindDevice")
	public ApiResult PicisbindDevice(Integer picid) throws MyException {
		boolean picisbindDevice = dcGroupService.PicisbindDevice(picid);
		int i = picisbindDevice ? 1 : 0;
		ApiResult resultInfo = null;
		if (i <= 0) {
			resultInfo = ApiResult.resultInfo("0", "该楼层有绑定设备！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "该楼层没有绑定设备！！",null);
		}
		return resultInfo;
	}
}
