package com.hotcomm.prevention.db.mysql.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hotcomm.prevention.bean.mysql.common.entity.TPower;

import tk.mybatis.mapper.common.Mapper;

public interface PowerMapper extends Mapper<TPower> {

	List<TPower> selectPowersByUserid(@Param("fatherid") Integer fatherid, @Param("userid") Integer userid);
	
	List<TPower> selectPowers(@Param("userid") Integer userid);

}
