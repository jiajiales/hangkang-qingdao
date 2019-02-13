package com.hot.manage.db.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hot.manage.entity.system.TUserPgrouRelationp;

import tk.mybatis.mapper.common.Mapper;

public interface TUserPgrouRelationpMapper extends Mapper<TUserPgrouRelationp> {

	List<TUserPgrouRelationp> selectByRoleId(@Param("id") Integer id);

}
