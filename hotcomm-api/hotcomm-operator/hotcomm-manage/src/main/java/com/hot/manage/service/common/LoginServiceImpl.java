package com.hot.manage.service.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hot.manage.db.common.LoginMapper;
import com.hot.manage.entity.common.user.PowerModel;
import com.hot.manage.entity.system.TUser;
import com.hot.manage.exception.MyException;

@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private LoginMapper loginMapper;

	@Override
	public List<PowerModel> GetPowerList(int userid) {
		return loginMapper.GetPowerList(userid);
	}

	@Override
	public TUser LoginFun(String username) {
		TUser user = loginMapper.LoginFun(username);
		return user;
	}

	@Override
	public Integer insertToken(int userid, String token, int logintype, String ipStr) {
		return loginMapper.insertToken(userid, token, logintype, ipStr);
	}

	/**
	 * 查询上一次登陆使用的token
	 */
	@Override
	public String queryToken(Integer userid) throws MyException {
		return loginMapper.queryToken(userid);
	}

	/**
	 * 查最后一条token
	 */
	@Override
	public String queryLastToken(Integer userid) throws MyException {
		return loginMapper.queryLastToken(userid);
	}

	@Override
	public void updateUser(String ip, String date, Integer id) throws MyException {
		loginMapper.updateUser(ip, date, id);
	}
}
