package com.hot.manage.service.mc;

import com.hot.manage.entity.DevPageParam;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.mc.TDeviceMcVo;
import com.hot.manage.exception.MyException;

public interface TDeviceMcService {

	Integer insertOne(TDeviceMcVo mc) throws MyException;

	Integer update(TDeviceMcVo mc) throws MyException;

	Integer del(Integer id,Integer moduleid) throws MyException;
	
	PageInfo<TDeviceMcVo> selectPage(DevPageParam param)throws MyException;

	TDeviceMcVo selectOne(Integer id, Integer moduleid) throws MyException;

	Integer changeOwn(String mcId,Integer ownId) throws MyException;
	
	Integer changeMac(Integer mcId,String mac) throws MyException;
	
	Integer AddSignDevList(Integer moduleid, String devid, Integer patrolid) throws MyException;


}
