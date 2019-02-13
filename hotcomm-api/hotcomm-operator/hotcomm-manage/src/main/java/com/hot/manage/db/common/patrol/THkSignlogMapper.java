package com.hot.manage.db.common.patrol;

import com.github.pagehelper.Page;
import com.hot.manage.entity.common.patrol.ShakeRecordParam;
import com.hot.manage.entity.common.patrol.SignRecordParam;
import com.hot.manage.entity.common.patrol.SignRecordVo;
import com.hot.manage.entity.common.patrol.bean.THkSignlog;

import tk.mybatis.mapper.common.Mapper;

public interface THkSignlogMapper extends Mapper<THkSignlog> {

	// 指定设备（终端设备）的签到历史记录
	Page<SignRecordVo> selectPageForDev(SignRecordParam param);
	
	//指定的摇一摇设备签到记录
	Page<SignRecordVo> selectPageForShake(ShakeRecordParam param);
	
	//(终端设备)全部的签到历史记录
	Page<SignRecordVo> selectPageInfoAllForDev(SignRecordParam param);
}
