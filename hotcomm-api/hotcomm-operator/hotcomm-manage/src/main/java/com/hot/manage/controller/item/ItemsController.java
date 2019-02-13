package com.hot.manage.controller.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hot.manage.annotation.Permissions;
import com.hot.manage.entity.item.TDevItemPic;
import com.hot.manage.entity.item.TItemPic;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.item.TDevItemPicService;
import com.hot.manage.service.item.TItemPicService;
import com.hot.manage.utils.ApiResult;

@RestController
public class ItemsController {

	@Autowired
	private TItemPicService itemPicService;
	@Autowired
	private TDevItemPicService devItemPicService;

	// 添加项目地图
	@PostMapping("/item/insertPic")
	@Permissions("item:pic:add")
	public ApiResult insertPic(TItemPic param) throws MyException {
		ApiResult resultInfo = null;
		Integer insert = itemPicService.insert(param);
		if (insert <= 0) {
			resultInfo = ApiResult.resultInfo("0", " 增加项目图片失败！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "增加项目图片成功！！",null);
		}
		return resultInfo;
	}

	// 修改项目地图
	@PostMapping("/item/updatePic")
	@Permissions("item:pic:update")
	public ApiResult updatePic(TItemPic param) throws MyException {
		ApiResult resultInfo = null;
		Integer update = itemPicService.update(param);
		if (update <= 0) {
			resultInfo = ApiResult.resultInfo("0", " 修改项目地图失败！！",null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "修改项目地图成功！！",null);
		}
		return resultInfo;
	}

	// 删除项目地图
	@PostMapping("/item/delPic")
	@Permissions("item:pic:del")
	public ApiResult delPic(Integer id) throws MyException {
		// 判断当前地图是否绑定设备
		List<TDevItemPic> list = devItemPicService.selectList(id);
		if (list.size() != 0) {
			TItemPic param = new TItemPic();
			param.setId(id);
			param.setIsdelete(true);
			param.setIsenable(false);
			Integer update = itemPicService.update(param);
			if (update > 0) {
				return ApiResult.resultInfo("1", "删除项目地图成功！！",null);
			}
		}
		ApiResult resultInfo = ApiResult.resultInfo("0", "删除项目地图失败！！",null);
		return resultInfo;
	}

	// 查询当前项目所有项目地图
	@PostMapping("/item/selectListOfItemPic")
	@Permissions("item:pic:list:read")
	public ApiResult selectListOfItemPic(Integer groupid) throws MyException {
		List<TItemPic> selectList = itemPicService.selectList(groupid);
		return ApiResult.resultInfo("1", "成功", selectList);
	}

//	// 查询具体的某一楼层地图
//	@PostMapping("/item/selectPicOne")
//	@Permissions("item:pic:one:read")
//	public ApiResult selectPicOne(String site, Integer groupid, Integer moduleid) throws MyException {
//		TItemPicVo selectOne = itemPicService.selectOne(site, groupid, moduleid);
//		return ApiResult.resultInfo("1", "成功", selectOne);
//	}
	
	//查询当前用户某一模块下项目总数
	@PostMapping("/item/selectGroupNum")
	@Permissions("item:groupnum:read")
	public ApiResult selectGroupNum(Integer userid, Integer moduleid) throws MyException {
		return null;
	}
	

}
