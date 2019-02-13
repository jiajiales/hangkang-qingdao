package com.hot.manage.db.ljt;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hot.manage.entity.common.groupgk.AlarmHandleStatistics;
import com.hot.manage.entity.common.groupgk.AlarmStateStatistics;
import com.hot.manage.entity.common.groupgk.AlarmTendency;
import com.hot.manage.entity.ljt.LJTDev;
import com.hot.manage.entity.ljt.LJTDevone;
import com.hot.manage.entity.ljt.LJTDevv;
import com.hot.manage.exception.MyException;

public interface LJTDevMapper {
	public Page<LJTDev> selectdevlist(LJTDevv ljtDevv);

	public LJTDevone selectdevbyid(Integer devid);

	public Integer updatedev(@Param("devid") Integer devid, @Param("devnum") String devnum, @Param("mac") String mac,
			@Param("groupid") Integer groupid, @Param("code") String code, @Param("lat") Double lat,
			@Param("lng") Double lng, @Param("x") Integer x, @Param("y") Integer y, @Param("mapimgid") Integer mapimgid,
			@Param("own_id") Integer own_id);

	public Integer insertsiterelation(@Param("devid") Integer devid, @Param("mapimgid") Integer mapimgid);

	public Integer updatesiterelation(@Param("devid") Integer devid, @Param("mapimgid") Integer mapimgid);

	public Integer insertdev(LJTDev ljtDev);

	public Integer insertgroup(LJTDev ljtDev);

	public Integer insertimggroup(LJTDev ljtDev);

	public Integer updatedevmac(@Param("devid") Integer devid, @Param("mac") String mac);

	public Integer updateownid(@Param("devids") List<Object> devids, @Param("own_id") Integer own_id);

	List<AlarmTendency> selectAlarmForDay(@Param("groupid") Integer groupid) throws MyException;

	List<AlarmTendency> selectAlarmForMonth(@Param("groupid") Integer groupid) throws MyException;

	List<AlarmTendency> selectAlarmForThreeYear(@Param("groupid") Integer groupid) throws MyException;

	List<AlarmHandleStatistics> selectAlarmForWeeken(@Param("groupid") Integer groupid) throws MyException;

	List<AlarmStateStatistics> selectAlarmForState(@Param("groupid") Integer groupid,@Param("TheType") Integer TheType) throws MyException;
}