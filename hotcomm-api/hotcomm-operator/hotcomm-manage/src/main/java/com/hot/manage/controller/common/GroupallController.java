package com.hot.manage.controller.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hot.manage.annotation.Permissions;
import com.hot.manage.controller.group.IsxistController;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.common.group.Groupdevcount;
import com.hot.manage.entity.common.group.Groupdevstate;
import com.hot.manage.entity.common.group.Grouplist;
import com.hot.manage.entity.common.group.Groupmap;
import com.hot.manage.entity.common.group.Groupmapimg;
import com.hot.manage.entity.common.group.Groupmaps;
import com.hot.manage.entity.common.group.Groupone;
import com.hot.manage.entity.common.group.Groups;
import com.hot.manage.entity.common.group.Groupsiteimgpath;
import com.hot.manage.entity.common.group.Map_group_xq;
import com.hot.manage.entity.common.group.Sitedev;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.common.group.GroupallService;
import com.hot.manage.utils.ApiResult;

@Controller
@RequestMapping("/groupall")
@CrossOrigin
public class GroupallController {

	@Autowired
	private GroupallService groupallService;

	@Autowired
	private IsxistController isxistController;

	/**
	 * 查询项目列表
	 * @param userid
	 * @param moduleid
	 * @param starttime
	 * @param endtime
	 * @param keywords
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@Permissions("groupall:grouplist:query")
	@PostMapping("/grouplist")
	public ApiResult selectgrouplist(Integer userid, Integer moduleid, String starttime, String endtime,
			String keywords, Integer pageNum, Integer pageSize) {
		PageInfo<Grouplist> list = groupallService.selectgrouplist(userid, moduleid, starttime, endtime, keywords,
				pageNum, pageSize);
		return ApiResult.resultInfo("1", "成功", list);
	}

	/**
	 * 项目设备状态及数量(我的项目数据)
	 * 
	 * @param userid
	 * @param moduleid
	 * @return
	 */
	@ResponseBody
	@Permissions("groupall:groupdevstate:query")
	@PostMapping("/groupdevstate")
	public ApiResult selectgroupdevstate(Integer userid, Integer moduleid,Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<Groupdevstate> page=(Page<Groupdevstate>) groupallService.selectgroupdevstate(userid, moduleid);
		if(page.getTotal()==0){
			return ApiResult.resultInfo("1", "成功", null);
		}
		PageInfo<Groupdevstate> pageInfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), page.getResult());
		return ApiResult.resultInfo("1", "成功", pageInfo);
	}

	/**
	 * 项目下设备数量（设备分布（设备终端数量））
	 * 
	 * @param userid
	 * @param moduleid
	 * @param groupid
	 * @return
	 */
	@ResponseBody
	@Permissions("groupall:groupdevcount:query")
	@PostMapping("/groupdevcount")
	public ApiResult selectgroupdevcount(Integer userid, Integer moduleid, Integer groupid) {
		Groupdevcount list = groupallService.selectgroupdevcount(userid, moduleid, groupid);
		return ApiResult.resultInfo("1", "成功", list);
	}

	/**
	 * 地图项目弹框
	 * @param groupid
	 * @return
	 */
	@ResponseBody
	@Permissions("groupall:mapgroupxq:query")
	@PostMapping("/mapgroupxq")
	public ApiResult mapgroupdevstate(Integer groupid,Integer moduleid,Integer userid) {
		Map_group_xq list = groupallService.mapgroupdevstate(groupid,moduleid,userid);
		return ApiResult.resultInfo("1", "成功", list);
	}


	/**
	 * 根据项目id查询
	 *
	 * @param groupid
	 * @return
	 */
	@ResponseBody
	@Permissions("groupall:findgroup:query")
	@PostMapping("/findgroup")
	public ApiResult selectgroupbyid(Integer groupid) {
		List<Groupone> list = groupallService.selectgroupbyid(groupid);
		return ApiResult.resultInfo("1", "成功", list);
	}

	/**
	 * 删除项目
	 * 
	 * @param groupid
	 * @return
	 */
	@ResponseBody
	@Permissions("groupall:delgroup:del")
	@PostMapping("/delgroup")
	public ApiResult delgroup(Integer groupid) {
		int dgcount = isxistController.delgroup_before(groupid);
		if (dgcount > 0) {
			return ApiResult.resultInfo("0", "项目下有设备,不能删除！", null);
		} else {
			Integer status = groupallService.delgroup(groupid);
			if (status > 0) {
				return ApiResult.resultInfo("1", "删除成功！", null);
			} else {
				return ApiResult.resultInfo("0", "删除失败！", null);
			}
		}
	}

	/**
	 * 添加项目
	 * 
	 * @param groups
	 * @param listsite
	 * @return
	 */
	@ResponseBody
	@Permissions("groupall:insertgroup:add")
	@PostMapping("/insertgroup")
	public ApiResult insertgroup(Groups groups, String listsite) {
		int moduleid = groups.getModuleid();
		String keywords1 = groups.getGroupname();
		String keywords2 = groups.getItemnum();
		int dgcount = isxistController.countdg(moduleid, keywords1, keywords2, 0);
		if (dgcount > 0) {
			return ApiResult.resultInfo("0", "已有相同项目名称或编号！", null);
		} else {
			List<Groupmaps> sitelist = new Gson().fromJson(listsite, new TypeToken<List<Groupmaps>>() {
			}.getType());
			groups.setSitelist(sitelist);
			int a = groupallService.insertgroup(groups);
			if (a > 0) {
				return ApiResult.resultInfo("1", "增加成功！", null);
			} else {
				return ApiResult.resultInfo("0", "增加失败！", null);
			}
		}
	}

	/**
	 * 修改项目
	 * 
	 * @param groups
	 * @param listsite
	 * @return
	 */
	@ResponseBody
	@Permissions("groupall:updategroup:update")
	@PostMapping("/updategroup")
	public ApiResult updategroup(Groups groups, String listsite) {
		int moduleid = groups.getModuleid();
		String keywords1 = groups.getGroupname();
		String keywords2 = groups.getItemnum();
		Integer id = groups.getId();
		int dgcount = isxistController.countdg(moduleid, keywords1, keywords2, id);
		if (dgcount > 0) {
			return ApiResult.resultInfo("0", "已有相同项目名称或编号！", null);
		} else {
			List<Groupmaps> sitelist = new Gson().fromJson(listsite, new TypeToken<List<Groupmaps>>() {
			}.getType());
			groups.setSitelist(sitelist);
			int a = groupallService.updategroups(groups);
			if (a > 0) {
				return ApiResult.resultInfo("1", "修改成功！", null);
			} else {
				return ApiResult.resultInfo("0", "修改失败！", null);
			}
		}
	}

	/**
	 *根据groupid查设备地图
	 * @param groupid
	 * @param userid
	 * @param moduleid
	 * @return
	 */
	@ResponseBody
	//@Permissions("groupall:selectgroupmapbyid:query")
	@PostMapping("/selectgroupmapbyid")
	public ApiResult selectgroupmapimg(Integer groupid, Integer userid, Integer moduleid) {
		List<Groupmapimg> list = groupallService.selectgroupmapimg(groupid, userid, moduleid);
		return ApiResult.resultInfo("1", "成功", list);
	}

	/**
	 * 根据groupid查设备地图和楼层（设备分布（中间地图图片））
	 * @param groupid
	 * @param userid
	 * @param moduleid
	 * @return
	 */
	@ResponseBody
	@Permissions("groupall:selectgroupsiteimgpath:query")
	@PostMapping("/selectgroupsiteimgpath")
	public ApiResult selectgroupsite_imgpath(Integer groupid, Integer userid, Integer moduleid) {
		List<Groupsiteimgpath> list = groupallService.selectgroupsite_imgpath(groupid, userid, moduleid);
		return ApiResult.resultInfo("1", "成功", list);
	}

	/**
	 * 根据地图id查设备设备
	 * @param mapimgid
	 * @param moduleid
	 * @return
	 */
	@ResponseBody
	@Permissions("groupall:selectdevbysiteid:query")
	@PostMapping("/selectdevbysiteid")
	public ApiResult selectsitedev(Integer mapimgid, Integer moduleid) {
		System.out.println(mapimgid);
		System.out.println(moduleid);
		List<Sitedev> list = groupallService.selectsitedev(mapimgid, moduleid);
		return ApiResult.resultInfo("1", "成功", list);
	}

	/**
	 * 删除设备
	 * @param devid
	 * @param moduleid
	 * @return
	 */
	@ResponseBody
	@Permissions("groupall:deldev:del")
	@PostMapping("/deldev")
	public ApiResult insertdevice(Integer devid, Integer moduleid) {
		int a = groupallService.deldev(devid, moduleid);
		if (a > 0) {
			return ApiResult.resultInfo("1", "删除成功！", null);
		} else {
			return ApiResult.resultInfo("0", "删除失败！", null);
		}
	}

	/**
	 * 添加坐标图片
	 * @param picnum
	 * @param picpath
	 * @param site
	 * @param itemid
	 * @return
	 */
	@ResponseBody
	@Permissions("groupall:insertdevmapimg:add")
	@PostMapping("/insertdevmapimg")
	public ApiResult insertdevmapimg(String picnum, String picpath, String site, Integer itemid) {

		int a = groupallService.insertdevmapimg(picnum, picpath, site, itemid);
		if (a > 0) {
			return ApiResult.resultInfo("1", "添加成功！", null);
		} else {
			return ApiResult.resultInfo("0", "删除失败！", null);
		}
	}

	/**
	 *  删除坐标图片
	 /**
	 * @param mapimgid
	 * @param moduleid
	 * @return
	 */
	@ResponseBody
	@Permissions("groupall:deldevmapimg:del")
	@PostMapping("/deldevmapimg")
	public ApiResult deldevmapimg(Integer mapimgid,Integer moduleid) {
		int a = groupallService.deldevmapimg(mapimgid,moduleid);
		if (a < 0) {
			return ApiResult.resultInfo("0", "项目下有设备，不可删除！", null);
		} else if(a > 0){
			return ApiResult.resultInfo("1", "删除成功!", null);
		}else {
			return ApiResult.resultInfo("0", "删除失败！", null);
		}
	}

	/**
	 * 当前用户指定模块下的所有项目
	 * @param userid
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	@ResponseBody
	@Permissions("groupall:selectAllItem:query")
	@PostMapping("/selectAllItem")
	public ApiResult selectAllItem(Integer userid, Integer moduleid) throws MyException {
		List<Grouplist> selectAllItem = groupallService.selectAllItem(userid, moduleid);
		return ApiResult.resultInfo("1", "成功", selectAllItem);
	}

	/**
	 * 查询图片
	 * @param itemid
	 * @param site
	 * @return
	 */
	@ResponseBody
	@Permissions("groupall:selectmapimgcount:query")
	@PostMapping("/selectmapimgcount")
	public Integer selectmapimgcount(Integer itemid, String site) {
		Groupdevcount list = groupallService.selectmapimgcount(itemid, site);
		return list.getGroupdevcount();
	}

	/**
	 * 修改项目坐标
	 * @param groupid
	 * @param x
	 * @param y
	 * @param groupcode
	 * @return
	 */
	@ResponseBody
	@PostMapping("/updategroupxy")
	@Permissions("groupall:updategroupxy:update")
	public ApiResult updategroupxy(Integer groupid, Double x, Double y, String groupcode) {

		int a = groupallService.updategroupxy(groupid, x, y, groupcode);
		if (a > 0) {
			return ApiResult.resultInfo("1", "修改成功！", null);
		} else {
			return ApiResult.resultInfo("0", "修改失败！", null);
		}
	}
	
	/**
	 * 修改设备坐标
	 * @param id
	 * @param x
	 * @param y
	 * @param code
	 * @param moduleid
	 * @return
	 */
	@ResponseBody
	@PostMapping("/updatedevxy")
	@Permissions("groupall:updatedevxy:update")
	public ApiResult updatedevxy(Integer id, Double x, Double y,String code,Integer moduleid) {


		if(x>0 && y>0 && x<180 && y<180){
			int a = groupallService.updatedevxy(id, x, y,code,moduleid);
			if (a > 0) {
				return ApiResult.resultInfo("1", "修改成功！", null);
			} else {
				return ApiResult.resultInfo("0", "修改失败！", null);
			}
		}else {
			return ApiResult.resultInfo("0", "请输入正确的参数！", null);
		}
	}

	 //根据groupid查设备地图


	@ResponseBody
	@PostMapping("/selectgroupmap")
	@Permissions("groupall:selectgroupmap:query")
	public ApiResult selectgroupmap(Integer userid,Integer moduleid) {
		List<Groupmap> list = groupallService.selectgroupmap(userid, moduleid);
		return ApiResult.resultInfo("1", "成功", list);
	}

	@ResponseBody
	@Permissions("groupall:finddev:query")
	@PostMapping("/findgroupdev")
	public ApiResult selectDevByGroupID(Integer groupid, Integer moduleid) {
		return ApiResult.resultInfo("1", "成功", groupallService.selectDevByGroupID(groupid, moduleid));
	}
	
	
	/**
	 * 修改楼层图片
	 * @param itemid
	 * @param picurl
	 * @return
	 * @throws MyException
	 */
	@ResponseBody
	@PostMapping("/updateItemPic")
	public ApiResult updateItemPic(Integer itemid,String picurl)throws MyException{
		return ApiResult.resultInfo("1", "成功", groupallService.updateItemPic(itemid, picurl));
	}
	// 设备联动视频
	/*
	 * @ResponseBody
	 * 
	 * @PostMapping("/updategroupxy") public ApiResult insertvodrelation(String
	 * deviceid, String videoid, Integer moduleid) {
	 * 
	 * int a=groupallService.insertvodrelation(1,2,moduleid); if(a>0){ return
	 * ApiResult.resultInfo("1", "修改成功！",null); }else{ return
	 * ApiResult.resultInfo("0", "修改失败！",null); } }
	 */
}
