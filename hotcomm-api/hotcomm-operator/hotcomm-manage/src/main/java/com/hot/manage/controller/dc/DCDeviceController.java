package com.hot.manage.controller.dc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hot.manage.annotation.Permissions;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.dc.param.DCChangeUser;
import com.hot.manage.entity.dc.param.DCDeviceParam;
import com.hot.manage.entity.dc.param.VideoList;
import com.hot.manage.entity.dc.vo.DCDeviceList;
import com.hot.manage.entity.item.TDevItemPic;
import com.hot.manage.entity.item.TDeviceGroupRelation;
import com.hot.manage.entity.video.DevRelationVideoParam;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.dc.DCDeviceService;
import com.hot.manage.service.video.VideoService;
import com.hot.manage.utils.ApiResult;
import com.hot.manage.utils.ConverUtil;

@RestController
public class DCDeviceController {

	@Autowired
	private VideoService videoService;
	@Autowired
	private DCDeviceService dcDeviceService;

	@PostMapping("DCDevice/selectDevAllByGroup")
	@Permissions("dc:selectDevAllByGroup:query")
	public ApiResult selectDevAllByGroup(Integer groupid) {
		ApiResult resultInfo = ApiResult.resultInfo("1", "成功", dcDeviceService.selectDevAllByGroup(groupid));
		return resultInfo;
	}

	@PostMapping("DCDevice/selectList")
	@Permissions("dc:selectList:query")
	public ApiResult selectList(Integer userid, Integer pageNum, Integer pageSize, String startTime, String endTime,
			String message, Integer Battery, Integer groupid) throws MyException {
		PageHelper.startPage(pageNum, pageSize);
		Page<DCDeviceList> page = (Page<DCDeviceList>) dcDeviceService.selectList(startTime, endTime, message, Battery,
				userid, groupid);
		List<DCDeviceList> list = ConverUtil.converPage(page, DCDeviceList.class);
		if(list.size()==0){
			return new ApiResult("1", "执行成功", null);
		}
		PageInfo<DCDeviceList> pageinfo = new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", pageinfo);
		return resultInfo;

	};

	@PostMapping("DCDevice/selectDeviceToFloor")
	@Permissions("dc:selectDeviceToFloor:query")
	public ApiResult selectDeviceToFloor(Integer groupid) {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", dcDeviceService.selectDeviceToFloor(groupid));
		return resultInfo;

	}

	@PostMapping("DCDevice/selectDevicImg")
	@Permissions("dc:selectDevicImg:query")
	public ApiResult selectDevicImg(Integer imgId) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", dcDeviceService.selectDevicImg(imgId));
		return resultInfo;
	}

	@PostMapping("DCDevice/selectDevicById")
	@Permissions("dc:selectDevicById:query")
	public ApiResult selectDevicById(Integer userid, Integer id) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", dcDeviceService.selectDeviceById(userid, id));
		return resultInfo;

	};

	@PostMapping("DCDevice/DCDeviceAllGroup")
	@Permissions("dc:DCDeviceAllGroup:query")
	public ApiResult DCDeviceAllGroup(Integer userid) throws MyException {
		ApiResult resultInfo = ApiResult.resultInfo("1", "执行成功", dcDeviceService.DCDeviceAllGroup(userid));
		return resultInfo;
	}

	@PostMapping("DCDevice/insertDevice")
	@Permissions("dc:insertDevice:add")
	public ApiResult insertDevice(Integer userid, DCDeviceParam dcDeviceParam,
			TDeviceGroupRelation tdeviceGroupRelation, TDevItemPic tDevItemPic, VideoList videoList)
			throws MyException {
		int i = dcDeviceService.insertDevice(userid, dcDeviceParam, tdeviceGroupRelation, tDevItemPic, videoList);
		ApiResult resultInfo = null;
		if (i <= 0) {
			resultInfo = ApiResult.resultInfo("0", "添加设备失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "添加设备成功！！", null);
		}

		return resultInfo;
	}

	@PostMapping("DCDevice/updateDeviceById")
	@Permissions("dc:updateDeviceById:update")
	public ApiResult updateDeviceById(DCDeviceParam dcDeviceParam, Integer userid,
			TDeviceGroupRelation tdeviceGroupRelation, TDevItemPic tdip, String videoid) throws MyException {
		int i = dcDeviceService.updateDeviceById(dcDeviceParam, userid, tdeviceGroupRelation, tdip, videoid);
		ApiResult resultInfo = null;
		if (i <= 0) {
			resultInfo = ApiResult.resultInfo("0", "修改设备失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "修改设备成功！！", null);
		}
		return resultInfo;

	}

	@PostMapping("DCDevice/deleteDeviceById")
	@Permissions("dc:deleteDeviceById:del")
	public ApiResult deleteDeviceById(Integer id, Integer userid) {
		int i = dcDeviceService.deleteDeviceById(id, userid);
		ApiResult resultInfo = null;
		if (i <= 0) {
			resultInfo = ApiResult.resultInfo("0", "删除设备失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "删除设备成功！！", null);
		}
		return resultInfo;

	}

	// 联动视屏
	// 设备关联摄像头
	@PostMapping("DCDevice/devConectVideo")
	@Permissions("dc:devConectVideo:add")
	public ApiResult devConectVideo(DevRelationVideoParam param) throws MyException {
		Integer result = videoService.devConectVideo(param);
		return ApiResult.resultInfo("1", "成功", result);
	}

	// 更换设备
	@PostMapping("/DCDevice/ChangeDevice")
	@Permissions("dc:ChangeDevice:update")
	public ApiResult ChangeDevice(DCDeviceParam dcDeviceParam) throws MyException {
		int i = dcDeviceService.changeDevice(dcDeviceParam);
		ApiResult resultInfo = null;
		if (i <= 0) {
			resultInfo = ApiResult.resultInfo("0", "修改设备失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "修改设备成功！！", null);
		}
		return resultInfo;
	}

	// 批量修改责任人
	@PostMapping("DCDevice/DCChangeDevOwn")
	@Permissions("dc:DCChangeDevOwn:update")
	public ApiResult LKTHWChangeUsersDev(DCChangeUser dCChangeUser) throws MyException {

		return ApiResult.resultInfo("1", "成功", dcDeviceService.changeDevOwn(dCChangeUser));

	}

	// 加入设备签到列表
	@PostMapping("DCDevice/LKTAddSignDevList")
	@Permissions("dc:LKTAddSignDevList:add")
	public ApiResult LKTAddSignDevList(Integer moduleid, String devid, Integer patrolid) {
		ApiResult resultInfo = null;
		Integer insertObject = dcDeviceService.LKTAddSignDevList(moduleid, devid, patrolid);
		if (insertObject <= 0) {
			resultInfo = ApiResult.resultInfo("0", "失败！！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "成功！！", null);
		}
		return resultInfo;
	}

}
