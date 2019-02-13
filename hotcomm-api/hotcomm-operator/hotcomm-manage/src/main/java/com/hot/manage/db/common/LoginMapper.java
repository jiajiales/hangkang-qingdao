package com.hot.manage.db.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hot.manage.entity.common.user.PowerModel;
import com.hot.manage.entity.system.TUser;

public interface LoginMapper {

	TUser LoginFun(@Param("username") String username);

	List<PowerModel> GetPowerList(int userid);

	Integer insertToken(@Param("userid") int userid, @Param("token") String token, @Param("logintype") int logintype,
			@Param("ipStr") String ipStr);

	String queryToken(@Param("userid") Integer userid);
	
	String queryLastToken(@Param("userid") Integer userid);
	
	void updateUser(@Param("ip") String ip,@Param("date") String date,@Param("id") Integer id);
}
