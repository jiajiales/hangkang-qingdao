package com.hotcomm.data.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotcomm.data.bean.entity.sys.MemberToken;
import com.hotcomm.data.comm.Constant;
import com.hotcomm.data.db.MemberTokenMapper;
import com.hotcomm.data.service.MemberTokenService;
import com.hotcomm.framework.utils.DateUtils;
import com.hotcomm.framework.web.exception.HKException;

@Service
@Transactional
public class MemberTokenServiceImpl implements MemberTokenService {

	@Resource
	MemberTokenMapper memberTokenMapper;

	@Override
	@Transactional
	public String saveMemberToken(Integer memberId, String token, String sessionId) throws HKException {
		MemberToken membertoken = new MemberToken();
		membertoken.setMemberId(memberId);
		membertoken.setSessionId(sessionId);
		membertoken = memberTokenMapper.selectOne(membertoken);
		if (membertoken == null) {
			membertoken = new MemberToken(memberId, token,DateUtils.converTime(new Date(), DateUtils.SECOND, DateUtils.ADD, Constant.DEFAULT_EXPIRE));
			membertoken.setSessionId(sessionId);
			memberTokenMapper.insert(membertoken);
			return token;
		}
		membertoken.setToken(token);
		membertoken.setExpireTime(DateUtils.converTime(new Date(), DateUtils.SECOND, DateUtils.ADD, Constant.DEFAULT_EXPIRE));
		memberTokenMapper.update(membertoken);
		return token;

	}

	@Override
	@Transactional
	public void updateTokenExpire(String token) throws HKException {
		MemberToken memberToken = new MemberToken();
		memberToken.setToken(token);
		memberToken.setExpireTime(DateUtils.converTime(new Date(), DateUtils.MINUTE, DateUtils.SECOND, Constant.DEFAULT_EXPIRE));
		memberTokenMapper.updateExpireTime(memberToken);
	}

	@Override
	@Transactional
	public void delToken(String token) throws HKException {
		MemberToken memberToken = new MemberToken();
		memberToken.setToken(token);
		memberTokenMapper.delByToken(memberToken);
	}

}
