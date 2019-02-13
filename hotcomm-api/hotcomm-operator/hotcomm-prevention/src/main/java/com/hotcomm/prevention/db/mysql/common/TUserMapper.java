package com.hotcomm.prevention.db.mysql.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hotcomm.prevention.bean.mysql.common.entity.TUser;

import tk.mybatis.mapper.common.Mapper;

public interface TUserMapper extends Mapper<TUser> {

	List<TUser> selectPageInfo(@Param("id") Integer id, @Param("userNum") String userNum,
			@Param("telephone") String telephone, @Param("status") Integer status);
}
