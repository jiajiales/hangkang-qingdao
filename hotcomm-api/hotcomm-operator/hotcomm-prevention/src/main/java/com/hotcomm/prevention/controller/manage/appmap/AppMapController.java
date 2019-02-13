package com.hotcomm.prevention.controller.manage.appmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hotcomm.prevention.service.manage.appmap.AppMapService;
import com.hotcomm.prevention.utils.ApiResult;

@RestController
@RequestMapping("Appmap")
public class AppMapController {

	@Autowired
	private AppMapService appMapService;

	// app地图页面-用户下的项目
	@PostMapping("/appMapGroupList")
	public ApiResult AppMapGroupList(Integer userid) {
		return ApiResult.resultInfo("1", "成功", appMapService.AppMapGroupList(userid));
	}

	// app地图页面-根据项目组查询展示设备
	@PostMapping("/appMapDevList")
	public ApiResult AppMapDevList(Integer userid, Integer groupid, Integer moduleid, String devnumorcode) {
		return ApiResult.resultInfo("1", "成功", appMapService.AppMapDevList(userid, groupid, moduleid, devnumorcode));
	}

	// 模糊查询相关的设备编号
	@PostMapping("/appMapDevnum")
	public ApiResult AppMapDevnum(Integer userid, Integer moduleid, String devnum) {
		return ApiResult.resultInfo("1", "成功", appMapService.AppMapDevnum(userid, moduleid, devnum));
	}
	
	
	
}
