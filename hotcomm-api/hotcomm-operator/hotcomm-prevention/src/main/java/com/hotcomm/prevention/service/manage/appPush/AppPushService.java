package com.hotcomm.prevention.service.manage.appPush;

import com.hotcomm.prevention.bean.mysql.manage.appPush.T_hk_apppush;
import com.hotcomm.prevention.exception.MyException;

public interface AppPushService {

	T_hk_apppush selectRegid(Integer userid) throws MyException;

	Integer sendAll(String title, String message) throws MyException;

	Integer setAppPush(T_hk_apppush t_hk_apppush) throws MyException;
}
