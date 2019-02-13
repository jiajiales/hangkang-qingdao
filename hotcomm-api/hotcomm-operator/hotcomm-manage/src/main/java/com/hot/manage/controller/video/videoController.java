package com.hot.manage.controller.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hot.manage.annotation.Permissions;
import com.hot.manage.db.video.VideoMapper;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.video.DevRelationVideoPageParam;
import com.hot.manage.entity.video.DevRelationVideoParam;
import com.hot.manage.entity.video.DeviceRelationVideoVo;
import com.hot.manage.entity.video.RelationDevListVo;
import com.hot.manage.entity.video.TDevVideoRelation;
import com.hot.manage.entity.video.VideoParam;
import com.hot.manage.exception.MyException;
import com.hot.manage.service.video.DevVideoService;
import com.hot.manage.service.video.VideoService;
import com.hot.manage.service.video.VideoServiceImpl;
import com.hot.manage.utils.ApiResult;

@RestController
public class videoController {

	@Autowired
	private VideoService videoService;

	@Autowired
	private VideoServiceImpl videoServiceImpl;

	@Autowired
	private DevVideoService devVideoService;

	@Autowired
	private VideoMapper videoMapper;

	// 设备列表分页
	@PostMapping("/video/getRelationDevPageInfo")
	@Permissions("video:getRelationDevPageInfo:query")
	public ApiResult getRelationDevPageInfo(DevRelationVideoPageParam param) throws MyException {
		PageInfo<DeviceRelationVideoVo> selectPage = videoService.getDeviceRelationVideoPage(param);
		return ApiResult.resultInfo("1", "成功", selectPage);
	}

	// 设备关联摄像头
	@PostMapping("/video/devConectVideo")
	@Permissions("video:devConectVideo:add")
	public ApiResult devConectVideo(DevRelationVideoParam param) throws MyException {
		Integer result = videoService.devConectVideo(param);
		return ApiResult.resultInfo("1", "成功", result);
	}

	// 获取摄像头列表
	@PostMapping("/video/getVideoList")
	@Permissions("video:getVideoList:query")
	public ApiResult getVideoList(Integer groupid) throws MyException {
		return ApiResult.resultInfo("1", "成功", videoService.getVideoList(groupid));
	}

	// 添加摄像头
	@PostMapping("/video/addVideo")
	@Permissions("video:addVideo:add")
	public ApiResult addVideo(VideoParam param) throws MyException {
		return ApiResult.resultInfo("1", "成功", videoService.insertVideo(param));
	}

	// 更新摄像头
	@PostMapping("/video/updateVideo")
	@Permissions("video:updateVideo:update")
	public ApiResult updateVideo(VideoParam param) throws MyException {
		return ApiResult.resultInfo("1", "成功", videoService.updateVideo(param));
	}

	// 删除摄像头
	@PostMapping("/video/delVideo")
	@Permissions("video:delVideo:del")
	public ApiResult delVideo(Integer videoId, Integer moduleid) throws MyException {
		return ApiResult.resultInfo("1", "成功", videoService.deleteVideo(videoId, moduleid));
	}

	// 更改摄像头责任人
	@PostMapping("/video/changeOwn")
	@Permissions("video:changeOwn:update")
	public ApiResult changeOwn(DevRelationVideoParam param) throws MyException {
		return ApiResult.resultInfo("1", "成功", videoService.changeOwn(param));
	}

	// 更换mac地址
	@PostMapping("/video/changeMac")
	@Permissions("video:changeMac:update")
	public ApiResult changeMac(Integer videoId, String mac) throws MyException {
		ApiResult resultInfo = null;
		Integer one = videoService.changeMac(videoId, mac);
		if (one <= 0) {
			resultInfo = ApiResult.resultInfo("0", "视频MAC修改失败！", null);
		} else {
			resultInfo = ApiResult.resultInfo("1", "视频MAC修改成功！", null);
		}
		return resultInfo;
	}

	// 分页查询与摄像头联动的设备列表
	@PostMapping("/video/getRelationDevListPage")
	@Permissions("video:getRelationDevListPage:query")
	public ApiResult getRelationDevListPage(DevRelationVideoPageParam param) throws MyException {
		PageInfo<RelationDevListVo> selectPage = videoService.getRelationDevList(param);
		return ApiResult.resultInfo("1", "成功", selectPage);
	}

	// 查询设备是否已关联摄像头
	@PostMapping("/video/checkDevVideoRelation")
	@Permissions("video:checkDevVideoRelation:query")
	public ApiResult checkDevVideoRelation(Integer moduleid, Integer deviceId) throws MyException {
		return ApiResult.resultInfo("1", "成功", devVideoService.checkDevVideoRelation(moduleid, deviceId));
	}

	// 新建某个设备时，分配到摄像头
	@PostMapping("/video/OneDevRelationVideo")
	@Permissions("video:OneDevRelationVideo:add")
	public ApiResult OneDevRelationVideo(TDevVideoRelation param) throws MyException {
		return ApiResult.resultInfo("1", "成功", devVideoService.insertDevVideoRelation(param));
	}

	// 获取单个设备绑定的摄像头
	@PostMapping("/video/getdevRelationVideo")
	@Permissions("video:getdevRelationVideo:query")
	public ApiResult getdevRelationVideo(Integer moduleid, Integer devId) throws MyException {
		return ApiResult.resultInfo("1", "成功", videoMapper.getDevRelationList(moduleid, devId));
	}

	// 设备重绑摄像头
	@PostMapping("/video/reRelation")
	@Permissions("video:reRelation:update")
	public ApiResult reRelation(Integer moduleid, Integer devid, String videoid) throws MyException {
		return ApiResult.resultInfo("1", "成功", videoServiceImpl.reRelation(moduleid, devid, videoid));
	}

	// 获取与摄像头绑定的设备
	@PostMapping("/video/selectDevByVideoAndModuleid")
	@Permissions("video:select:selectDevByVideoAndModuleid")
	public ApiResult selectDevByVideoAndModuleid(Integer moduleid, Integer userid, Integer videoid) throws MyException {
		return ApiResult.resultInfo("1", "成功", videoServiceImpl.selectDevByVideoAndModuleid(moduleid, userid, videoid));
	}

	// 获取与某模块设备存在绑定关系的摄像头
	@PostMapping("/video/selectVideoByModuleid")
	@Permissions("video:select:selectVideoByModuleid")
	public ApiResult selectVideoByModuleid(Integer moduleid, Integer userid) throws MyException {
		return ApiResult.resultInfo("1", "成功", videoServiceImpl.selectVideoByModuleid(moduleid, userid));
	}

}
