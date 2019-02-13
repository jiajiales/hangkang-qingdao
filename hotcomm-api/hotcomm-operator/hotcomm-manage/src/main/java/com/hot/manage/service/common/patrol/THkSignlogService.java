package com.hot.manage.service.common.patrol;

import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.common.patrol.ShakeRecordParam;
import com.hot.manage.entity.common.patrol.SignRecordParam;
import com.hot.manage.entity.common.patrol.SignRecordVo;
import com.hot.manage.exception.MyException;

public interface THkSignlogService {
	// 查看指定设备（终端设备）历史签到记录
	PageInfo<SignRecordVo> selectPageForDev(SignRecordParam param) throws MyException;
	
	//查看摇一摇设备签到历史记录
	PageInfo<SignRecordVo> selectPageForShake(ShakeRecordParam param) throws MyException;
	
	//设备签到全部历史
	PageInfo<SignRecordVo> selectPageInfoAllForDev(SignRecordParam param)throws MyException;

	// 对指定设备（终端设备）添加签到记录
	Integer insertDevSign(Integer userid,Integer deviceid,Integer moduleid) throws MyException;
	
	
}
