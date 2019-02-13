package com.hot.manage.service.video;

import com.hot.manage.entity.video.TDevVideoRelation;
import com.hot.manage.exception.MyException;

public interface DevVideoService {

	//查询设备是否已与摄像头关联
	boolean checkDevVideoRelation(Integer moduleid,Integer deviceId) throws MyException;
	
	//新建设备时联动摄像头
	Integer insertDevVideoRelation(TDevVideoRelation param) throws MyException;
	
	//设备与摄像头重绑
	Integer updateDevVideoRelation(Integer moduleid,Integer devid,Integer videoid) throws MyException;
	
	//设备与摄像头解绑
	Integer cutDevVideoRelation(Integer moduleid,Integer devid) throws MyException;

}
