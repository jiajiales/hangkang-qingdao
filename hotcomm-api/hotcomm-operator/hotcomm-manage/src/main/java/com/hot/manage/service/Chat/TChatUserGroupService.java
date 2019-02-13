package com.hot.manage.service.Chat;

import com.hot.manage.entity.Chat.TChatUserGroup;
import com.hot.manage.exception.MyException;

public interface TChatUserGroupService {
	
	/**
	 * 加入群组
	 * @param chatUserGroup
	 * @return
	 * @throws MyException
	 */
	Integer joinGroup(TChatUserGroup chatUserGroup)throws MyException;
	
	/**
	 * 退出群
	 * @param chatUserGroup
	 * @return
	 * @throws MyException
	 */
	Integer quitGroup(TChatUserGroup chatUserGroup)throws MyException;

}
