package com.hot.manage.service.Chat;

import java.util.List;
import com.hot.manage.entity.Chat.TChatUserVo;
import com.hot.manage.entity.Chat.Record.ChatRecord;
import com.hot.manage.exception.MyException;

public interface ChatManagerService {

	/**
	 * 新增消息聊天用户
	 * @param userid
	 * @param username
	 * @param userpicpath
	 * @param token
	 * @return	用户id
	 * @throws Exception
	 */
	Integer AddChatUser(String userid,String username,String token)throws Exception;
	
	/**
	 *	修改用户
	 * @param userid
	 * @param username
	 * @return
	 * @throws Exception
	 */
	Integer UpdateChatUser(String userid,String username)throws Exception;
	
	
	/**
	 * 删除用户
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	Integer DeleteChatUser(String userid)throws MyException;
	
	/**
	 * 查询所有好友
	 * @param userid
	 * @return
	 * @throws MyException
	 */
	List<TChatUserVo> selectChatFriendRe(String chatuserid)throws MyException;
	

	/**
	 * 单人聊天(文本)
	 * @param chat
	 * @return
	 * @throws Exception
	 */
	Integer SendMessageByTxt(String senduserid,String targetid,String txtcontent,Integer msgtype)throws Exception;
	
	/**
	 * 单人聊天(图片)
	 * @param senduserid
	 * @param targetid
	 * @param url
	 * @return
	 * @throws Exception
	 */
	Integer SendMessageByImg(String senduserid,String targetid,String url,Integer msgtype)throws Exception;
	
	/**
	 * 单人聊天（语音）
	 * @param senduserid
	 * @param targetid
	 * @param time
	 * @param url
	 * @return
	 * @throws Exception
	 */
	Integer sendMessageByVideo(String senduserid,String targetid,long time,String url,Integer msgtype)throws Exception;
	
	/**
	 * 聊天记录查询
	 * @param senderid
	 * @param targetid
	 * @param type
	 * @return
	 * @throws MyException
	 */
	List<ChatRecord> selectChatRecords(String senderid,String targetid,Integer type)throws MyException;
}
