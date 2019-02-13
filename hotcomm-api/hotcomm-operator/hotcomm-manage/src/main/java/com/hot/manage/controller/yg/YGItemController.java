package com.hot.manage.controller.yg;

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
import com.hot.manage.service.item.TDeviceGroupService;
import com.hot.manage.utils.ApiResult;

@RestController
public class YGItemController {
	@Autowired
	private TDeviceGroupService deviceGroupService;
	
	@PostMapping("/YgItem/insertOne")
	@Permissions("item:add")
	public ApiResult insertOne(Integer userid,GroupParam params,String pics) throws MyException {
		ApiResult resultInfo = null;
		Integer insertObject = deviceGroupService.insert(userid,params,pics);
		if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "添加项目组失败！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "添加项目组成功！！",null);
		}
		return resultInfo;
	}
	
	@PostMapping("/YgItem/update")
	@Permissions("item:update")
	public ApiResult update(GroupParam params,String pics) throws MyException {
		ApiResult resultInfo = null;
		Integer updateObject = deviceGroupService.update(params,pics);
		if (updateObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "项目修改失败！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "项目修改成功！！",null);
		}
		return resultInfo;
	}
	
	@PostMapping("/YgItem/delete")
	@Permissions("item:del")
	public ApiResult delItem(Integer groupid, Integer moduleid, Integer userid) throws MyException {
		ApiResult resultInfo = null;
		Integer delObject = deviceGroupService.del(groupid, moduleid, userid);
		if (delObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "项目删除失败！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "项目删除成功！！",null);
		}
		return resultInfo;
	}
	
	//单个项目查询
	@PostMapping("/YgItem/selectOne")
	@Permissions("item:one:read")
	public ApiResult selectOne(Integer groupid, Integer moduleid) throws MyException {
		TDeviceGroupVo selectById = deviceGroupService.selectById(groupid, moduleid);
		return ApiResult.resultInfo("1", "成功", selectById);
	}
	
	//项目分布（设备终端总数）
	@PostMapping("/YgItem/selectDevNum")
	@Permissions("item:num:read")
	public ApiResult selectDevNum(Integer userid, Integer moduleid) {
		Integer selectDevNum = deviceGroupService.selectDevNum(userid, moduleid);
		return ApiResult.resultInfo("1", "成功", selectDevNum);
	}
	
	@PostMapping("/YgItem/selectItemNum")
	@Permissions("")
	public ApiResult selectItemNum(Integer userid, Integer moduleid) {
		Integer num = deviceGroupService.selectItemNum(userid, moduleid);
		return ApiResult.resultInfo("1", "成功", num);
	}

	//项目列表
	@PostMapping("/YgItem/page")
	@Permissions("item:page:read")
	public ApiResult page(ItemParam params) throws MyException {
		PageInfo<TDeviceGroupVo> selectItems = deviceGroupService.selectItems(params);
		return ApiResult.resultInfo("1", "成功", selectItems);
	}

	//根据用户ID，模块ID，查询当前用户所有的项目(项目分布地图展示数据)
	@PostMapping("/YgItem/selectAllItems")
	//@Permissions("item:all:read")
	public ApiResult selectAllItems(Integer userid, Integer moduleid) throws MyException {
		List<TDeviceGroupVo> selectAllItems = deviceGroupService.selectAllItems(userid, moduleid);
		return ApiResult.resultInfo("1", "成功", selectAllItems);
	}
	
	//我的项目数据
	@PostMapping("/YgItem/selectYgItemData")
	//@Permissions("mc:item:myitem:read")
	public ApiResult selectYgItemData(Integer pageNum,Integer pageSize,Integer userid, Integer moduleid) throws MyException{
		PageInfo<ItemData> pageInfo = deviceGroupService.selectYgItemData(pageNum,pageSize,userid, moduleid);
		return ApiResult.resultInfo("1", "成功", pageInfo);	
	}

}
