package com.hot.manage.db.video;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hot.manage.entity.video.DevByVideoidVo;
import com.hot.manage.entity.video.DevRelationVideoPageParam;
import com.hot.manage.entity.video.DevRelationVideoParam;
import com.hot.manage.entity.video.DeviceRelationVideoVo;
import com.hot.manage.entity.video.RelationDevListVo;
import com.hot.manage.entity.video.TDeviceVideo;
import com.hot.manage.entity.video.VideoByModuleid;
import com.hot.manage.exception.MyException;

import tk.mybatis.mapper.common.Mapper;

public interface VideoMapper extends Mapper<TDeviceVideo> {
	// 分页查询与选中设备关联的摄像头列表
	Page<DeviceRelationVideoVo> getDeviceRelationVideoPage(@Param("param") DevRelationVideoPageParam param);

	// 获取设备列表
	List<TDeviceVideo> getVideoList(@Param("groupid") Integer groupid) throws MyException;

	// 修改责任人
	Integer changeOwn(@Param("param") DevRelationVideoParam param) throws MyException;

	// 查询与部分摄像头的联动设备
	Page<RelationDevListVo> getRelationDevList(@Param("param") DevRelationVideoPageParam param) throws MyException;

	// 查询与某个设备联动的视频
	String getDevRelationList(@Param("moduleid") Integer moduleid, @Param("devid") Integer devid);

	List<DevByVideoidVo> selectDevByVideoAndModuleid(@Param("moduleid") Integer moduleid,
			@Param("userid") Integer userid, @Param("videoid") Integer videoid);

	List<VideoByModuleid> selectVideoByModuleid(@Param("moduleid") Integer moduleid, @Param("userid") Integer userid);
}
