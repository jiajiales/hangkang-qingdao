package com.hot.manage.db.sj;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hot.manage.entity.sj.SJAlarmNum;
import com.hot.manage.entity.sj.SJDev;
import com.hot.manage.entity.sj.SJDevone;
import com.hot.manage.entity.sj.SJDevv;


public interface SJDevMapper {
    public Page<SJDev> selectdevlist(SJDevv sjDevv);

    public SJDevone selectdevbyid(Integer devid);

    public Integer updatedev(
            @Param("devid") Integer devid,
            @Param("devnum") String devnum,
            @Param("mac") String mac,
            @Param("groupid") Integer groupid,
            @Param("code") String code,
            @Param("lat") Double lat,
            @Param("lng") Double lng,
            @Param("x") Integer x,
            @Param("y") Integer y,
            @Param("mapimgid") Integer mapimgid,
            @Param("own_id") Integer own_id
    );
    public Integer insertsiterelation(@Param("devid") Integer devid,@Param("mapimgid") Integer mapimgid);
    public Integer updatesiterelation(@Param("devid") Integer devid,@Param("mapimgid") Integer mapimgid);

    public Integer insertdev(SJDev sjDev);
    public Integer insertgroup(SJDev sjDev);
    public Integer insertimggroup(SJDev sjDev);
    
    
	public Integer updatedevmac(@Param("devid") Integer devid, @Param("mac") String mac);
    
	public Integer updateownid(@Param("devids") List<Object> devids, @Param("own_id") Integer own_id);

	public List<SJAlarmNum> selectSJAlarmNums(@Param("Time") Integer Time, @Param("moduleID") Integer moduleID, @Param("userid")  Integer userid);
}