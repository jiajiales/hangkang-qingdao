package com.hotcomm.prevention.db.mysql.manage.patrol;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.github.pagehelper.Page;
import com.hotcomm.prevention.bean.mysql.common.entity.TUser;
import com.hotcomm.prevention.bean.mysql.manage.patrol.PatrolParams;
import com.hotcomm.prevention.bean.mysql.manage.patrol.PatrolPersonVo;
import com.hotcomm.prevention.bean.mysql.manage.patrol.bean.THkUserpatrelation;

import tk.mybatis.mapper.common.Mapper;

public interface PatrolMapper extends Mapper<THkUserpatrelation> {

	// 巡检人员列表
	Page<PatrolPersonVo> selectPageInfo(@Param("patrol")PatrolParams patrol,@Param("params") List<Object> params);

	// 查询当前用户下的所有巡检人员
	List<TUser> selectPatrolUser(@Param("params") List<Object> params);

}
