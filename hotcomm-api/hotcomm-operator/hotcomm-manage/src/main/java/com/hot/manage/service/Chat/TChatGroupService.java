package com.hot.manage.service.Chat;

import com.hot.manage.entity.Chat.TChatGroup;
import com.hot.manage.exception.MyException;

public interface TChatGroupService {
	
	/**
	 * 创建群组
	 * @param group
	 * @return
	 * @throws MyException
	 */
	Integer createGroup(TChatGroup group)throws MyException;
	
	/**
	 * 修改群信息
	 * @param group
	 * @return
	 * @throws MyException
	 */
	Integer updateGroup(TChatGroup group)throws MyException;
	
	/**
	 * 解散群组
	 * @return
	 * @throws MyException
	 */
	Integer dismissGroup(TChatGroup group)throws MyException;
	

}
