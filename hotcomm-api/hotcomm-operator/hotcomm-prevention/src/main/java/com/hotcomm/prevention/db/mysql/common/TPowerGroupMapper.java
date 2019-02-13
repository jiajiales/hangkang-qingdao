package com.hotcomm.prevention.db.mysql.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hotcomm.prevention.bean.mysql.common.entity.TPowerGroup;

import tk.mybatis.mapper.common.Mapper;

public interface TPowerGroupMapper extends Mapper<TPowerGroup> {

	TPowerGroup selectRoleByUserid(@Param("userid") Integer userid);

	List<TPowerGroup> selectRolesByFid(@Param("fatherid") Integer fatherid);

}
