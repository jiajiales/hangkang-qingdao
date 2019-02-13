package com.hotcomm.prevention.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotcomm.prevention.bean.mysql.common.entity.TUserToken;
import com.hotcomm.prevention.db.mysql.common.TUserTokenMapper;
import com.hotcomm.prevention.exception.MyException;
@Service
public class TUserTokenServiceImpl implements TUserTokenService {
	
	@Autowired
	TUserTokenMapper userTokenMapper;

	@Override
	public void insertUserToken(TUserToken userToken) throws MyException {
		userTokenMapper.insertSelective(userToken);
	}

	@Override
	public String queryToken(Integer userid) throws MyException {
		return userTokenMapper.queryToken(userid);
	}

	@Override
	public String queryLastToken(Integer userid) throws MyException {
		return userTokenMapper.queryLastToken(userid);
	}

}
