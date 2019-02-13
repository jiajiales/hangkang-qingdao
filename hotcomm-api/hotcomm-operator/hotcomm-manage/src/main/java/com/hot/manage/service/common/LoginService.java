package com.hot.manage.service.common;

import java.util.List;

import com.hot.manage.entity.common.user.PowerModel;
import com.hot.manage.entity.system.TUser;
import com.hot.manage.exception.MyException;

public interface LoginService {

	public TUser LoginFun(String username);

	public List<PowerModel> GetPowerList(int userid);

	public Integer insertToken(int userid, String token, int logintype, String ipStr);

	String queryToken(Integer userid) throws MyException;
	
	String queryLastToken(Integer userid)throws MyException;
	
	void updateUser(String ip,String date,Integer id)throws MyException;
}
