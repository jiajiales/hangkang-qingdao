package com.hot.manage.db.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hot.manage.entity.system.TPowerGroup;

import tk.mybatis.mapper.common.Mapper;

public interface TPowerGroupMapper extends Mapper<TPowerGroup> {

	List<TPowerGroup> selectAllByUserId(@Param("userid") Integer userid);

	TPowerGroup selectByUserId(@Param("userid") Integer userid);
	
	List<TPowerGroup> selctByFatherId(@Param("id") Integer id);

}
