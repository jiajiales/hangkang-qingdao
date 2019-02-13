package com.hot.manage.service.video;

import java.util.List;

import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.video.DevRelationVideoPageParam;
import com.hot.manage.entity.video.DevRelationVideoParam;
import com.hot.manage.entity.video.DeviceRelationVideoVo;
import com.hot.manage.entity.video.RelationDevListVo;
import com.hot.manage.entity.video.TDeviceVideo;
import com.hot.manage.entity.video.VideoParam;
import com.hot.manage.exception.MyException;

public interface VideoService {

	// 分页查询与选中设备关联的摄像头
	PageInfo<DeviceRelationVideoVo> getDeviceRelationVideoPage(DevRelationVideoPageParam param) throws MyException;

	// 设备分配到摄像机
	Integer devConectVideo(DevRelationVideoParam param) throws MyException;

	// 获取摄像头列表
	List<TDeviceVideo> getVideoList(Integer groupId);

	// 添加摄像头
	Integer insertVideo(VideoParam param) throws MyException;

	// 修改摄像头
	Integer updateVideo(VideoParam param) throws MyException;

	// 删除摄像头
	Integer deleteVideo(Integer videoId, Integer moduleid) throws MyException;

	// 修改责任人
	Integer changeOwn(DevRelationVideoParam hWChangeUser);

	// 修改mac
	Integer changeMac(Integer videoId, String mac) throws MyException;

	// 重绑摄像头
	Integer reRelation(Integer moduleid, Integer devid, String videoid) throws MyException;

	// 分页查询与摄像头联动的设备列表
	PageInfo<RelationDevListVo> getRelationDevList(DevRelationVideoPageParam param) throws MyException;

}
