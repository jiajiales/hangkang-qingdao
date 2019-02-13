package com.hot.manage.service.common.AppPush;

import com.hot.manage.entity.common.AppPush.T_hk_apppush;
import com.hot.manage.exception.MyException;

public interface AppPushService {

	T_hk_apppush selectRegid(Integer userid) throws MyException;

	Integer sendAll(String title, String message) throws MyException;
	
	Integer setAppPush(T_hk_apppush t_hk_apppush);
}
