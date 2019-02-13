package com.hotcomm.prevention.service.common;

import com.hotcomm.prevention.bean.mysql.common.entity.TUserToken;
import com.hotcomm.prevention.exception.MyException;

public interface TUserTokenService {
	
	void insertUserToken(TUserToken userToken)throws MyException;
	
	String queryToken(Integer userid) throws MyException;
	
	String queryLastToken(Integer userid)throws MyException;

}
