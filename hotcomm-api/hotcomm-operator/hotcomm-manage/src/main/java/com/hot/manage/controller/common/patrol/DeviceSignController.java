package com.hot.manage.controller.common.patrol;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hot.manage.annotation.Permissions;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.common.patrol.AppSigndDevVo;
import com.hot.manage.entity.common.patrol.DeviceSignVo;
import com.hot.manage.entity.common.patrol.PatrolParams;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.common.patrol.DeviceSignService;
import com.hot.manage.utils.ApiResult;

@RestController
public class DeviceSignController {
	@Autowired
	private DeviceSignService deviceSignService;

	/**
	 * (终端设备)设备签到列表分页，各种条件查询
	 * 
	 * @param params
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/patrol/selectDevPageInfo")
	@Permissions("patrol:selectDevPageInfo:query")
	public ApiResult selectDevPageInfo(PatrolParams params) throws MyException {
		PageInfo<DeviceSignVo> pageInfo = deviceSignService.selectPageInfo(params);
		return ApiResult.resultInfo("1", "成功", pageInfo);
	}

	/**
	 * 把终端设备添加到签到设备表
	 * 
	 * @param deviceid
	 *            签到设备（终端设备）ID
	 * @param id
	 *            可以是多个巡检人员ID，以逗号分割
	 * @param moduleid
	 *            模块ID
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/patrol/insertDev")
	@Permissions("patrol:insertDev:add")
	public ApiResult insertDev(Integer deviceid, String id, Integer moduleid) throws MyException {
		Integer in = deviceSignService.insertDevSign(deviceid, id, moduleid);
		if (in <= 0) {
			return ApiResult.resultInfo("0", "添加失败", null);
		} else {
			return ApiResult.resultInfo("1", "添加成功", null);
		}
	}

	/**
	 * 修改签到设备
	 * 
	 * @param deviceid
	 *            终端设备ID
	 * @param id
	 *            巡检人员ID，可以是多个，以逗号分隔
	 * @param moduleid
	 *            模块ID
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/patrol/updateDevSign")
	@Permissions("patrol:updateDevSign:update")
	public ApiResult updateDevSign(Integer deviceid, String id, Integer moduleid) throws MyException {
		Integer up = deviceSignService.updateDevSign(deviceid, id, moduleid);
		if (up <= 0) {
			return ApiResult.resultInfo("0", "修改失败", null);
		} else {
			return ApiResult.resultInfo("1", "修改成功", null);
		}
	}

	/**
	 * 签到设备删除
	 * 
	 * @param deviceid
	 * @param moduleid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/patrol/delDevSign")
	@Permissions("patrol:delDevSign:del")
	public ApiResult delDevSign(Integer deviceid, Integer moduleid) throws MyException {
		Integer del = deviceSignService.delDevSign(deviceid, moduleid);
		if (del <= 0) {
			return ApiResult.resultInfo("0", "删除失败", null);
		} else {
			return ApiResult.resultInfo("1", "删除成功", null);
		}
	}

	/**
	 * 分配签到人员
	 * 
	 * @param deviceid
	 *            终端设备ID
	 * @param id
	 *            巡检人员ID
	 * @param moduleid
	 *            模块ID
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/patrol/allocationPatrol")
	@Permissions("patrol:allocationPatrol:update")
	public ApiResult allocationPatrol(Integer deviceid, String id, Integer moduleid) throws MyException {
		Integer allocationPatrol = deviceSignService.allocationPatrol(deviceid, id, moduleid);
		if (allocationPatrol <= 0) {
			return ApiResult.resultInfo("0", "分配失败", null);
		} else {
			return ApiResult.resultInfo("1", "分配成功", null);
		}
	}

	/**
	 * 根据项目ID，查询签到设备(app查询签到设备)
	 * 
	 * @param userid
	 * @param groupid
	 * @return
	 * @throws MyException
	 */
	@PostMapping("/patrol/queryDevSign")
	@Permissions("patrol:queryDevSign:query")
	public ApiResult queryDevSign(Integer userid, String context, Integer moduleid, Integer groupid)
			throws MyException {
		List<AppSigndDevVo> list = deviceSignService.selectSigndDevVo(userid, context, moduleid, groupid);
		return ApiResult.resultInfo("1", "成功", list);
	}

}
