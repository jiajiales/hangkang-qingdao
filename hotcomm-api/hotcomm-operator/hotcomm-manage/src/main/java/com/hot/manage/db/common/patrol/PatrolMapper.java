package com.hot.manage.db.common.patrol;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.hot.manage.entity.common.patrol.PatrolParams;
import com.hot.manage.entity.common.patrol.PatrolPersonVo;
import com.hot.manage.entity.common.patrol.bean.THkUserpatrelation;
import com.hot.manage.entity.system.TUser;

import tk.mybatis.mapper.common.Mapper;

public interface PatrolMapper extends Mapper<THkUserpatrelation> {

	// 巡检人员列表
	Page<PatrolPersonVo> selectPageInfo(@Param("patrol")PatrolParams patrol,@Param("params") List<Object> params);

	// 查询当前用户下的所有巡检人员
	List<TUser> selectPatrolUser(@Param("params") List<Object> params);

}
