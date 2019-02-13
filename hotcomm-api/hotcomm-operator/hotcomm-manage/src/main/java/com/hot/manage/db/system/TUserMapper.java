package com.hot.manage.db.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hot.manage.entity.system.TUser;
import com.hot.manage.exception.MyException;

import tk.mybatis.mapper.common.Mapper;

public interface TUserMapper extends Mapper<TUser> {

	List<TUser> selectPageInfo(@Param("id") Integer id, @Param("userNum") String userNum,
			@Param("telephone") String telephone, @Param("status") Integer status);

	List<TUser> selectByUserId(@Param("userid") Integer userid);
	
	Integer selectDeviceOwn(@Param("ownid") Integer ownid)throws MyException;
}
