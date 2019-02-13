package com.hot.manage.db.yg;

import com.github.pagehelper.Page;
import com.hot.manage.entity.yg.*;
import com.hot.manage.exception.MyException;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YGDevMapper {
	public Page<YGDev> selectdevlist(YGDevv yGDevv);

	public YGDevone selectdevbyid(@Param("devid") Integer devid);

	public Integer updatedev(@Param("devid") Integer devid, @Param("devnum") String devnum, @Param("mac") String mac,
			@Param("groupid") Integer groupid, @Param("code") String code, @Param("lat") String lat,
			@Param("lng") String lng, @Param("x") Double x, @Param("y") Double y, @Param("mapimgid") Integer mapimgid,
			@Param("own_id") Integer own_id);

	public Integer insertsiterelation(@Param("devid") Integer devid, @Param("mapimgid") Integer mapimgid);

	public Integer updatesiterelation(@Param("devid") Integer devid, @Param("mapimgid") Integer mapimgid);

	public Integer updatedevmac(@Param("devid") Integer devid, @Param("mac") String mac);

	public Integer insertdev(YGDev ygDev);

	public Integer insertgroup(YGDev ygDev);

	public Integer insertimggroup(YGDev ygDev);

	public Integer updateownid(@Param("devids") List<Object> devids, @Param("own_id") Integer own_id);

	List<TDeviceYg> selectDevForGroup(@Param("groupid") Integer groupid, @Param("userid") Integer userid,
			@Param("moduleid") Integer moduleid);

	Integer insertvideoRe(@Param("devid") Integer devid, @Param("videoid") Integer videoid,
			@Param("moduleid") Integer moduleid) throws MyException;

}