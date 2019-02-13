package com.hot.manage.db.common.patrol;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hot.manage.entity.common.patrol.AppSigndDevVo;
import com.hot.manage.entity.common.patrol.DeviceSignVo;
import com.hot.manage.entity.common.patrol.PatrolParams;

public interface DeviceSignMapper {

	/**
	 * 签到设备列表
	 * 
	 * @param params
	 * @return
	 */
	Page<DeviceSignVo> selectPageInfo(PatrolParams params);

	/**
	 * app查询签到设备
	 */
	List<AppSigndDevVo> selectSigndDevVo(@Param("userid") Integer userid, @Param("context") String context,
			@Param("moduleid") Integer moduleid, @Param("groupid") Integer groupid);

}
