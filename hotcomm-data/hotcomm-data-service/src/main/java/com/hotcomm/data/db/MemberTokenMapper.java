package com.hotcomm.data.db;

import com.hotcomm.data.bean.entity.sys.MemberToken;

import tk.mybatis.mapper.common.Mapper;

public interface MemberTokenMapper extends Mapper<MemberToken> {

	public void update(MemberToken memberToken);

	public void updateExpireTime(MemberToken memberToken);

	public void delByToken(MemberToken memberToken);

}
