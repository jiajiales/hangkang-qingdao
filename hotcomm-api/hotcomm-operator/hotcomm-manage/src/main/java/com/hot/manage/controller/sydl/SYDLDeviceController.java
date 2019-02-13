package com.hot.manage.controller.sydl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.annotation.Permissions;
import com.hot.manage.controller.PageController;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.sydl.SYDLChangeUser;
import com.hot.manage.entity.sydl.SYDLDevList;
import com.hot.manage.entity.sydl.value.SYDLAddDevValue;
import com.hot.manage.entity.sydl.value.SYDLDevListValue;
import com.hot.manage.entity.sydl.value.SYDLUpdateDevVaule;
import com.hot.manage.entity.video.DevRelationVideoParam;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.sydl.SYDLService;
import com.hot.manage.service.video.VideoService;
import com.hot.manage.utils.ApiResult;
import com.hot.manage.utils.ConverUtil;

@RestController
public class  SYDLDeviceController   implements  PageController<SYDLDevListValue, SYDLDevList> {

	
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private SYDLService  sYDLService;
	// 设备列表数据
	@PostMapping("/sydl/SYDLDevList")
	@Permissions("hw:LKTHWDevList:query")
	@Override/**/
	public ApiResult page(SYDLDevListValue p) throws MyException {
		if (p.getModuleid() == null || p.getUserid() == null || p.getPageNum() == null || p.getPageNum() == null) {
			throw new MyException("0", "必填字段请勿留空！！");
		}
		PageHelper.startPage(p.getPageNum(), p.getPageSize());
		Page<SYDLDevList> page = sYDLService.SYDLDevList(p);
		List<SYDLDevList> list = ConverUtil.converPage(page, SYDLDevList.class);
		PageInfo<SYDLDevList> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(),
				list);
		return ApiResult.resultInfo("1", "成功!!", pageinfo);
	}

	// 设备列表数据
		@PostMapping("/sydl/AlarmingTrendForSYDL")
		@Permissions("hw:AlarmingTrendForSYDL:query")
		public ApiResult AlarmingTrendForSYDL(Integer queryType, Integer userid, Integer groupid) throws MyException {
			return ApiResult.resultInfo("1", "成功!!", sYDLService.AlarmingTrendForSYDL(queryType, userid, groupid));
		}
	
	
	
	// 新增设备
	@PostMapping("/sydl/SYDLAddDev")
	@Permissions("sydl:SYDLAddDev:add")
	public ApiResult SYAddDev(SYDLAddDevValue sYDLAddDevValue) {
		
		if (sYDLAddDevValue.getMac() == null || sYDLAddDevValue.getCode() == null || sYDLAddDevValue.getLng() == null
				|| sYDLAddDevValue.getLat() == null || sYDLAddDevValue.getDevnum() == null
				|| sYDLAddDevValue.getGroupid() == null || sYDLAddDevValue.getModuleid() == null) {
			throw new MyException("0", "必填字段请勿留空！！");
		}
		ApiResult resultInfo = null;
		Integer insertObject = sYDLService.SYDLAddDev(sYDLAddDevValue);
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
	

    //删除设备 
	@PostMapping("/sydl/SYDLDeleteDev")
	@Permissions("sydl:SYDLDeleteDev:del")
	public ApiResult SYDLDeleteDev(Integer moduleid, Integer devid) {
		if (moduleid == null || devid == null) {
			throw new MyException("0", "必填字段请勿留空！！");
		}
		ApiResult resultInfo = null;
		Integer insertObject = sYDLService.SYDLDeleteDev(moduleid, devid);
		if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}
	
	
	//修改设备信息
	@PostMapping("/sydl/SYDLUpdateDev")
	@Permissions("sydl:SYDLUpdateDev:update")
	public ApiResult SYDLUpdateDev(SYDLUpdateDevVaule sYDLUpdateDevVaule) {
		if (sYDLUpdateDevVaule.getDevid() == null || sYDLUpdateDevVaule.getModuleid() == null
				|| sYDLUpdateDevVaule.getUserid() == null) {
			throw new MyException("0", "必填字段请勿留空！！");
		}
		ApiResult resultInfo = null;
		Integer insertObject = sYDLService.SYDLUpdateDev(sYDLUpdateDevVaule);
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
	@PostMapping("/sydl/SYDLAddSignDevList")
	@Permissions("sydl:SYDLAddSignDevList:add")
	public ApiResult SYDLAddSignDevList(Integer moduleid, String devid, Integer userid, Integer patrolid) {
		if (moduleid == null || devid == null || devid == null || userid == null) {
			throw new MyException("0", "必填字段请勿留空！！");
		}
		ApiResult resultInfo = null;
		Integer insertObject = sYDLService.SYDLAddSignDevList(moduleid, devid, userid, patrolid);
		System.out.println(insertObject.toString());
		if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}
	
	// 查询可更换的项目组
	@PostMapping("/sydl/SYDLGroupList")
	@Permissions("sydl:SYDLGroupList:query")
	public ApiResult SYDLGroupList(Integer userid, Integer id, Integer moduleid, String groupname, String itemnum) {
		if (moduleid == null || userid == null) {
			throw new MyException("0", "必填字段请勿留空！！");
		}
		return ApiResult.resultInfo("1", "成功！！", sYDLService.SYDLGroupList(userid, id, moduleid, groupname, itemnum));
	}
	
	
	// 根据设备id查询设备
	@PostMapping("/sydl/SYDLSelectOnId")
	@Permissions("sydl:SYDLSelectOnId:query")
	public ApiResult SYDLSelectOnId(Integer moduleid, Integer userid, Integer devid, String devnum, String macAddr) {
		return ApiResult.resultInfo("1", "成功！！", sYDLService.SYDLSelectOnId(moduleid, userid, devid, devnum, macAddr));
	}

	//更换设备
		@PostMapping("/sydl/SYDLhangeDev")
		@Permissions("sydl:SYDLhangeDev:update")
		public ApiResult SYDLhangeDev(SYDLUpdateDevVaule sYDLUpdateDevVaule) {
			if (sYDLUpdateDevVaule.getDevid() == null || sYDLUpdateDevVaule.getMac() == null
					|| sYDLUpdateDevVaule.getUserid() == null) {
				throw new MyException("0", "必填字段请勿留空！！");
			}
			ApiResult resultInfo = null;
			Integer insertObject = sYDLService.SYDLChangeDev(sYDLUpdateDevVaule);
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
		@PostMapping("/sydl/SYDLChangeDevOwn")
		@Permissions("sydl:SYDLChangeDevOwn:update")
		public ApiResult SYChangeUsersDev(SYDLChangeUser sYDLChangeUser) throws MyException {

			return ApiResult.resultInfo("1", "成功", sYDLService.SYDLchangeDevOwn(sYDLChangeUser));

		}
		
		// 设备关联摄像头
		@PostMapping("/sydl/SYDLdevConectVideo")
		@Permissions("sydl:SYDLdevConectVideo:add")
		public ApiResult devConectVideo(DevRelationVideoParam param) throws MyException {
			Integer result = videoService.devConectVideo(param);
			return ApiResult.resultInfo("1", "成功", result);
		}


}
