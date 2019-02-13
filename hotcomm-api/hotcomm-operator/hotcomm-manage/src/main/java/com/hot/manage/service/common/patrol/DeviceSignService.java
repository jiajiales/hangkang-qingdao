package com.hot.manage.service.common.patrol;

import java.util.List;

import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.common.patrol.AppSigndDevVo;
import com.hot.manage.entity.common.patrol.DeviceSignVo;
import com.hot.manage.entity.common.patrol.PatrolParams;
import com.hot.manage.exception.MyException;

public interface DeviceSignService {
	//设备列表分页
	PageInfo<DeviceSignVo> selectPageInfo(PatrolParams params)throws MyException;
	
	//添加设备到签到设备表
	Integer insertDevSign(Integer deviceid,String userid,Integer moduleid)throws MyException;
	
	//修改签到设备
	Integer updateDevSign(Integer deviceid,String userid,Integer moduleid)throws MyException;
	
	//删除签到设备
	Integer delDevSign(Integer deviceid,Integer moduleid)throws MyException;
	
	//分配签到人员
	Integer allocationPatrol(Integer deviceid,String id,Integer moduleid)throws MyException;
	
	/**
	 * app签到设备查询
	 * @param userid
	 * @param context
	 * @return
	 */
	List<AppSigndDevVo> selectSigndDevVo(Integer userid,String context,Integer moduleid,Integer groupid);
	
	
}
