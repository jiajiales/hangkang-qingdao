package com.hot.manage.db.video;

import org.apache.ibatis.annotations.Param;

import com.hot.manage.entity.video.DevRelationVideoParam;
import com.hot.manage.entity.video.TDevVideoRelation;

import tk.mybatis.mapper.common.Mapper;

public interface TDevVideoRelationMapper extends Mapper<TDevVideoRelation> {

	// 把重复分配的摄像头从摄像头设备关联表中删除
	Integer delDevVideorelationship(@Param("param") DevRelationVideoParam param);

	// 设备与摄像头重绑
	Integer updateDevVideoRelation(@Param("moduleid") Integer moduleid,
			@Param("devid") Integer devid,@Param("videoid") Integer videoid);
	
	// 设备与摄像头重绑
		Integer cutDevVideoRelation(@Param("moduleid") Integer moduleid,
				@Param("devid") Integer devid);

}
