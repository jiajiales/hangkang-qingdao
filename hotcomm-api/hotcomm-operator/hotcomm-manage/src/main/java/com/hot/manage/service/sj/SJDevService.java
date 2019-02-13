package com.hot.manage.service.sj;

import java.util.List;

import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.sj.SJAlarmNum;
import com.hot.manage.entity.sj.SJDev;
import com.hot.manage.entity.sj.SJDevone;
import com.hot.manage.entity.sj.SJDevv;
import com.hot.manage.exception.MyException;

public interface SJDevService {
    public PageInfo<SJDev> selectdevlist(SJDevv sjDevv) throws MyException;

    public SJDevone selectdevbyid(Integer devid) throws MyException;

    public Integer updatedev(Integer devid, String devnum, String mac, Integer groupid, String code, Double lat, Double lng, Integer x, Integer y, Integer mapimgid,Integer own_id,Integer moduleid) throws MyException;

    public Integer insertdev(SJDev sjDev) throws MyException;
    
    public Integer updatedevmac(Integer devid,String mac) throws MyException;
    
    public Integer updateownid(String devids,Integer own_id) throws MyException;

	public List<SJAlarmNum> selectSJAlarmNums(Integer Time, Integer moduleID, Integer userid);
}
