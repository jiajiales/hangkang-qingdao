package com.hot.manage.controller.common.appmap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hot.manage.annotation.Permissions;
import com.hot.manage.entity.common.appmap.SignInfo;
import com.hot.manage.entity.common.appmap.SignlogList;
import com.hot.manage.service.common.appmap.AppMapService;
import com.hot.manage.utils.ApiResult;

@RestController
public class AppMapController {

	@Autowired
	private AppMapService appMapService;

	// app地图页面-用户下的项目
	@PostMapping("/AppMapGroupList")
	@Permissions("AppMap:AppMapGroupList:query")
	public ApiResult AppMapGroupList(Integer userid) {
		return ApiResult.resultInfo("1", "成功", appMapService.AppMapGroupList(userid));
	}

	// app地图页面-根据项目组查询展示设备
	@PostMapping("/AppMapDevList")
	@Permissions("AppMap:AppMapDevList:query")
	public ApiResult AppMapDevList(Integer userid, Integer groupid, Integer moduleid, String devnumorcode) {
		return ApiResult.resultInfo("1", "成功", appMapService.AppMapDevList(userid, groupid, moduleid, devnumorcode));
	}

	// 模糊查询相关的设备编号
	@PostMapping("/AppMapDevnum")
	@Permissions("AppMap:AppMapDevnum:query")
	public ApiResult AppMapDevnum(Integer userid, Integer moduleid, String devnum) {
		return ApiResult.resultInfo("1", "成功", appMapService.AppMapDevnum(userid, moduleid, devnum));
	}
	
	
	// 签到列表
	
		@PostMapping("/AppSignlog")
		@Permissions("AppMap:AppMapDevnum:query")
		public ApiResult AppSignlog(Integer userid) {
			
			List<SignlogList>  list= appMapService.AppSignlog(userid);

			return ApiResult.resultInfo("1", "成功", list);
		}
		
		// 扫码签到
			@PostMapping("/AppSign")
			@Permissions("AppMap:AppMapDevnum:query")
			public ApiResult AppSignlogs(SignInfo signInfo) {
				Integer  code= appMapService.AppSigns(signInfo);
             if(code==0){
	            return ApiResult.resultInfo("0", "用户未绑定当前设备签到失败", null);
            }else if(code==2){
            	
            	 return ApiResult.resultInfo("0", "签到地点与设备地点距离大于100米签到失败", null);
            }  
            else if(code==3){
            	 return ApiResult.resultInfo("0", "未成功定位签到失败",null);
            }
				return ApiResult.resultInfo("1", "签到成功", null);
			}
	
	
}
