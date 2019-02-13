package com.hot.manage.db.mc;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hot.manage.entity.mc.TDeviceMc;
import com.hot.manage.entity.mc.TDeviceMcVo;

import tk.mybatis.mapper.common.Mapper;

public interface TDeviceMcMapper extends Mapper<TDeviceMc> {

	Page<TDeviceMcVo> selectPage(@Param("param") Map<String, Object> param);

	TDeviceMcVo selectByIdModuleid(@Param("id") Integer id, @Param("moduleid") Integer moduleid);
	
	List<TDeviceMc> selectListByExampl(@Param("groupid") Integer groupid,@Param("site") String site, @Param("moduleid") Integer moduleid);

}
