package com.hotcomm.prevention.db.mysql.common;

import org.apache.ibatis.annotations.Param;

import com.hotcomm.prevention.bean.mysql.common.entity.TUserToken;

import tk.mybatis.mapper.common.Mapper;

public interface TUserTokenMapper extends Mapper<TUserToken> {
	String queryToken(@Param("userid") Integer userid);

	String queryLastToken(@Param("userid") Integer userid);

}
