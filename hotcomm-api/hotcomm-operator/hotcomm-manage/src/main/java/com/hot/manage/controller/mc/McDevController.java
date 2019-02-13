package com.hot.manage.controller.mc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hot.manage.annotation.Permissions;
import com.hot.manage.entity.DevPageParam;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.mc.TDeviceMcVo;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.mc.TDeviceMcService;
import com.hot.manage.utils.ApiResult;

@RestController
public class McDevController {
	@Autowired
	private TDeviceMcService deviceMcService;

	@PostMapping("/MC/changeOwn")
	@Permissions("mc:changeOwn:update")
	public ApiResult changeOwn(String mcId, Integer ownId) throws MyException {
		ApiResult resultInfo = null;
		Integer one = deviceMcService.changeOwn(mcId, ownId);
		if (one <= 0) {
			resultInfo = ApiResult.resultInfo("0", "门磁责任人修改失败！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "门磁责任人修改成功！", null);
		}
		return resultInfo;
	}

	@PostMapping("/MC/changeMac")
	@Permissions("mc:changeMac:update")
	public ApiResult changeMac(Integer mcId, String mac) throws MyException {
		ApiResult resultInfo = null;
		Integer one = deviceMcService.changeMac(mcId, mac);
		if (one == 201) {
			resultInfo = ApiResult.resultInfo("0", "门磁MAC已存在！", null);
		} else if (one <= 0) {
			resultInfo = ApiResult.resultInfo("0", "门磁MAC修改失败！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "门磁MAC修改成功！", null);
		}
		return resultInfo;
	}

	@PostMapping("/MC/insertOne")
	@Permissions("mc:insertOne:add")
	public ApiResult insertOne(TDeviceMcVo mc) throws MyException {
		ApiResult resultInfo = null;
		Integer one = deviceMcService.insertOne(mc);
		if (one <= 0) {
			resultInfo = ApiResult.resultInfo("0", "门磁设备添加失败！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "门磁设备添加成功！", null);
		}
		return resultInfo;
	}

	@PostMapping("/MC/updateDev")
	@Permissions("mc:updateDev:update")
	public ApiResult updateDev(TDeviceMcVo mc) throws MyException {
		ApiResult resultInfo = null;
		Integer update = deviceMcService.update(mc);
		if (update <= 0) {
			resultInfo = ApiResult.resultInfo("0", "门磁设备修改失败！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "门磁设备修改成功！", null);
		}
		return resultInfo;
	}

	@PostMapping("/MC/delDev")
	@Permissions("mc:delDev:del")
	public ApiResult delDev(Integer id, Integer moduleid) throws MyException {
		ApiResult resultInfo = null;
		Integer del = deviceMcService.del(id, moduleid);
		if (del <= 0) {
			resultInfo = ApiResult.resultInfo("0", "门磁设备删除失败！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "门磁设备删除成功！", null);
		}
		return resultInfo;
	}

	// 设备列表分页
	@PostMapping("/MC/getPageInfo")
	@Permissions("mc:getPageInfo:query")
	public ApiResult getPageInfo(DevPageParam param) throws MyException {
		PageInfo<TDeviceMcVo> selectPage = deviceMcService.selectPage(param);
		return ApiResult.resultInfo("1", "成功", selectPage);
	}

	// 单个设备查询
	@PostMapping("/MC/selectOne")
	@Permissions("mc:selectOne:query")
	public ApiResult selectOne(Integer id, Integer moduleid) throws MyException {
		TDeviceMcVo selectOne = deviceMcService.selectOne(id, moduleid);
		return ApiResult.resultInfo("1", "成功", selectOne);
	}

	// 加入签到列表
	@PostMapping("/MC/AddSignDevList")
	@Permissions("mc:addSignDevList:add")
	public ApiResult AddSignDevList(Integer moduleid, String devid, Integer patrolid) throws MyException {
		Integer selectOne = deviceMcService.AddSignDevList(moduleid, devid, patrolid);
		return ApiResult.resultInfo("1", "成功", selectOne);
	}

}
