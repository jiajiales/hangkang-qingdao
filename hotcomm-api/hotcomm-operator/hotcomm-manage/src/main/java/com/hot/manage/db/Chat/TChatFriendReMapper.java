package com.hot.manage.db.Chat;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hot.manage.entity.Chat.TChatFriendRelation;
import com.hot.manage.entity.Chat.TChatUserVo;
import com.hot.manage.exception.MyException;

import tk.mybatis.mapper.common.Mapper;

public interface TChatFriendReMapper extends Mapper<TChatFriendRelation>{
	
	List<TChatUserVo> selectFriendsList(@Param("chatuserid")String chatuserid)throws MyException;
}
