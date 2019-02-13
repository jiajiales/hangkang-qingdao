package com.hot.manage.db.Chat;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hot.manage.entity.Chat.TChatOneLog;
import com.hot.manage.entity.Chat.Record.ChatRecord;
import com.hot.manage.exception.MyException;

import tk.mybatis.mapper.common.Mapper;

public interface TChatOneLogMapper extends Mapper<TChatOneLog> {

	List<ChatRecord> selectChatRecord(@Param("senderid") String senderid, @Param("targetid") String targetid,
			@Param("type") Integer type) throws MyException;
}
