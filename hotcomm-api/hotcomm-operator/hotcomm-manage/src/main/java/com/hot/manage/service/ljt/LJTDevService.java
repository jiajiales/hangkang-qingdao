package com.hot.manage.service.ljt;

import java.util.List;
import com.hot.manage.entity.PageInfo;
import com.hot.manage.entity.common.groupgk.AlarmHandleStatistics;
import com.hot.manage.entity.common.groupgk.AlarmStateStatistics;
import com.hot.manage.entity.common.groupgk.AlarmTendecyVo;
import com.hot.manage.entity.ljt.LJTDev;
import com.hot.manage.entity.ljt.LJTDevone;
import com.hot.manage.entity.ljt.LJTDevv;
import com.hot.manage.exception.MyException;

public interface LJTDevService {
    public PageInfo<LJTDev> selectdevlist(LJTDevv ljtDevv) throws MyException;

    public LJTDevone selectdevbyid(Integer devid) throws MyException;

    public Integer updatedev(Integer devid, String devnum, String mac, Integer groupid, String code, Double lat, Double lng, Integer x, Integer y, Integer mapimgid,Integer own_id,Integer moduleid) throws MyException;

    public Integer insertdev(LJTDev ljtDev) throws MyException;
    
    public Integer updatedevmac(Integer devid,String mac) throws MyException;
    
    public Integer updateownid(String devids,Integer own_id) throws MyException;
    
    public AlarmTendecyVo selectAlarmForDay(Integer groupid)throws MyException;
	
    public AlarmTendecyVo selectAlarmForMonth(Integer groupid)throws MyException;
	
    public AlarmTendecyVo selectAlarmForThreeYear(Integer groupid)throws MyException;
    
    List<AlarmHandleStatistics> selectAlarmForWeeken(Integer groupid)throws MyException;
    
    List<AlarmStateStatistics> selectAlarmForState(Integer groupid,Integer TheType)throws MyException;
}
