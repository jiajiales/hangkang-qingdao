package com.hot.manage.db.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hot.manage.entity.system.TPower;

import tk.mybatis.mapper.common.Mapper;

public interface TPowerMapper extends Mapper<TPower> {

	List<TPower> selectAllById(@Param("userid") Integer userid);

	List<TPower> selectpowerByfatherid(@Param("userid") Integer userid, @Param("fatherid") Integer fatherid);

	int insertTPower(TPower param);

	TPower selectById(@Param("id") Integer id);

	int updateByKeySelective(TPower param);

	TPower selectOneById(@Param("userid") Integer userid);

	List<TPower> selectByFatherid(@Param("fatherid") Integer fatherid);

}
