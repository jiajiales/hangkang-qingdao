package com.hotcomm.data.service;

import com.hotcomm.framework.web.exception.HKException;

public interface MemberTokenService {

	public String saveMemberToken(Integer memberId, String token, String sessionId) throws HKException;

	public void updateTokenExpire(String token) throws HKException;

	public void delToken(String token) throws HKException;

}
