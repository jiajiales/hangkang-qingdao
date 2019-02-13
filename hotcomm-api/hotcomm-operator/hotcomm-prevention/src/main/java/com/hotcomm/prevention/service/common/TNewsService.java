package com.hotcomm.prevention.service.common;

import com.hotcomm.prevention.bean.mysql.common.entity.TNews;
import com.hotcomm.prevention.bean.mysql.common.params.SystemNoticeParams;
import com.hotcomm.prevention.bean.mysql.manage.PageInfo;
import com.hotcomm.prevention.exception.MyException;

public interface TNewsService {
	
	Integer addSystemNotice(TNews news)throws MyException;
	
	Integer updateSystemNotice(TNews news)throws MyException;
	
	Integer delSystemNotice(Integer id)throws MyException;
	
	PageInfo<TNews> selectPageByNotice(SystemNoticeParams param)throws MyException;
	
	TNews selectLastOne()throws MyException;
	
	TNews selectById(Integer id)throws MyException;
}
