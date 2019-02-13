package com.hot.manage.service.yg;

import java.util.List;

import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.yg.TDeviceYg;
import com.hot.manage.entity.yg.YGDev;
import com.hot.manage.entity.yg.YGDevone;
import com.hot.manage.entity.yg.YGDevv;
import com.hot.manage.exception.MyException;

public interface YGDevService {
	public PageInfo<YGDev> selectdevlist(YGDevv yGDevv) throws MyException;

	public YGDevone selectdevbyid(Integer devid) throws MyException;

	public Integer updatedev(Integer devid, String devnum, String mac, Integer groupid, String code, String lat,
			String lng, Double x, Double y, Integer mapimgid, Integer own_id, Integer moduleid) throws MyException;

	public Integer updatedevmac(Integer devid, String mac) throws MyException;

	public Integer insertdev(YGDev ygDev) throws MyException;

	public Integer updateownid(String devids, Integer own_id) throws MyException;

	List<TDeviceYg> selectDevForGroup(Integer groupid, Integer userid, Integer moduleid) throws MyException;

}
